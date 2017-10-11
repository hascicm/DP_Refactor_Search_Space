package entities.stateSpace;

import entities.Repair;

public class Relation {
	
	private State fromState;
	private State toState;
	private SmellOccurance fixedSmellOccurance;
	private Repair usedRepair;
	private double probability;
	
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
	
	public void calculateProbability(){
		
		double probability = this.usedRepair.calculateProbability();
		
		if(this.getFromState().getSourceRelation() == null){
			this.probability = probability;
		}else{
			this.probability = probability * this.getFromState().getSourceRelation().probability;
		}
	}
	
	public double getProbability() {
		return probability;
	}	
}
