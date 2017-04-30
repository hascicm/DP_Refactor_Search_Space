package dataprovider;

import java.util.LinkedList;
import java.util.List;

import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.SmellType;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class BasicDataProvider implements DataProvider{

	private List<Repair> repairs;
	private List<SmellType> smells;
	private State root;
	
	@Override
	public List<Repair> getRepairs() {
		
		return this.repairs;
	}

	@Override
	public List<SmellType> getSmellTypes() {
		return this.smells;
	}

	@Override
	public State getRootState() {
		return this.root;
	}
	
	public BasicDataProvider(){
			
		this.smells = new LinkedList<SmellType>();
		
		SmellType smell_1 = new SmellType("Smell_1");
		SmellType smell_2 = new SmellType("Smell_2");
		SmellType smell_3 = new SmellType("Smell_3");
		SmellType smell_4 = new SmellType("Smell_4");
		
		
		smell_2.setWeight(8);
		smell_3.setWeight(2);
		
		this.smells.add(smell_1);
		this.smells.add(smell_2);
		this.smells.add(smell_3);
		this.smells.add(smell_4);
		
		this.repairs = new LinkedList<Repair>();
		
		List<SmellType> r1_smells = new LinkedList<SmellType>();
		r1_smells.add(smell_1);
		Repair repair_1 = new Repair("Repair_1", r1_smells);
		repair_1.setWeight(3);
		
		List<SmellType> r2_smells = new LinkedList<SmellType>();
		r2_smells.add(smell_2);
		Repair repair_2 = new Repair("Repair_2", r2_smells);
		repair_2.setWeight(5);
		
		List<SmellType> r3_smells = new LinkedList<SmellType>();
		r3_smells.add(smell_2);
		DependencyRepair repair_3 = new DependencyRepair("Repair_3", r3_smells, DependencyType.CAUSE);
		repair_3.setWeight(1);
		List<SmellType> r3_smells_related = new LinkedList<SmellType>();
		r3_smells_related.add(smell_3);
		repair_3.setRelatedSmells(r3_smells_related);
		
		List<SmellType> r4_smells = new LinkedList<SmellType>();
		r4_smells.add(smell_1);
		DependencyRepair repair_4 = new DependencyRepair("Repair_4", r4_smells, DependencyType.SOLVE);
		
		List<SmellType> r4_smells_related = new LinkedList<SmellType>();
		r4_smells_related.add(smell_3);
		repair_4.setRelatedSmells(r4_smells_related);
		
		
		this.repairs.add(repair_1);
		this.repairs.add(repair_2);
		this.repairs.add(repair_3);
		this.repairs.add(repair_4);
		
		//init root state
		this.root = new State();
		this.root.setSmells(new LinkedList<SmellOccurance>());
		this.root.getSmells().add(new SmellOccurance(smell_1));
		this.root.getSmells().add(new SmellOccurance(smell_2));
		
		
	}
	
}
