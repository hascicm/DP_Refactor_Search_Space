package usecases;

import java.util.List;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public abstract class PathSearchStrategy {
	
	public abstract List<Relation> findPath(State rootState, RelationCreator relationCreator);
	
	
}
