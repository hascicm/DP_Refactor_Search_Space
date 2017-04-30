package usecases;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public class DefaultPathSearchStrategy extends PathSearchStrategy{
	
	private Set<State> visitedStates;
	private PriorityQueue<GraphRelation> queue;
	private State localMinimum = null;
	private final int MAX_depth = 10;
	
	@Override
	public List<Relation> findPath(State rootState, RelationCreator relationCreator) {
		
		//init queue
		this.queue = new PriorityQueue<GraphRelation>();
		
		//init visited state
		this.visitedStates = new HashSet<State>();
		this.visitedStates.add(rootState);
		this.localMinimum = rootState;
		
		//init rootState
		StateEvaluator.calculateFitness(rootState);
		relationCreator.addRelationsToState(rootState);
		applyRepair(rootState.getRelations());
		calculateEndNodeFitness(rootState.getRelations());
		
		//DEBUG
		System.out.println(rootState);
		//DEBUG
		
		
		//add relations from rootState to queue
		this.addRelationsToQueue(rootState.getRelations());
		
		Relation currentRelation = null;
		State currentState = null;
		
		while(!this.queue.isEmpty()){
			
			//get next state for visiting
			currentRelation = this.queue.remove().relation;
			currentState = currentRelation.getToState();
			
			//TODO Check the control of visited state
			
			//DEBUG
			System.out.println("(" + currentRelation.getUsedRepair().getName() + ") :" + currentState);
			//DEBUG
			
			//if currentState is better then local minimum
			if(currentState.getFittnes() < this.localMinimum.getFittnes()){
				
				this.localMinimum = currentState;
				
				if(localMinimum.getFittnes() == 0)
					break;
			}
			
			//add set of relations to actual node
			relationCreator.addRelationsToState(currentState);
			//create end state of relations
			applyRepair(currentState.getRelations());
			calculateEndNodeFitness(currentState.getRelations());
			
			//add just created relations to queue
			this.addRelationsToQueue(currentState.getRelations());
			
			//TODO depth control
			//If more then K then break
		}
		
		//RESULT
		//DEBUG
		System.out.println("");
		System.out.println("RESULT");
		currentState = this.localMinimum;
		while(currentState.getSourceRelation() != null){
			System.out.println(currentState);
			System.out.println(currentState.getSourceRelation().getUsedRepair().getName());
			currentState = currentState.getSourceRelation().getFromState();
		}
		System.out.println(currentState);
		//DEBUG
		
		return null;
	}

	
	private void calculateEndNodeFitness(List<Relation> relations) {
		
		for(Relation rel: relations){
			StateEvaluator.calculateFitness(rel.getToState());
		}	
	}


	private void addRelationToQueue(Relation r){
		this.queue.add(new GraphRelation(r));
	}
	
	private void addRelationsToQueue(List<Relation> relations){
		
		for(Relation r : relations)
			this.queue.add(new GraphRelation(r));
	}
	
	private class GraphRelation implements Comparable<GraphRelation>{
		
		private Relation relation;

		public Relation getRelation() {
			return relation;
		}

		public GraphRelation(Relation relation) {
			super();
			this.relation = relation;
		}
		
		@Override
		public int compareTo(GraphRelation o) {
				return Integer.compare(calculateHeuristic(this.getRelation()), calculateHeuristic(o.getRelation()));
		}			
	}
	
	
	private static int calculateHeuristic(Relation r){
		
		int result = 0;	

		result += r.getToState().getFittnes();
		
		return result; 
	}
	
	/*private boolean isVisited(){
		
		
		
		
	}*/
	
	private void applyRepair(List<Relation> rels){
		
		for(Relation rel : rels){
			State s = StateProcessor.applyRepair(rel.getFromState(), rel.getUsedRepair(), rel.getFixedSmellOccurance());
			s.setSourceRelation(rel);
			rel.setToState(s);
		}
	}
}
