package usecases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import entities.stateSpace.Relation;
import entities.stateSpace.State;


public class DefaultPathSearchStrategy extends PathSearchStrategy{
		
	private final int MAX_DEPTH = 10;
	
	public DefaultPathSearchStrategy(RelationCreator relationCreator) {
		super(relationCreator);
	}
	
	
	@Override
	public List<Relation> findPath(State rootState, int depth) {
			
		init(rootState, depth);
		
		start(rootState);
		
		//RESULT
		List<Relation> results = new ArrayList<Relation>();
		
		State currentState = this.localMinimum;
		while(currentState.getSourceRelation() != null){
			results.add(currentState.getSourceRelation());
			currentState = currentState.getSourceRelation().getFromState();
		}
		
		Collections.reverse(results);
			
		/*//DEBUG
		System.out.println("");
		System.out.println("RESULT");
		for(Relation r : results){
			System.out.println("-------------");
			currentState = r.getFromState();
			System.out.println("S_" + currentState.getId()+ " [ Fitness: " + currentState.getFitness() + ", NumOfSmells: " +currentState.getSmells().size() + ", Depth: " + currentState.getDepth() + "] " + currentState);
			System.out.println(r.getUsedRepair().getName());
			currentState = r.getToState();
			System.out.println("S_" + currentState.getId()+ " [ Fitness: " + currentState.getFitness() + ", NumOfSmells: " +currentState.getSmells().size() + ", Depth: " + currentState.getDepth() + "] " + currentState);
		}
		System.out.println(currentState);
		//DEBUG	*/	
		return results;
	}


	protected void start(State rootState) {
		
		this.lastStateId = 0;
		// add relations from rootState to queue
		this.addRelationsToQueue(rootState.getRelations());
		
		Relation currentRelation = null;
		State currentState = null;
		
		while(!this.queue.isEmpty()){
			
			//get next state for visiting
			currentRelation = this.queue.remove().getRelation();
			currentState = currentRelation.getToState();
			
			
			//Skip the state contains same smells as any of visited state (node)
			if(isVisited(currentState)){
				continue;
			}
			
			currentState.setId(lastStateId++); 
			
			
			//if currentState is better then local minimum
			if(currentState.getFitness() < this.localMinimum.getFitness()){
				
				this.localMinimum = currentState;
				
			}
			
			if(currentState.getDepth() < MAX_DEPTH){
				expandCurrentState(currentState);
			}
			
			//System.out.println(currentState.getDepth() + ", " + currentState.getFitness() + ", " + (this.localMinimum.getDepth()+ this.localMinimum.getFitness()));
			
		}
		
	}	

	protected void init(State rootState, int depth) {
		super.init(rootState, depth);
		
		// init queue
		this.queue = new PriorityQueue<GraphRelation>();
		this.visitedStates = new HashSet<State>();

		// init visited state
		this.visitedStates = new HashSet<State>();
		this.visitedStates.add(rootState);
		this.localMinimum = rootState;
	
		this.visitedStates.add(rootState);		
	}
		
protected void expandCurrentState(State currentState){
		
		super.expandCurrentState(currentState);
		
		this.visitedStates.add(currentState);
		
		//add just created relations to queue
		this.addRelationsToQueue(currentState.getRelations());
	}
	
}
