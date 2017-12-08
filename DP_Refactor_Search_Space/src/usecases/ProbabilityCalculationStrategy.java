package usecases;

import entities.stateSpace.Relation;

//REFACTOR - Lazy Class
  public abstract class ProbabilityCalculationStrategy {
	abstract public double calculateProbability(Relation rel); 
}
