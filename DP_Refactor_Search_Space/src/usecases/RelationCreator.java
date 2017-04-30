package usecases;

import java.util.ArrayList;
import java.util.List;

import entities.Repair;
import entities.SmellType;
import entities.stateSpace.Relation;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class RelationCreator {
	List<SmellType> smellTypes;
	List<Repair> repairs;
	
	public RelationCreator(List<SmellType> smellTypes, List<Repair> repairs) {
		super();
		this.smellTypes = smellTypes;
		this.repairs = repairs;
	}
	
	public List<SmellType> getSmellTypes() {
		return smellTypes;
	}
	
	public void setSmellTypes(List<SmellType> smellTypes) {
		this.smellTypes = smellTypes;
	}
	
	public List<Repair> getRepairs() {
		return repairs;
	}
	
	public void setRepairs(List<Repair> repairs) {
		this.repairs = repairs;
	}
	
	public void addRelationsToState(State state){
		
		List<Relation> newRelations = new ArrayList<Relation>();	
		
		for(SmellOccurance so : state.getSmells()){
			newRelations.addAll(this.assignRelationsToSmellOccurance(so));
		}
		
		this.assignFromStateToRelation(newRelations, state);
		state.setRelations(newRelations);
	}
	
	private List<Relation> assignRelationsToSmellOccurance(SmellOccurance smellOccurance){
		
		List<Relation> relations = new ArrayList<Relation>();
		
		for(Repair repair : this.repairs){
			
			for(SmellType smell : repair.getSmells()){
				
				if(smellOccurance.getSmell() == smell){
					Relation newRel = new Relation();
					newRel.setFixedSmellOccurance(smellOccurance);
					newRel.setUsedRepair(repair);
					relations.add(newRel);
				}
			}
		}
		
		return relations;
	}
	
	private void assignFromStateToRelation(List<Relation> rels, State state){
		
		for(Relation r : rels){
			r.setFromState(state);
		}
		
	}
	
}
