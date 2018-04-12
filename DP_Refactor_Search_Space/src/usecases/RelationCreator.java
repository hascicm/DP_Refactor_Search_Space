package usecases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entities.Dependency;
import entities.DependencyPlaceType;
import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.SmellType;
import entities.stateSpace.Relation;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class RelationCreator {
	Map<SmellType, List<Repair>> repairsMap; 
	
	public RelationCreator(List<SmellType> smellTypes, List<Repair> repairs) {
		super();
		initRepairMap(smellTypes, repairs); 
	}
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE37 //SMELL: #SmellType(Feature Envy)
 public void addRelationsToState(State state){
		
		List<Relation> newRelations = new LinkedList<Relation>();	
		
		for(SmellOccurance so : state.getSmells()){
			newRelations.addAll(this.assignRelationsToSmellOccurance(so));
		}
		
		this.assignFromStateToRelation(newRelations, state);
		state.setRelations(newRelations);
	}// smelltag start : FE37 
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE38 //SMELL: #SmellType(Feature Envy)
 private List<Relation> assignRelationsToSmellOccurance(SmellOccurance smellOccurance){
		
		List<Relation> relations = new ArrayList<Relation>();
		
		
		if(this.repairsMap.containsKey(smellOccurance.getSmell())){
			for(Repair repair : this.repairsMap.get(smellOccurance.getSmell())){
				for(Relation newRel : makeRelationsOfRepair(repair)){
					newRel.setFixedSmellOccurance(smellOccurance);
					relations.add(newRel);
				}
			}
		}
		return relations;
	}// smelltag start : FE38 
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE39 //SMELL: #SmellType(Feature Envy)
 private void assignFromStateToRelation(List<Relation> rels, State state){
		
		for(Relation r : rels){
			r.setFromState(state);
		}		
	}// smelltag start : FE39 	
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE40 //SMELL: #SmellType(Feature Envy)
 private List<Relation> makeRelationsOfRepair(Repair repair){
		
		List<Relation> results = new ArrayList<Relation>();
		
		//make base repair
		results.add(makeBaseRepairRelation(repair));
		
		if(repair instanceof DependencyRepair){
			//results.addAll(makeDependencyRepairRelations((DependencyRepair)repair));
			results.addAll(makeDependencyRepairRelations((DependencyRepair)repair));
		}
		
		
		return results;
	}// smelltag start : FE40 

	//REFACTOR - Feature Envy
  // smelltag end   : FE41 //SMELL: #SmellType(Feature Envy)
 private Relation makeBaseRepairRelation(Repair repair) {		
		Relation relation = new Relation();
		relation.setUsedRepair(new Repair(repair.getName(), repair.getRepairUses()));
		return relation;
	}// smelltag start : FE41 
	
	
	//TODO - fuzzy logic for dependencies
	/*
	 * This method makes the combination of dependencies. 
	 * 
	 * Its mean - if repair causes one smell A and repairs one smells B, 
	 * 	it creates 3 relations: causes A, repairs B, cause A and repairs B. 
	 * 
	 * */
	//REFACTOR - Feature Envy
  // smelltag end   : FE42 //SMELL: #SmellType(Feature Envy)
 //REFACTOR - Long Method
  private List<Relation> makeDependencyRepairRelations(DependencyRepair repair) {
		
		List<Relation> relations = new ArrayList<Relation>(); 
		List<Dependency> dependencies = repair.getDependencies();
				
			
		List<List<Dependency>> combinations = new ArrayList<List<Dependency>>();
		//Create combination of dependecies
		for(int i = 0; i < dependencies.size(); i++){
			combinations(dependencies, i+//REFACTOR - Magic Number
  // smelltag end   : MAGIC32 //SMELL: #SmellType(Magic Numbers)
 1// smelltag start : MAGIC32 , //REFACTOR - Magic Number
  // smelltag end   : MAGIC33 //SMELL: #SmellType(Magic Numbers)
 0// smelltag start : MAGIC33 , new Dependency[i+//REFACTOR - Magic Number
  // smelltag end   : MAGIC34 //SMELL: #SmellType(Magic Numbers)
 1// smelltag start : MAGIC34 ], combinations);
		}
		
		//Every combination is a one Relation
		for(List<Dependency> tempDependencyList : combinations){
			DependencyRepair dependencyRepair = new DependencyRepair(repair.getName(), repair.getRepairUses());
			
			for(Dependency dep : tempDependencyList){
				dependencyRepair.addDependency(dep.getType(), dep.getSmell(), dep.getPropability(), dep.getActionField(), dep.getPlaceType());
			}
			
			Relation rel = new Relation();
			rel.setUsedRepair(dependencyRepair);
			relations.add(rel);
		}
		
				
		return relations;
	}// smelltag start : FE42 
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE43 //SMELL: #SmellType(Feature Envy)
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
            combinations(dependencies, len-//REFACTOR - Magic Number
  // smelltag end   : MAGIC35 //SMELL: #SmellType(Magic Numbers)
 1// smelltag start : MAGIC35 , i+//REFACTOR - Magic Number
  // smelltag end   : MAGIC36 //SMELL: #SmellType(Magic Numbers)
 1// smelltag start : MAGIC36 , results, combinations);
        }
    }// smelltag start : FE43 
	
	/*private static void calculateProbabilityForRelations(List<Relation> rels){
		
		for(Relation rel : rels){
			rel.calculateProbability();
		}
		
	}*/
	
	private void initRepairMap(List<SmellType> smellTypes, List<Repair> repairs) {
		
		this.repairsMap = new HashMap<SmellType, List<Repair>>();	
		
		List<Repair> tempRepairsList;
		for(Repair rep : repairs){
			
			for(SmellType smell : rep.getSmells()){
				
				if(!this.repairsMap.containsKey(smell)){
					
					tempRepairsList = new LinkedList<Repair>(); 
					tempRepairsList.add(rep);
					
					this.repairsMap.put(smell, tempRepairsList);
				}else{
					this.repairsMap.get(smell).add(rep);
				}
			}
		}	
	}
}
