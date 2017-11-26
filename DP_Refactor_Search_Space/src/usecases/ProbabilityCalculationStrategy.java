package usecases;

import entities.stateSpace.Relation;

public abstract class ProbabilityCalculationStrategy {
	abstract public double calculateProbability(Relation rel); 
}
