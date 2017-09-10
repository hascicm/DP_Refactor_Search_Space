package usecases;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import entities.DependencyRepair;
import entities.DependencyType;
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
		
		List<Relation> newRelations = new LinkedList<Relation>();	
		
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
										
					for(Relation newRel : makeRelationsOfRepair(repair)){
						newRel.setFixedSmellOccurance(smellOccurance);
						relations.add(newRel);
					}
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
	
	private List<Relation> makeRelationsOfRepair(Repair repair){
		
		List<Relation> results = new ArrayList<Relation>();
		
		//make base repair
		results.add(makeBaseRepairRelation(repair));
		
		if(repair instanceof DependencyRepair){
			//results.addAll(makeDependencyRepairRelations((DependencyRepair)repair));
			results.addAll(makeDependencyRepairRelationsONE((DependencyRepair)repair));
		}
		
		
		return results;
	}

		private Relation makeBaseRepairRelation(Repair repair) {		
		Relation relation = new Relation();
		relation.setUsedRepair(new Repair(repair.getName(), repair.getSmells()));
		return relation;
	}
	
	
	//TODO - fuzzy logic for dependencies
	/*
	 * This method makes the combination of dependencies. 
	 * 
	 * Its mean - if repair causes one smell A and repairs one smells B, 
	 * 	it creates 3 relations: causes A, repairs B, cause A and repairs B. 
	 * 
	 * */
	private List<Relation> makeDependencyRepairRelationsONE(DependencyRepair repair) {
		
		List<Relation> relations = new ArrayList<Relation>(); 
		List<DependencyUnit> dependencies = new ArrayList<>();
		
		//Create one list with dependencies
		for(DependencyType dependencyType : repair.getDependencies().keySet()){
			
			for(SmellType smellType : repair.getDependencies().get(dependencyType)){
				dependencies.add(new DependencyUnit(dependencyType, smellType));
			}	
		}
		
		List<List<DependencyUnit>> combinations = new ArrayList<List<DependencyUnit>>();
		//Create combination of dependecies
		for(int i = 0; i < dependencies.size(); i++){
			combinations(dependencies, i+1, 0, new DependencyUnit[i+1], combinations);
		}
		
		//Every combination is a one Relation
		for(List<DependencyUnit> tempDependencyUnitList : combinations){
			DependencyRepair dependencyRepair = new DependencyRepair(repair.getName(), repair.getSmells());
			
			for(DependencyUnit depUnit : tempDependencyUnitList){
				dependencyRepair.addDependency(depUnit.getDependencyType(), depUnit.getSmellType());
			}
			
			Relation rel = new Relation();
			rel.setUsedRepair(dependencyRepair);
			relations.add(rel);
		}
		
				
		return relations;
	}
	
	private class DependencyUnit{
		DependencyType dependencyType;
		SmellType smellType;
		
		private DependencyUnit(DependencyType dependencyType, SmellType smellType) {
			super();
			this.dependencyType = dependencyType;
			this.smellType = smellType;
		}
		private DependencyType getDependencyType() {
			return dependencyType;
		}
		private void setDependencyType(DependencyType dependencyType) {
			this.dependencyType = dependencyType;
		}
		private SmellType getSmellType() {
			return smellType;
		}
		private void setSmellType(SmellType smellType) {
			this.smellType = smellType;
		}	
	}
	
	static void combinations(List<DependencyUnit> dependencies, int len, int startPosition, 
							 DependencyUnit[] results, List<List<DependencyUnit>> combinations){
        if (len == 0){
            
        	List<DependencyUnit> tempDependencyList = new ArrayList<DependencyUnit>();
            
            for(DependencyUnit du : results){
            	tempDependencyList.add(du);
            }
            
            combinations.add(tempDependencyList);
            
            return;
        }       
        for (int i = startPosition; i <= dependencies.size()-len; i++){
            results[results.length - len] = dependencies.get(i);
            combinations(dependencies, len-1, i+1, results, combinations);
        }
    }
}
