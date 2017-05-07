package usecases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.SmellType;
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
	
	private static State applyBasicRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
		State resultState = new State();
		
		//for the simply repair without dependency 
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
					continue;
			}
			
			for(SmellOccurance smellOccurance : tempSmellOccurances){
				state.getSmells().remove(smellOccurance);
			}
			
		}	
	}
	
	
	public static boolean isEquals(State s1, State s2){
		
		if(s1.getSmells().size() == s2.getSmells().size()){
			
			Map<SmellType, Integer> stateMap1 = makeSmellMap(s1);
			Map<SmellType, Integer> stateMap2 = makeSmellMap(s2);
			
			for(SmellType st1 : stateMap1.keySet()){
				
				if(!stateMap2.containsKey(st1)){
					return false;
				}
				
				if(stateMap1.get(st1) != stateMap2.get(st1)){
					return false;
				}
				
			}
			
			return true;
		
		}else{
			return false;
		}
	}
	
	private static Map<SmellType, Integer> makeSmellMap(State s){
		
		Map<SmellType, Integer> map = new HashMap<SmellType, Integer>();
		
		for(SmellOccurance so : s.getSmells()){
			
			if(map.containsKey(so.getSmell())){
				
				int temp = map.get(so.getSmell());
				temp++;
				map.put(so.getSmell(), temp);
						
			}else{
				map.put(so.getSmell(), 1);
			}
			
		}
		
		return map;
	}
	
		
	public static void calculateFitness(State state){
		
		int fitness = 0;
		
		for(SmellOccurance smellOccurance : state.getSmells()){
			fitness += smellOccurance.getSmell().getWeight();
		}
		
		State currentState = state;
		while(currentState.getSourceRelation() != null){
			fitness += currentState.getSourceRelation().getUsedRepair().getWeight();
			currentState = currentState.getSourceRelation().getFromState();
		}
		
		
		state.setFitness(fitness);
	}
}
