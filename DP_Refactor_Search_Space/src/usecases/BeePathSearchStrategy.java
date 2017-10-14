package usecases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public class BeePathSearchStrategy extends PathSearchStrategy {
	
	private static int NUM_ITER = 300;
	private static int NUM_BEES = 128; 
	private static int NUM_EMPLOYED_BEES = 64;
	private static int NUM_ONLOOKER_BEES = 64;
	private static int SCOUT_MAX_DEPTH = 20;
	private static int PATCH_SIZE = 4;
	private List<Bee> bees;
	
	public BeePathSearchStrategy(RelationCreator relationCreator) {
		super(relationCreator);
		this.bees = new ArrayList<Bee>();
	}

	@Override
	public List<Relation> findPath(State rootState, int depth) {
		
		List<Bee> employeeBees = new ArrayList<Bee>();
		List<Bee> onlookerBees = new ArrayList<Bee>();
		List<Bee> fittestBees = new ArrayList<Bee>();
		List<Bee> remainingBees = new ArrayList<Bee>();
		
		this.init(rootState, depth);
		
		this.initPopulation(rootState);
		
		for(int i = 0; i < NUM_ITER; i++){
			
			this.evaluatePopulation(this.bees);
			
			Collections.sort(this.bees);
			//DEBUG
			//printBees(bees);
			//DEBUG
			
			//best <num> of states (employed bees)
			employeeBees.clear();
			for(int j = 0; j < NUM_EMPLOYED_BEES; j++){
				employeeBees.add(this.bees.get(j));
			}
			
			//others - onlookers
			onlookerBees.clear();
			for(int j = NUM_EMPLOYED_BEES; j < NUM_BEES; j++){
				onlookerBees.add(bees.get(j));
			}
			
			//calculate the probability for each employed bee - je to nevynutne? 
			this.calculateProbabilityForRecruit(employeeBees);
			
			//Recruit Bees for selected space exploit the employed bees (with the onlookers)
			int numOfRecruitBees = 0;
			for(Bee b : employeeBees){
				b.getRecruitedBees().clear();
								
				Bee tempBee = null;
				for(int j = 0; j < b.getNumForRecruit(); j++){
					tempBee = onlookerBees.get(numOfRecruitBees++);
					exploreSpace(tempBee, b.visitedState, PATCH_SIZE);
					b.getRecruitedBees().add(tempBee);
				}
			}
			
			//select the fittes Bee form Each State
			fittestBees.clear();
			remainingBees.clear();
			for(Bee b : employeeBees){
				//for comparison add self
				b.getRecruitedBees().add(b);
				
				Collections.sort(b.getRecruitedBees());
				//fittest bee on index O
				fittestBees.add(b.getRecruitedBees().get(0));
				
				//others go to remaining bees
				for(int j = 1; j < b.getRecruitedBees().size(); j++){
					remainingBees.add(b.getRecruitedBees().get(j));
				}
			}
			
			//Remaining bees are scouts and create random explore
			for(Bee b : remainingBees){
				exploreSpace(b, rootState, SCOUT_MAX_DEPTH);
			}
			
			//assign new population
			this.bees.clear();
			
			for(Bee b : fittestBees){
				this.bees.add(b);
			}
			
			for(Bee b : remainingBees){
				this.bees.add(b);
			}
		}
		
		Collections.sort(this.bees);
		
		List<Relation> results = new ArrayList<Relation>();
		State currentState = this.bees.get(0).getVisitedState();
		while(currentState.getSourceRelation() != null){
			results.add(currentState.getSourceRelation());
			currentState = currentState.getSourceRelation().getFromState();
		}
		
		Collections.reverse(results);
		
		//DEBUG
		System.out.println("");
		System.out.println("RESULT");
		for(Relation r : results){
			System.out.println("-------------");
			currentState = r.getFromState();
			System.out.println("S_" + currentState.getId()+ " [ Fitness: " + currentState.getFitness() + ", NumOfSmells: " +currentState.getSmells().size() + ", Depth: " + currentState.getDepth() + "] " + currentState);
			System.out.println(r.getUsedRepair().getName() + " P: " + r.getProbability());
			currentState = r.getToState();
			System.out.println("S_" + currentState.getId()+ " [ Fitness: " + currentState.getFitness() + ", NumOfSmells: " +currentState.getSmells().size() + ", Depth: " + currentState.getDepth() + "] " + currentState);
		}
		System.out.println(currentState);
		//DEBUG
		
		return results;
	}

	private void calculateProbabilityForRecruit(List<Bee> employeeBees) {
		double sum = 0.0;
		for(Bee b : employeeBees){
			sum += b.getHeuristic();
		}
		
		for(Bee b : employeeBees){
			b.setProbabilityToRecruit(b.getHeuristic()/sum);
			
			//25% for 2 recruited bee
			if(employeeBees.indexOf(b) < employeeBees.size()/4){
				b.setNumForRecruit(2);
			}else{
				
				if(employeeBees.indexOf(b) < ((employeeBees.size()/4) * 3)){
					b.setNumForRecruit(1);
				}else{
					b.setNumForRecruit(0);
				}
				
			}
			
		}		
	}
	
	private void checkLowProbability(Bee bee){
		if(isLowProbability(bee.getVisitedState())){
			bee.heuristic *= (-1.0);
		}
	}

	private void evaluatePopulation(List<Bee> bees) {
		for(Bee b: bees){
			b.setHeuristic(this.calculateHeuristic(b.getVisitedState().getSourceRelation()));
			checkLowProbability(b);
		}	
	}
	
	private void initPopulation(State rootState) {	
		for(int i = 0; i < NUM_BEES; i++){	
			Bee b = new Bee();
			exploreSpace(b, rootState, SCOUT_MAX_DEPTH);
			this.bees.add(b);
		}
	}

	private void exploreSpace(Bee bee, State state, int maxDepth){
		
		Random random = new Random();
		
		//rendom number between 1 - maxDepth
		int numOfHops = random.nextInt(maxDepth) + 1;
		
		State currentState = state;
		int indexOfSelectedRelation;
		for(int i = 0; i < numOfHops; i++){
			
			expandCurrentState(currentState);
			if(currentState.getRelations().size() == 0){
				break;
			}
				
			indexOfSelectedRelation = random.nextInt(currentState.getRelations().size()); 
			
			currentState = currentState.getRelations().get(indexOfSelectedRelation).getToState();	
		}
		
		bee.setVisitedState(currentState);
	}
		
	private class Bee implements Comparable<Bee>{
		
		State visitedState;
		double heuristic;
		double probabilityToRecruit;
		int numForRecruit; 
		List<Bee> recruitedBees = new ArrayList<Bee>();

		public State getVisitedState() {
			return visitedState;
		}

		public void setVisitedState(State visitedState) {
			this.visitedState = visitedState;
		}

		public double getHeuristic() {
			return heuristic;
		}

		public void setHeuristic(double heuristic) {
			this.heuristic = heuristic;
		}

		@Override
		public int compareTo(Bee o) {
			return Double.compare(o.getHeuristic(), this.getHeuristic());
		}

		public double getProbabilityToRecruit() {
			return probabilityToRecruit;
		}

		public void setProbabilityToRecruit(double probabilityToRecruit) {
			this.probabilityToRecruit = probabilityToRecruit;
		}

		public List<Bee> getRecruitedBees() {
			return recruitedBees;
		}

		public void setRecruitedBees(List<Bee> recruitedBees) {
			this.recruitedBees = recruitedBees;
		}

		public int getNumForRecruit() {
			return numForRecruit;
		}

		public void setNumForRecruit(int numForRecruit) {
			this.numForRecruit = numForRecruit;
		}
				
	}

	private void printBees(List<Bee> bees){		
		System.out.println((bees.get(0).getHeuristic()));
	}
}
