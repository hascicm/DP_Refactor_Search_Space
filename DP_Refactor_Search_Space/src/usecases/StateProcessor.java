package usecases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import entities.DependencyRepair;
import entities.DependencyType;
import entities.Location;
import entities.LocationPart;
import entities.LocationPartType;
import entities.Repair;
import entities.SmellType;
import entities.Dependency;
import entities.DependencyPlaceType;
import entities.stateSpace.Relation;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateProcessor {

	public static State applyRepair(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = applyBasicRepair(baseState, repair, smellOccurance);

		// TODO preprobit na repair.applyOnState() s vyuzitim override
		if (repair instanceof DependencyRepair) {
			applyDependencies(resultState, (DependencyRepair) repair, smellOccurance);
		}

		return resultState;
	}

	public static State applyRepairMonteCarlo(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = applyBasicRepairMonteCarlo(baseState, repair, smellOccurance);

		// TODO preprobit na repair.applyOnState() s vyuzitim override
		if (repair instanceof DependencyRepair) {
			applyDependencies(resultState, (DependencyRepair) repair, smellOccurance);
		}

		return resultState;
	}

	// for the simply repair without dependency just remove smell occurance
	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 private static State applyBasicRepair(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = new State();
		
		List<SmellOccurance> smellOccuranceList = new ArrayList<SmellOccurance>(baseState.getSmells());
		
		smellOccuranceList.remove(smellOccurance);

		/*for (SmellOccurance so : baseState.getSmells()) {
			if (so != smellOccurance) {
				smellOccuranceList.add(so);
			}
		}*/
		
		resultState.setSmells(smellOccuranceList);
		
		return resultState;
	}

	// for the simply repair without dependency just remove smell occurance
	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 private static State applyBasicRepairMonteCarlo(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = State.getMonteCarloStateInstance();

		List<SmellOccurance> smellOccuranceList = new ArrayList<SmellOccurance>();

		for (SmellOccurance so : baseState.getSmells()) {
			if (so != smellOccurance) {
				smellOccuranceList.add(so);
			}
		}

		resultState.setSmells(smellOccuranceList);
		return resultState;
	}

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 private static void applyDependencies(State state, DependencyRepair repair, SmellOccurance smellOccurance) {

		for (Dependency dep : repair.getDependencies()) {

			if (dep.getType() == DependencyType.CAUSE) {
				
				if(dep.getPlaceType() == DependencyPlaceType.INTERNAL){
					SmellOccurance newSmellOccurance = new SmellOccurance(dep.getSmell());
					
					List<LocationPart> newLocationPartList = new ArrayList<LocationPart>();
					
					List<LocationPart> tempLocationPartList = smellOccurance.getLocations().get(0).getLocation(); 
					boolean isFound = false;
					LocationPart currentPart = null;
					
					for(int i = tempLocationPartList.size()-1; i >= 0; i--){
					
						currentPart = tempLocationPartList.get(i);
						
						if(isFound){
						
							newLocationPartList.add(currentPart);
						
						}else{
							
							if(currentPart.getLocationPartType() == dep.getActionField()){
								isFound = true;
								newLocationPartList.add(currentPart);
							}
						}
					}
					
					List<Location> newLocations = new ArrayList<Location>();
					Collections.reverse(newLocationPartList);
					Location loc = new Location(newLocationPartList);
					newLocations.add(loc);
					
					newSmellOccurance.setLocations(newLocations);
				
					state.getSmells().add(newSmellOccurance);
				}
			}

			if (dep.getType() == DependencyType.SOLVE) {
				
				if(dep.getPlaceType() == DependencyPlaceType.INTERNAL){
					SmellOccurance tempSmellOccurance = isOnSameLocation(state, smellOccurance, dep.getSmell(), dep.getActionField());
					/*boolean isSolved = false;
	
					for (SmellOccurance smellOccurance : state.getSmells()) {
						if (smellOccurance.getSmell() == dep.getSmell()) {
	
							tempSmellOccurance = smellOccurance;
							isSolved = true;
							break;
						}
					}
	
					if (isSolved) {
						state.getSmells().remove(tempSmellOccurance);
					}*/
					if(tempSmellOccurance != null){
						state.getSmells().remove(tempSmellOccurance);
					}
				
				}
				
				if(dep.getPlaceType() == DependencyPlaceType.EXTERNAL){
					//TODO!!!!
				}

			}
		}
	}

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 public static long calculateSmellsWeight(State state) {
		long result = 0;

		for (SmellOccurance so : state.getSmells()) {
			result += so.getSmell().getWeight();
		}

		return result;
	}

	//REFACTOR - Long Method
  public static void calculateFitness(State state, long initSmellsWeight) {

		long fitness = 0;

		fitness += (initSmellsWeight - calculateSmellsWeight(state));

		fitness = fitness << //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 2;

		Relation currentRel = state.getSourceRelation();
		int count = 0;
		int weightSum = 0;
		while (currentRel != null) {
			weightSum += currentRel.getUsedRepair().getWeight(currentRel.getFixedSmellOccurance().getSmell());
			count++;
			currentRel = currentRel.getFromState().getSourceRelation();
		}

		if (count != 0)
			fitness += (weightSum / count);

		fitness -= state.getDepth();

		/*
		 * if(state.getSourceRelation() != null){ fitness *=
		 * state.getSourceRelation().getProbability(); }
		 */

		// fitness = (state.getDepth());

		state.setFitness(fitness);
	}

	/*
	 * public static void calculateFitness(State state){
	 * 
	 * int fitness = 0;
	 * 
	 * for(SmellOccurance smellOccurance : state.getSmells()){ fitness +=
	 * smellOccurance.getSmell().getWeight(); }
	 * 
	 * fitness *= 10; fitness = (int) Math.pow(fitness, 3.0);
	 * 
	 * fitness += state.getDepth();
	 * 
	 * State currentState = state; while(currentState.getSourceRelation() !=
	 * null){ fitness +=
	 * currentState.getSourceRelation().getUsedRepair().getWeight(currentState.
	 * getSourceRelation().getFixedSmellOccurance().getSmell()); currentState =
	 * currentState.getSourceRelation().getFromState(); }
	 * 
	 * state.setFitness(fitness); }
	 */

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 //REFACTOR - Long Method
  public static void calculateFitnessForAnts(State state) {
		int fitness = 0;
		float fit = 0;
		for (SmellOccurance smellOccurance : state.getSmells()) {
			fitness += smellOccurance.getSmell().getWeight() * //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 2;
		}
		if (fitness == 0) {
			fitness = //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 1;
		}
		fit = //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 1 / (float) fitness;
		fitness = ((int) (fit * //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 10000));

		State currentState = state;
		while (currentState.getSourceRelation() != null) {
			fitness -= (currentState.getSourceRelation().getUsedRepair()
					.getWeight(currentState.getSourceRelation().getFixedSmellOccurance().getSmell()) * //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 5);
			currentState = currentState.getSourceRelation().getFromState();
		}
		state.setFitness(fitness);
	}

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 public static void initializeState(State state) {
		for (Relation r : state.getRelations()) {
			r.setPheromoneTrail(//REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 2000);
		}
	}

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 public static String createHash(State s) {

		StringBuilder sb = new StringBuilder();

		for (SmellOccurance so : s.getSmells()) {
			sb.append(so.getSmell().getId() + "_");
			for(LocationPart loc : so.getLocations().get(0).getLocation()){
				sb.append(loc.getId()+ "_" + loc.getLocationPartType() + "_" );
			}
		}

		return sb.toString();
	}
	
	public static SmellOccurance isOnSameLocation(State state, SmellOccurance baseSmellOccurance, SmellType smellType, LocationPartType placeType){
		
		SmellOccurance result = null;
		List<LocationPart> tempPath;
		
		for(SmellOccurance smellOccurance : state.getSmells()){
			
			if(smellOccurance != baseSmellOccurance){
				
				if(smellOccurance.getSmell() == smellType){
					
					tempPath = PlaceComparator.findCommonDestinationPath(baseSmellOccurance.getLocations().get(//REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 0).getLocation(), 
																			smellOccurance.getLocations().get(//REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 0).getLocation());
					
					for(int i = tempPath.size()-1; i >=0; i-- ){
						
						if(tempPath.get(i).getLocationPartType() == placeType){
							result = smellOccurance;
							break;
						}

					}
				}
				
			}
		}
		
		
		return result; 
	}
	
}
