package usecases;


import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public abstract class PathSearchStrategy {
	
	protected Set<State> visitedStates;
	protected State localMinimum = null;
	protected PriorityQueue<GraphRelation> queue;
	protected RelationCreator relationCreator;
	protected int lastStateId = 0;
	
	public PathSearchStrategy(RelationCreator relationCreator){
		this.relationCreator = relationCreator;
	}
	
	public abstract List<Relation> findPath(State rootState, int depth);
			
	protected void applyRepair(List<Relation> rels){
		
		for(Relation rel : rels){
			State s = StateProcessor.applyRepair(rel.getFromState(), rel.getUsedRepair(), rel.getFixedSmellOccurance());
			s.setSourceRelation(rel);
			s.setDepth(rel.getFromState().getDepth() + 1);
			rel.setToState(s);
		}
	}
	
	
	protected boolean isVisited(State s){
		
		for(State visitedState : this.visitedStates){
			
			if(StateProcessor.isEquals(s, visitedState)){
				return true;
			}	
		}
		
		return false;
	}
	
	protected int calculateHeuristic(Relation r){
		return (int) r.getToState().getFitness(); 
	}
	
	protected void calculateEndNodeFitness(List<Relation> relations) {
		
		for(Relation rel: relations){
			StateProcessor.calculateFitness(rel.getToState());
		}	
	}
	
	protected class GraphRelation implements Comparable<GraphRelation>{
		
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
	
	
	protected void addRelationToQueue(Relation r){
		this.queue.add(new GraphRelation(r));
	}
	
	protected void addRelationsToQueue(List<Relation> relations){
		
		for(Relation r : relations)
			this.queue.add(new GraphRelation(r));
	}
	
	protected void init(State rootState, int depth) {
		
		// init rootState
		rootState.setDepth(depth);
		rootState.setId(lastStateId++);
		StateProcessor.calculateFitness(rootState);
		relationCreator.addRelationsToState(rootState);
		applyRepair(rootState.getRelations());
		calculateEndNodeFitness(rootState.getRelations());
		
	}
	
	protected void expandCurrentState(State currentState){
		
		//add set of relations to actual node
		relationCreator.addRelationsToState(currentState);
		
		//create end state of relations
		applyRepair(currentState.getRelations());
		calculateEndNodeFitness(currentState.getRelations());
	}
	
}
