package usecases;

import entities.stateSpace.State;

public class StateEvaluator {

	public static void calculateFitness(State state){
		state.setFittnes(state.getSmells().size());
	}
	
}
