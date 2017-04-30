package usecases;

import java.util.ArrayList;
import java.util.List;

import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateProcessor {

	public static State applyRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
		State resultState = applyBasicRepair(baseState, repair, smellOccurance);
		
		//Is it possible to change to Visitor pattern (repair.visit(state))
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
	
	public static void applyDependencyRepair(State baseState, DependencyRepair repair){
		
		if(repair.getDependencyType() == DependencyType.CAUSE){
			
			//List<Smell>
			
			
		}
		
		
		
	}
	
	
}
