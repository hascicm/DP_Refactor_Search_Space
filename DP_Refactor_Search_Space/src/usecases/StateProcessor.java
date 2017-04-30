package usecases;

import java.util.ArrayList;
import java.util.List;

import entities.Repair;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateProcessor {

	public static State applyRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
		//TODO different logic for simple/solve/cause repair
		
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
	
		
	
}
