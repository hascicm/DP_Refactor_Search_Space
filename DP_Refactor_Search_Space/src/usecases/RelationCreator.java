package usecases;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import entities.Dependency;
import entities.DependencyRepair;
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
		
		calculateProbabilityForRelations(state.getRelations());
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
			results.addAll(makeDependencyRepairRelations((DependencyRepair)repair));
		}
		
		
		return results;
	}

	private Relation makeBaseRepairRelation(Repair repair) {		
		Relation relation = new Relation();
		relation.setUsedRepair(new Repair(repair.getName(), repair.getRepairUses()));
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
	private List<Relation> makeDependencyRepairRelations(DependencyRepair repair) {
		
		List<Relation> relations = new ArrayList<Relation>(); 
		List<Dependency> dependencies = repair.getDependencies();
			
		List<List<Dependency>> combinations = new ArrayList<List<Dependency>>();
		//Create combination of dependecies
		for(int i = 0; i < dependencies.size(); i++){
			combinations(dependencies, i+1, 0, new Dependency[i+1], combinations);
		}
		
		//Every combination is a one Relation
		for(List<Dependency> tempDependencyList : combinations){
			DependencyRepair dependencyRepair = new DependencyRepair(repair.getName(), repair.getRepairUses());
			
			for(Dependency dep : tempDependencyList){
				dependencyRepair.addDependency(dep.getType(), dep.getSmell(), dep.getPropability());
			}
			
			Relation rel = new Relation();
			rel.setUsedRepair(dependencyRepair);
			relations.add(rel);
		}
		
				
		return relations;
	}
	
	static void combinations(List<Dependency> dependencies, int len, int startPosition, 
		Dependency[] results, List<List<Dependency>> combinations){
        if (len == 0){
            
        	List<Dependency> tempDependencyList = new ArrayList<Dependency>();
            
            for(Dependency d : results){
            	tempDependencyList.add(d);
            }
            
            combinations.add(tempDependencyList);
            
            return;
        }       
        for (int i = startPosition; i <= dependencies.size()-len; i++){
            results[results.length - len] = dependencies.get(i);
            combinations(dependencies, len-1, i+1, results, combinations);
        }
    }
	
	private static void calculateProbabilityForRelations(List<Relation> rels){
		
		for(Relation rel : rels){
			rel.calculateProbability();
		}
		
	}
	
}
