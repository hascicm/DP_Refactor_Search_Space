package usecases;

import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.SmellType;
import entities.stateSpace.Relation;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateProcessor {

	public static State applyRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
		State resultState = applyBasicRepair(baseState, repair, smellOccurance);
		
		//TODO preprobit na repair.applyOnState() s vyuzitim override
		if(repair instanceof DependencyRepair){
			applyDependencies(resultState, (DependencyRepair) repair);
		}
		
		return resultState;
	}
	
	//for the simply repair without dependency just remove smell occurance
	private static State applyBasicRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
		State resultState = new State();
		
		List<SmellOccurance> smellOccuranceList = new ArrayList<SmellOccurance>();		
		
		for(SmellOccurance so : baseState.getSmells()){
			if(so != smellOccurance){
				smellOccuranceList.add(so);
			}
		}
		
		resultState.setSmells(smellOccuranceList);
		return resultState;
	}
	
	private static void applyDependencies(State state, DependencyRepair repair){
		
		if(repair.getDependencies().containsKey(DependencyType.CAUSE)){
			
			List<SmellOccurance> newSmellOccurances = new ArrayList<SmellOccurance>();
			
			for(SmellType smellType : repair.getDependencies().get(DependencyType.CAUSE)){
				newSmellOccurances.add(new SmellOccurance(smellType));
			}
			
			state.getSmells().addAll(newSmellOccurances);
		}
		
		if(repair.getDependencies().containsKey(DependencyType.SOLVE)){
			
			List<SmellOccurance> tempSmellOccurances = new ArrayList<SmellOccurance>();
			for(SmellType smellType : repair.getDependencies().get(DependencyType.SOLVE)){
				
				boolean isSolved = false;
				
				for(SmellOccurance smellOccurance : state.getSmells()){
				
					if(smellOccurance.getSmell() == smellType){
					
						tempSmellOccurances.add(smellOccurance);
						isSolved = true;
						break;
					}
					
				}
				
				if(isSolved)
					break;
			}
			
			for(SmellOccurance smellOccurance : tempSmellOccurances){
				state.getSmells().remove(smellOccurance);
			}
			
		}	
	}
			
	public static void calculateFitness(State state){
		
		int fitness = 0;
		
		for(SmellOccurance smellOccurance : state.getSmells()){
			fitness += smellOccurance.getSmell().getWeight();
		}
		
		fitness *= 10; 
		fitness = (int) Math.pow(fitness, 3.0);
		
		fitness += state.getDepth();
		
		State currentState = state;
		while(currentState.getSourceRelation() != null){
			fitness += currentState.getSourceRelation().getUsedRepair().getWeight();
			currentState = currentState.getSourceRelation().getFromState();
		}
			
		state.setFitness(fitness);
	}

	public static void calculateFitnessForAnts(State state) {
		int fitness = 0;
		float fit = 0;
		for (SmellOccurance smellOccurance : state.getSmells()) {
			fitness += smellOccurance.getSmell().getWeight() * 2;
		}
		if (fitness == 0) {
			fitness = 1;
		}
		fit = 1 / (float) fitness;
		fitness = ((int) (fit * 10000));
		
		State currentState = state;
		while (currentState.getSourceRelation() != null) {
			fitness -= currentState.getSourceRelation().getUsedRepair().getWeight();
			currentState = currentState.getSourceRelation().getFromState();
		}
		state.setFitness(fitness);
	}
	
	public static void initializeState(State state){
		for (Relation r : state.getRelations()){
			r.setPheromoneTrail(20);
		}
	}
	public static String createHash(State s){
		
		StringBuilder sb = new StringBuilder();
		
		for(SmellOccurance so : s.getSmells()){
			sb.append(so.getSmell().getId() + "_");
		}
		
		return sb.toString();
	}
}
