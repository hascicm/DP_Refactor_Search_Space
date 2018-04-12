package dataprovider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import entities.DependencyPlaceType;
import entities.DependencyRepair;
import entities.DependencyType;
import entities.Location;
import entities.LocationPart;
import entities.LocationPartType;
import entities.Repair;
import entities.SmellType;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

//REFACTOR - Lazy Class
  // smelltag end   : LAZC2 //SMELL: #SmellType(Lazy Class)
 //REFACTOR - Data Class
  // smelltag end   : DC1 //SMELL: #SmellType(Data Class)
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
		
		/*SmellType smell_longParameterList = new SmellType(1, "LongParameterList", 10);
		SmellType smell_divergentChange = new SmellType(2, "DivergentChange", 8);
		SmellType smell_largeClass = new SmellType(3, "LargeClass", 1);
		SmellType smell_featureEnvy = new SmellType(4, "FeatureEnvy", 4);
		SmellType smell_lazyClass = new SmellType(5, "LazyClass", 1);
		SmellType smell_dataClass = new SmellType(6, "DataClass", 1);
		SmellType smell_dataClumps = new SmellType(7, "DataClumps", 7);
		SmellType smell_incompleteLibraryPath = new SmellType(8, "IncompleteLibraryPath", 5);
		SmellType smell_temporaryField= new SmellType(9, "TemporaryField", 3);
		SmellType smell_longMethod = new SmellType(10, "LongMethod", 1);*/
		
		//DEBUG
		SmellType smell_A = new SmellType(1, "Smell_A", 10);
		SmellType smell_B = new SmellType(2, "Smell_B", 10);
		//DEBUG
		
				
		/*this.smells.add(smell_longParameterList);
		this.smells.add(smell_divergentChange);
		this.smells.add(smell_largeClass);
		this.smells.add(smell_featureEnvy);
		this.smells.add(smell_lazyClass);
		this.smells.add(smell_dataClass);
		this.smells.add(smell_dataClumps);
		this.smells.add(smell_incompleteLibraryPath);
		this.smells.add(smell_temporaryField);
		this.smells.add(smell_longMethod);*/
		
		this.smells.add(smell_A);
		this.smells.add(smell_B);
		
		//TODO - check if it works correctly 
		/*List<SmellType> badClassContent = new ArrayList<SmellType>();
		badClassContent.add(smell_largeClass);
		badClassContent.add(smell_dataClass);
		badClassContent.add(smell_lazyClass);
		badClassContent.add(smell_featureEnvy);*/
		
		
		
		this.repairs = new LinkedList<Repair>();
		
		//DEBUG
		DependencyRepair repair_A = new DependencyRepair("Repair_A");
		repair_A.addSmellCoverage(smell_A, //REFACTOR - Magic Number
  // smelltag end   : MAGIC1 //SMELL: #SmellType(Magic Numbers)
 5// smelltag start : MAGIC1 );
		repair_A.addDependency(DependencyType.SOLVE, smell_B, //REFACTOR - Magic Number
  // smelltag end   : MAGIC2 //SMELL: #SmellType(Magic Numbers)
 0.8// smelltag start : MAGIC2 , LocationPartType.PACKAGE, DependencyPlaceType.INTERNAL);
		repairs.add(repair_A);
		
		DependencyRepair repair_B = new DependencyRepair("Repair_B");
		repair_B.addSmellCoverage(smell_B, //REFACTOR - Magic Number
  // smelltag end   : MAGIC3 //SMELL: #SmellType(Magic Numbers)
 5// smelltag start : MAGIC3 );
		repair_B.addDependency(DependencyType.CAUSE, smell_A, //REFACTOR - Magic Number
  // smelltag end   : MAGIC4 //SMELL: #SmellType(Magic Numbers)
 0.8// smelltag start : MAGIC4 , LocationPartType.PACKAGE, DependencyPlaceType.INTERNAL);
		repairs.add(repair_B);
		
		//DEBUG
		
		/*DependencyRepair repair_introduceParameterObject = new DependencyRepair("IntroduceParameterObject");
		repair_introduceParameterObject.addSmellCoverage(smell_longParameterList, 3);
		repair_introduceParameterObject.addDependency(DependencyType.CAUSE, smell_largeClass, 0.5);
		repairs.add(repair_introduceParameterObject);*/
		
		/*Repair repair_preserveWholeObject = new Repair("PreserveWholeObject");
		repair_preserveWholeObject.addSmellCoverage(smell_longParameterList, 1);
		repairs.add(repair_preserveWholeObject);*/
		
		
		/*Repair repair_replaceParameterWithMethod = new Repair("ReplaceParameterWithMethod");
		repair_replaceParameterWithMethod.addSmellCoverage(smell_longParameterList, 3); 
		repairs.add(repair_replaceParameterWithMethod);*/
		
		/*DependencyRepair repair_extractClass = new DependencyRepair("ExtractClass");
		repair_extractClass.addSmellCoverage(smell_divergentChange, 2);
		repair_extractClass.addSmellCoverage(smell_largeClass, 1);
		repair_extractClass.addSmellCoverage(smell_temporaryField, 3);
		repair_extractClass.addDependency(DependencyType.CAUSE, badClassContent);
		repair_extractClass.addDependency(DependencyType.SOLVE, smell_largeClass);*/
		/*repair_extractClass.addDependency(DependencyType.CAUSE, smell_dataClass, 0.5);
		repair_extractClass.addDependency(DependencyType.CAUSE, smell_largeClass, 0.5);
		repair_extractClass.addDependency(DependencyType.SOLVE, smell_dataClumps, 0.5);
		repairs.add(repair_extractClass);*/
			
		/*Repair repair_extractIF = new Repair("ExtractInterface");
		repair_extractIF.addSmellCoverage(smell_largeClass, 4);
		repairs.add(repair_extractIF);*/
		
		/*DependencyRepair repair_extractSubClass = new DependencyRepair("ExtractSubClass");
		repair_extractSubClass.addSmellCoverage(smell_largeClass, 3);
		/*repair_extractSubClass_LARC.addDependency(DependencyType.CAUSE, badClassContent);*/
		/*.addDependency(DependencyType.CAUSE, smell_dataClass, 0.5);
		repair_extractSubClass.addDependency(DependencyType.CAUSE, smell_largeClass, 0.5);
		repair_extractSubClass.addDependency(DependencyType.SOLVE, smell_dataClumps, 0.5);
		repairs.add(repair_extractSubClass);*/
		
		/*DependencyRepair repair_MoveMethod = new DependencyRepair("MoveMethod");
		repair_MoveMethod.addSmellCoverage(smell_featureEnvy, 1);
		/*repair_MoveMethod_FE.addDependency(DependencyType.CAUSE, badClassContent);*/
		/*repair_MoveMethod.addDependency(DependencyType.SOLVE, smell_largeClass, 0.5);
		repair_MoveMethod.addDependency(DependencyType.CAUSE, smell_lazyClass, 0.5);
		repairs.add(repair_MoveMethod); */
		
		/*DependencyRepair repair_extractMethod = new DependencyRepair("ExtractMethod");
		repair_extractMethod.addSmellCoverage(smell_featureEnvy, 2);
		repair_extractMethod.addDependency(DependencyType.CAUSE, smell_largeClass, 0.5);
		repair_extractMethod.addDependency(DependencyType.CAUSE, smell_longMethod, 0.5);
		repair_extractMethod.addDependency(DependencyType.CAUSE, smell_lazyClass, 0.5);
		repair_extractMethod.addDependency(DependencyType.SOLVE, smell_longParameterList, 0.5);
		repairs.add(repair_extractMethod);*/
		
		/*DependencyRepair repair_colapseHierarchy = new DependencyRepair("ColapseHierarchy");		
		repair_colapseHierarchy.addSmellCoverage(smell_lazyClass, 2);
		repair_colapseHierarchy.addDependency(DependencyType.CAUSE, smell_largeClass, 0.5);
		repair_colapseHierarchy.addDependency(DependencyType.SOLVE, smell_dataClass, 0.5);
		repairs.add(repair_colapseHierarchy);
				
		DependencyRepair repair_inlineClass = new DependencyRepair("InlineClass");
		repair_inlineClass.addSmellCoverage(smell_lazyClass, 3);
		repair_inlineClass.addDependency(DependencyType.CAUSE, smell_largeClass, 0.5);
		repair_inlineClass.addDependency(DependencyType.SOLVE, smell_dataClass, 0.5);
		repair_inlineClass.addDependency(DependencyType.SOLVE, smell_largeClass, 0.5);
		repairs.add(repair_inlineClass);
		
		Repair repair_introduceForeignMethod = new Repair("IntroduceForeignMethod");
		repair_introduceForeignMethod.addSmellCoverage(smell_incompleteLibraryPath, 1);
		repairs.add(repair_introduceForeignMethod);
		
		Repair repair_introduceNullObject = new Repair("IntroduceNullObject");
		repair_introduceNullObject.addSmellCoverage(smell_temporaryField, 3); 
		repairs.add(repair_introduceNullObject);*/
		
		
		
		
		
		
		//init root state
		this.root = new State();
		this.root.setSmells(new LinkedList<SmellOccurance>());
		
		
		List<LocationPart> locationParts = new ArrayList<LocationPart>();
		locationParts.add(new LocationPart(LocationPartType.PACKAGE, "default"));
		locationParts.add(new LocationPart(LocationPartType.CLASS, "Class_A"));
		
		this.root.getSmells().add(new SmellOccurance(smell_A));
		this.root.getSmells().get(//REFACTOR - Magic Number
  // smelltag end   : MAGIC5 //SMELL: #SmellType(Magic Numbers)
 0// smelltag start : MAGIC5 ).getLocations().add(new Location(locationParts));
		
		locationParts = new ArrayList<LocationPart>();
		locationParts.add(new LocationPart(LocationPartType.PACKAGE, "default"));
		locationParts.add(new LocationPart(LocationPartType.CLASS, "Class_B"));
		
		this.root.getSmells().add(new SmellOccurance(smell_B));
		this.root.getSmells().get(//REFACTOR - Magic Number
  // smelltag end   : MAGIC6 //SMELL: #SmellType(Magic Numbers)
 1// smelltag start : MAGIC6 ).getLocations().add(new Location(locationParts));
		
		//System.out.println(this.root.getSmells().get(0).getLocations().get(0).toString());
		
		/*this.root.getSmells().add(new SmellOccurance(smell_dataClumps));
		this.root.getSmells().add(new SmellOccurance(smell_lazyClass));
		this.root.getSmells().add(new SmellOccurance(smell_featureEnvy));
		this.root.getSmells().add(new SmellOccurance(smell_incompleteLibraryPath));
		this.root.getSmells().add(new SmellOccurance(smell_longParameterList));		
		this.root.getSmells().add(new SmellOccurance(smell_dataClass));
		this.root.getSmells().add(new SmellOccurance(smell_largeClass));*/
		
		/*this.root.getSmells().add(new SmellOccurance(smell_dataClumps));
		this.root.getSmells().add(new SmellOccurance(smell_lazyClass));
		this.root.getSmells().add(new SmellOccurance(smell_featureEnvy));
		this.root.getSmells().add(new SmellOccurance(smell_incompleteLibraryPath));
		this.root.getSmells().add(new SmellOccurance(smell_longParameterList));		
		this.root.getSmells().add(new SmellOccurance(smell_dataClass));
		this.root.getSmells().add(new SmellOccurance(smell_largeClass));*/
		//this.root.getSmells().add(new SmellOccurance(smell_temporaryField));
		
	}
	
}// smelltag start : DC1 // smelltag start : LAZC2 
