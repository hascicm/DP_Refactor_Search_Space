package usecases;



import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public abstract class PathSearchStrategy {
	
	protected Set<String> visitedStates;
	protected State localMaximum = null;
	protected PriorityQueue<GraphRelation> queue;
	protected RelationCreator relationCreator;
	protected int lastStateId = 0;
	
	private static double PROBABILITY_THRASHOLD = 0.30;
	private long rootStateSmellsWeight = 0;
	
	public PathSearchStrategy(RelationCreator relationCreator){
		this.relationCreator = relationCreator;
	}
	
	public abstract List<Relation> findPath(State rootState, int depth);
			
	protected void applyRepair(List<Relation> rels){
		
		Relation rel = null;
		int length = rels.size();
		for(int i = 0; i < length; i++){
			
			rel = rels.get(i);
			State s = StateProcessor.applyRepair(rel.getFromState(), rel.getUsedRepair(), rel.getFixedSmellOccurance());
			s.setSourceRelation(rel);
			s.setDepth(rel.getFromState().getDepth() + 1);
			rel.setToState(s);
					
			//sort smells in new state by ID
			s.getSmells().sort((o1, o2) -> o1.getSmell().getId().compareTo(o2.getSmell().getId()));
			
		}
	}
	
	protected boolean isVisited(State s){	
		return this.visitedStates.contains(StateProcessor.createHash(s)) ? true : false; 			
	}
		
	protected boolean isLowProbability(State s){
		
		boolean result = false;
		
		if(s.getSourceRelation() != null){
			if(s.getSourceRelation().getProbability() < PROBABILITY_THRASHOLD){
				result = true;
			}
		}
		return result; 
	}
	
	protected int calculateHeuristic(Relation r){
		
		int result = 0;
		
		if(r != null){
			result = (int) r.getToState().getFitness();
		}
		
		
		return result; 
	}
	
	protected void calculateEndNodeFitness(List<Relation> relations) {
		
		for(Relation rel: relations){
			StateProcessor.calculateFitness(rel.getToState(), this.rootStateSmellsWeight);
		}	
	}
	
	protected void calculateProbabilityOfRelations(List<Relation> relations){
		for(Relation rel : relations){
			rel.calculateProbability();
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
				return Integer.compare(calculateHeuristic(o.getRelation()), calculateHeuristic(this.getRelation()));
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
		
		//init root atributes
		this.rootStateSmellsWeight = StateProcessor.calculateSmellsWeight(rootState);
		
		// init rootState
		rootState.setDepth(depth);
		rootState.setId(lastStateId++);
		StateProcessor.calculateFitness(rootState, this.rootStateSmellsWeight);
		relationCreator.addRelationsToState(rootState);
		applyRepair(rootState.getRelations());
		calculateEndNodeFitness(rootState.getRelations());
		
	}
	
	protected void expandCurrentState(State currentState){
		
		relationCreator.addRelationsToState(currentState);
		
		applyRepair(currentState.getRelations());
		
		calculateEndNodeFitness(currentState.getRelations());
		calculateProbabilityOfRelations(currentState.getRelations());
	}
	
}
