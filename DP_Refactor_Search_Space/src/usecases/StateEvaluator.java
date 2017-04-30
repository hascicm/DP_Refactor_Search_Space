package usecases;

import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateEvaluator {

	public static void calculateFitness(State state){
		
		int fitness = 0;
		
		for(SmellOccurance smellOccurance : state.getSmells()){
			fitness += smellOccurance.getSmell().getWeight();
		}
		
		state.setFittnes(fitness);
	}
	
}
