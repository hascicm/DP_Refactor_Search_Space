package usecases;

import java.util.List;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public class BeePathSearchStrategy extends PathSearchStrategy {

	
	
	public BeePathSearchStrategy(RelationCreator relationCreator) {
		super(relationCreator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Relation> findPath(State rootState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int calculateHeuristic(Relation r) {
		// TODO Auto-generated method stub
		return 0;
	}

}
