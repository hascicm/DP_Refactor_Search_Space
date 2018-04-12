package usecases;

import entities.stateSpace.Relation;

//REFACTOR - Lazy Class
  // smelltag end   : LAZC15 //SMELL: #SmellType(Lazy Class)
 public abstract class ProbabilityCalculationStrategy {
	abstract public double calculateProbability(Relation rel); 
}// smelltag start : LAZC15 
