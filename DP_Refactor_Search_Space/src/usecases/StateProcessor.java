package usecases;

import java.util.LinkedList;
import java.util.List;

import entities.Repair;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateProcessor {

	public static State applyRepair(State baseState, Repair repair, SmellOccurance smellOccurance){
		
		State resultState = new State();
		
		List<SmellOccurance> temp = new LinkedList<SmellOccurance>();		
		for(SmellOccurance so : baseState.getSmells()){
			if(so != smellOccurance){
				temp.add(so);
			}
		}
		
		resultState.setSmells(temp);
		return resultState;
	}
	
		
	
}
