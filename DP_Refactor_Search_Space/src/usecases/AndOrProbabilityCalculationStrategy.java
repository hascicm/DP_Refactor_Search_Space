package usecases;

import entities.stateSpace.Relation;

//REFACTOR - Lazy Class
  public class AndOrProbabilityCalculationStrategy extends ProbabilityCalculationStrategy {

	//REFACTOR - Feature Envy
  @Override
	public double calculateProbability(Relation rel) {
		double probability = rel.getUsedRepair().calculateProbability();
		
		if(!(rel.getFromState().getSourceRelation() == null)){
			probability = probability * rel.getFromState().getSourceRelation().getProbability();			
		}
		
		return probability;
	}

}
