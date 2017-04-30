package entities.stateSpace;

import java.util.List;

public class State {
	private List<SmellOccurance> smells;
	private double fittnes;
	private List<Relation> relations;
	private Relation sourceRelation = null;

	public List<Relation> getRelations() {
		return relations;
	}


	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}


	public double getFittnes() {
		return fittnes;
	}


	public void setFittnes(double fittnes) {
		this.fittnes = fittnes;
	}


	public List<SmellOccurance> getSmells() {
		return smells;
	}


	public void setSmells(List<SmellOccurance> smells) {
		this.smells = smells;
	}


	public Relation getSourceRelation() {
		return sourceRelation;
	}


	public void setSourceRelation(Relation sourceRelation) {
		this.sourceRelation = sourceRelation;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("Smells: ");
		
		for(SmellOccurance so : this.getSmells()){
			sb.append(so.getSmell().getName());
			sb.append(", ");
		}
		
		return sb.toString();
	}
	
}
