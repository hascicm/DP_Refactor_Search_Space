package dataprovider;

import java.util.ArrayList;
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
		
		SmellType smell_longParameterList = new SmellType("LongParameterList", 10);
		SmellType smell_divergentChange = new SmellType("DivergentChange", 8);
		SmellType smell_largeClass = new SmellType("LargeClass", 1);
		SmellType smell_featureEnvy = new SmellType("FeatureEnvy", 4);
		SmellType smell_lazyClass = new SmellType("LazyClass", 1);
		SmellType smell_dataClass = new SmellType("DataClass", 1);
		SmellType smell_dataClumps = new SmellType("DataClumps", 7);
		SmellType smell_incompleteLibraryPath = new SmellType("IncompleteLibraryPath", 5);
		SmellType smell_temporaryField= new SmellType("TemporaryField", 3);
		SmellType smell_longMethod = new SmellType("LongMethod", 1);
		
				
		this.smells.add(smell_longParameterList);
		this.smells.add(smell_divergentChange);
		this.smells.add(smell_largeClass);
		this.smells.add(smell_featureEnvy);
		this.smells.add(smell_lazyClass);
		this.smells.add(smell_dataClass);
		this.smells.add(smell_dataClumps);
		this.smells.add(smell_incompleteLibraryPath);
		this.smells.add(smell_temporaryField);
		this.smells.add(smell_longMethod);
		
		List<SmellType> badClassContent = new ArrayList<SmellType>();
		badClassContent.add(smell_largeClass);
		badClassContent.add(smell_dataClass);
		badClassContent.add(smell_lazyClass);
		badClassContent.add(smell_featureEnvy);
		
		
		
		this.repairs = new LinkedList<Repair>();
		
		DependencyRepair repair_introduceParameterObject_LPL = new DependencyRepair("IntroduceParameterObject_LPL", smell_longParameterList);
		repair_introduceParameterObject_LPL.setWeight(3);
		repair_introduceParameterObject_LPL.addDependency(DependencyType.CAUSE, badClassContent);
		repairs.add(repair_introduceParameterObject_LPL);
		
		Repair repair_preserveWholeObject_LPL = new Repair("PreserveWholeObject_LPL", smell_longParameterList);
		repair_preserveWholeObject_LPL.setWeight(1);
		repairs.add(repair_preserveWholeObject_LPL);
		
		Repair repair_replaceParameterWithMethod_LPL = new Repair("ReplaceParameterWithMethod_LPL", smell_longParameterList);
		repair_replaceParameterWithMethod_LPL.setWeight(3);
		repairs.add(repair_replaceParameterWithMethod_LPL);
		
		DependencyRepair repair_extractClass_DC = new DependencyRepair("ExtractClass_DC", smell_divergentChange);
		repair_extractClass_DC.setWeight(2);
		repair_extractClass_DC.addDependency(DependencyType.CAUSE, badClassContent);
		repair_extractClass_DC.addDependency(DependencyType.SOLVE, smell_largeClass);
		repair_extractClass_DC.addDependency(DependencyType.SOLVE, smell_dataClumps);
		repairs.add(repair_extractClass_DC);
		
		
		DependencyRepair repair_extractClass_LARC = new DependencyRepair("ExtractClass_LARC", smell_largeClass);
		repair_extractClass_LARC.setWeight(1);
		repair_extractClass_LARC.addDependency(DependencyType.CAUSE, badClassContent);
		repair_extractClass_LARC.addDependency(DependencyType.SOLVE, smell_largeClass);
		repair_extractClass_LARC.addDependency(DependencyType.SOLVE, smell_dataClumps);
		repairs.add(repair_extractClass_LARC);
		
		Repair repair_extractIF_LARC = new Repair("ExtractInterface_LARC", smell_largeClass);
		repair_extractIF_LARC.setWeight(4);
		repairs.add(repair_extractIF_LARC);
		
		DependencyRepair repair_extractSubClass_LARC = new DependencyRepair("ExtractSubClass_LARC", smell_largeClass);
		repair_extractSubClass_LARC.setWeight(3);
		repair_extractSubClass_LARC.addDependency(DependencyType.CAUSE, badClassContent);
		repair_extractSubClass_LARC.addDependency(DependencyType.SOLVE, smell_dataClumps);
		repairs.add(repair_extractSubClass_LARC);
		
		
		DependencyRepair repair_MoveMethod_FE = new DependencyRepair("MoveMethod_FE", smell_featureEnvy);
		repair_MoveMethod_FE.setWeight(1);
		repair_MoveMethod_FE.addDependency(DependencyType.CAUSE, badClassContent);
		repair_MoveMethod_FE.addDependency(DependencyType.SOLVE, smell_largeClass);
		repairs.add(repair_MoveMethod_FE);
		
		DependencyRepair repair_extractMethod_FE = new DependencyRepair("ExtractMethod_FE", smell_featureEnvy);
		repair_extractMethod_FE.setWeight(2);
		repair_extractMethod_FE.addDependency(DependencyType.CAUSE, smell_largeClass);
		repair_extractMethod_FE.addDependency(DependencyType.CAUSE, smell_longMethod);
		repair_extractMethod_FE.addDependency(DependencyType.SOLVE, smell_longParameterList);
		repairs.add(repair_extractMethod_FE);
		
		DependencyRepair repair_colapseHierarchy_LAZC = new DependencyRepair("ColapseHierarchy_LAZC", smell_lazyClass);
		repair_colapseHierarchy_LAZC.setWeight(2);
		repair_colapseHierarchy_LAZC.addDependency(DependencyType.CAUSE, smell_largeClass);
		repair_colapseHierarchy_LAZC .addDependency(DependencyType.SOLVE, smell_dataClass);
		repairs.add(repair_colapseHierarchy_LAZC);
		
		DependencyRepair repair_inlineClass_LAZC = new DependencyRepair("InlineClass_LAZC", smell_lazyClass);
		repair_inlineClass_LAZC.setWeight(3);
		repair_inlineClass_LAZC.addDependency(DependencyType.CAUSE, smell_largeClass);
		repair_inlineClass_LAZC .addDependency(DependencyType.SOLVE, smell_dataClass);
		repair_inlineClass_LAZC .addDependency(DependencyType.SOLVE, smell_largeClass);
		repairs.add(repair_inlineClass_LAZC);
		
		Repair repair_introduceForeignMethod_ILC = new Repair("IntroduceForeignMethod_ILC", smell_incompleteLibraryPath);
		repair_introduceForeignMethod_ILC.setWeight(1);
		repairs.add(repair_introduceForeignMethod_ILC);
		
		Repair repair_introduceNullObject_TF = new Repair("IntroduceNullObject_TF", smell_temporaryField);
		repair_introduceNullObject_TF.setWeight(3);
		repairs.add(repair_introduceNullObject_TF );
		
		DependencyRepair repair_extractClass_TF = new DependencyRepair("repair_extractClass_TF", smell_temporaryField);
		repair_extractClass_TF.setWeight(3);
		repair_extractClass_TF.addDependency(DependencyType.CAUSE, badClassContent);
		repair_extractClass_TF.addDependency(DependencyType.SOLVE, smell_largeClass);
		repair_extractClass_TF.addDependency(DependencyType.SOLVE, smell_dataClumps);
		repairs.add(repair_extractClass_TF);
		
		//init root state
		this.root = new State();
		this.root.setSmells(new LinkedList<SmellOccurance>());
		
		this.root.getSmells().add(new SmellOccurance(smell_dataClumps));
		this.root.getSmells().add(new SmellOccurance(smell_lazyClass));
		this.root.getSmells().add(new SmellOccurance(smell_featureEnvy));
		this.root.getSmells().add(new SmellOccurance(smell_incompleteLibraryPath));
		this.root.getSmells().add(new SmellOccurance(smell_longParameterList));		
		this.root.getSmells().add(new SmellOccurance(smell_dataClass));
		this.root.getSmells().add(new SmellOccurance(smell_largeClass));		
		//this.root.getSmells().add(new SmellOccurance(smell_temporaryField));
		
	}
	
}
