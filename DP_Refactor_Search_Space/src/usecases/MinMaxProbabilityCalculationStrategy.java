package usecases;

import entities.stateSpace.Relation;

//REFACTOR - Lazy Class
  // smelltag end   : LAZC12 //SMELL: #SmellType(Lazy Class)
 public class MinMaxProbabilityCalculationStrategy extends ProbabilityCalculationStrategy {

	//REFACTOR - Feature Envy
  // smelltag end   : FE26 //SMELL: #SmellType(Feature Envy)
 @Override
	public double calculateProbability(Relation rel) {
		
		double probability = rel.getUsedRepair().calculateProbability();
		
		if(!(rel.getFromState().getSourceRelation() == null)){
			if(rel.getFromState().getSourceRelation().getProbability() < probability){
				probability = rel.getFromState().getSourceRelation().getProbability();			
			}
		}
		
		return probability;
	}// smelltag start : FE26 

}// smelltag start : LAZC12 
