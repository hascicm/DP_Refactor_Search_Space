package entities.stateSpace;

import java.util.List;

public class State {
	private long id;
	private List<SmellOccurance> smells;
	private double fitness;
	private List<Relation> relations;
	private Relation sourceRelation = null;

	public List<Relation> getRelations() {
		return relations;
	}


	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}


	public double getFitness() {
		return fitness;
	}


	public void setFitness(double fitness) {
		this.fitness = fitness;
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
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
