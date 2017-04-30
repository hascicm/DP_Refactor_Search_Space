package usecases;

import java.util.ArrayList;
import java.util.LinkedList;
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
		
		List<Relation> allRelations = new ArrayList<Relation>();	
		for(SmellOccurance so : state.getSmells()){
			allRelations.addAll(this.createRelationsToSmellOccurance(so));
		}
		
		this.addFromState(allRelations, state);
		state.setRelations(allRelations);
	}
	
	private List<Relation> createRelationsToSmellOccurance(SmellOccurance so){
		
		List<Relation> relations = new ArrayList<Relation>();
		
		for(Repair r : this.repairs){
			for(SmellType s : r.getSmells()){
				if(so.getSmell() == s){
					Relation rel = new Relation();
					rel.setFixedSmellOccurance(so);
					rel.setUsedRepair(r);
					relations.add(rel);
				}
			}
		}
		
		return relations;
	}
	
	private void addFromState(List<Relation> rels, State state){
		for(Relation r : rels){
			r.setFromState(state);
		}
	}
	
}
