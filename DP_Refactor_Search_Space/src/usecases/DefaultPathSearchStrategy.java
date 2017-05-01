package usecases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public class DefaultPathSearchStrategy extends PathSearchStrategy{
		
	private final int MAX_NODE = 15000;
	
	public DefaultPathSearchStrategy(RelationCreator relationCreator) {
		super(relationCreator);
	}
	
	@Override
	public List<Relation> findPath(State rootState) {
			
		initRoot(rootState);
			
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
				
				if(localMinimum.getFitness() == 0)
					break;
			}
			
			expandCurrentState(currentState);
						
			if(currentState.getId() > MAX_NODE){
				break;
			}
		}
		
		//RESULT
		List<Relation> results = new ArrayList<Relation>();
		
		currentState = this.localMinimum;
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
			System.out.println("S_" + currentState+ " [" + currentState.getFitness() + ", " +currentState.getSmells().size() + "] " + currentState);
			System.out.println(r.getUsedRepair().getName());
			currentState = r.getToState();
			System.out.println("S_" + currentState+ " [" + currentState.getFitness() + ", " +currentState.getSmells().size() + "] " + currentState);
		}
		System.out.println(currentState);
		//DEBUG		
		return results;
	}	

	protected int calculateHeuristic(Relation r){
		
		int result = 0;	

		result += r.getToState().getFitness();
		result += r.getUsedRepair().getWeight();
		
		return result; 
	}
	
}
