package usecases;

import java.util.ArrayList;
import java.util.List;

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
			applyDependencyRepair(resultState, (DependencyRepair) repair);
		}
		
		return resultState;
	}
	
	public static State applyBasicRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
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
	
	public static void applyDependencyRepair(State state, DependencyRepair repair){
		
		if(repair.getDependencyType() == DependencyType.CAUSE){
			
			List<SmellOccurance> newSmellOccurances = new ArrayList<SmellOccurance>();
			
			for(SmellType smellType : repair.getRelatedSmells()){
				newSmellOccurances.add(new SmellOccurance(smellType));
			}
			
			state.getSmells().addAll(newSmellOccurances);
		}
		
		if(repair.getDependencyType() == DependencyType.SOLVE){
			
			List<SmellOccurance> tempSmellOccurances = new ArrayList<SmellOccurance>();
			for(SmellType smellType : repair.getRelatedSmells()){
				
				boolean isSolved = false;
				
				for(SmellOccurance smellOccurance : state.getSmells()){
				
					if(smellOccurance.getSmell() == smellType){
					
						tempSmellOccurances.add(smellOccurance);
						isSolved = true;
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
	
	
}
