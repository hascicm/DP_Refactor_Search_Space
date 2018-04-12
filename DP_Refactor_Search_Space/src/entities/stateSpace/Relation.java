package entities.stateSpace;

import entities.Repair;
import usecases.ProbabilityCalculationStrategy;

//REFACTOR - Lazy Class
  // smelltag end   : LAZC8 //SMELL: #SmellType(Lazy Class)
 public class Relation {
	
	private State fromState;
	private State toState;
	private SmellOccurance fixedSmellOccurance;
	private Repair usedRepair;

	private int pheromoneTrail;
	private double probability;
	
	public int getPheromoneTrail() {
		return pheromoneTrail;
	}
	public void setPheromoneTrail(int pheromoneTrail) {
		this.pheromoneTrail = pheromoneTrail;
	}
	public State getFromState() {
		return fromState;
	}
	public void setFromState(State fromState) {
		this.fromState = fromState;
	}
	public State getToState() {
		return toState;
	}
	public void setToState(State toState) {
		this.toState = toState;
	}
	public SmellOccurance getFixedSmellOccurance() {
		return fixedSmellOccurance;
	}
	public void setFixedSmellOccurance(SmellOccurance fixedSmellOccurance) {
		this.fixedSmellOccurance = fixedSmellOccurance;
	}
	public Repair getUsedRepair() {
		return usedRepair;
	}
	public void setUsedRepair(Repair usedRepair) {
		this.usedRepair = usedRepair;
	}
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE8 //SMELL: #SmellType(Feature Envy)
 public void calculateProbability(ProbabilityCalculationStrategy calculationStrategy){
		
		/*double probability = this.usedRepair.calculateProbability();
		
		if(this.getFromState().getSourceRelation() == null){
			this.probability = probability;
		}else{
			this.probability = probability * this.getFromState().getSourceRelation().probability;
		}*/
		
		this.probability = calculationStrategy.calculateProbability(this);
	}// smelltag start : FE8 
	
	public double getProbability() {
		return probability;
	}	
}// smelltag start : LAZC8 
