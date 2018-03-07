package usecases;

import entities.stateSpace.Relation;

//REFACTOR - Lazy Class
  //SMELL: #SmellType(Lazy Class)
 public class MinMaxProbabilityCalculationStrategy extends ProbabilityCalculationStrategy {

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 @Override
	public double calculateProbability(Relation rel) {
		
		double probability = rel.getUsedRepair().calculateProbability();
		
		if(!(rel.getFromState().getSourceRelation() == null)){
			if(rel.getFromState().getSourceRelation().getProbability() < probability){
				probability = rel.getFromState().getSourceRelation().getProbability();			
			}
		}
		
		return probability;
	}

}
