package usecases;

import java.util.ArrayList;
import java.util.Collection;
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
			results.addAll(makeDependencyRepairRelations((DependencyRepair)repair));
		}
		
		
		return results;
	}

	/*TODO:
	 *  Metoda vytvori kombinaciu
	 *  1. SOLVE
	 *  2. CAUSE
	 *  3. SOLVE && CAUSE 
	 * 
	 * Ak je to mozne. 
	 * 
	 * TODO !!!Prerobit na nieco krajsie. :(
	 */
	private List<Relation> makeDependencyRepairRelations(DependencyRepair repair) {
		
		List<Relation> relations = new ArrayList<Relation>();
		
		if(repair.getDependencies().containsKey(DependencyType.CAUSE) 
				&& repair.getDependencies().containsKey(DependencyType.SOLVE)){
			
			DependencyRepair rep = new DependencyRepair(repair.getName(), repair.getSmells());
			rep.addDependency(DependencyType.CAUSE, repair.getDependencies().get(DependencyType.CAUSE));
			rep.addDependency(DependencyType.SOLVE, repair.getDependencies().get(DependencyType.SOLVE));
			
			Relation rel = new Relation();
			rel.setUsedRepair(rep);
			relations.add(rel);
			return relations;
		}
		
		if(repair.getDependencies().containsKey(DependencyType.CAUSE) ){
			
			DependencyRepair rep = new DependencyRepair(repair.getName(), repair.getSmells());
			rep.addDependency(DependencyType.CAUSE, repair.getDependencies().get(DependencyType.CAUSE));
			
			Relation rel = new Relation();
			rel.setUsedRepair(rep);
			relations.add(rel);
			return relations;
		}
		
		if(repair.getDependencies().containsKey(DependencyType.SOLVE) ){
			
			DependencyRepair rep = new DependencyRepair(repair.getName(), repair.getSmells());
			rep.addDependency(DependencyType.SOLVE, repair.getDependencies().get(DependencyType.SOLVE));
			
			Relation rel = new Relation();
			rel.setUsedRepair(rep);
			relations.add(rel);
			return relations;
		}
		
		
		return relations;
	}

	private Relation makeBaseRepairRelation(Repair repair) {
		
		Relation relation = new Relation();
		relation.setUsedRepair(new Repair(repair.getName(), repair.getSmells()));
		return relation;
	}
}
