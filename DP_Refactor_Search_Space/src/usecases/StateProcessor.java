package usecases;

import java.util.ArrayList;
import java.util.List;
import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.Dependency;
import entities.stateSpace.Relation;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class StateProcessor {

	public static State applyRepair(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = applyBasicRepair(baseState, repair, smellOccurance);

		// TODO preprobit na repair.applyOnState() s vyuzitim override
		if (repair instanceof DependencyRepair) {
			applyDependencies(resultState, (DependencyRepair) repair);
		}

		return resultState;
	}

	public static State applyRepairMonteCarlo(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = applyBasicRepairMonteCarlo(baseState, repair, smellOccurance);

		// TODO preprobit na repair.applyOnState() s vyuzitim override
		if (repair instanceof DependencyRepair) {
			applyDependencies(resultState, (DependencyRepair) repair);
		}

		return resultState;
	}

	// for the simply repair without dependency just remove smell occurance
	private static State applyBasicRepair(State baseState, Repair repair, SmellOccurance smellOccurance) {

		State resultState = new State();

		List<SmellOccurance> smellOccuranceList = new ArrayList<SmellOccurance>();

		for (SmellOccurance so : baseState.getSmells()) {
			if (so != smellOccurance) {
				smellOccuranceList.add(so);
			}
		}

		resultState.setSmells(smellOccuranceList);
		return resultState;
	}

	// for the simply repair without dependency just remove smell occurance
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

	private static void applyDependencies(State state, DependencyRepair repair) {

		for (Dependency dep : repair.getDependencies()) {

			if (dep.getType() == DependencyType.CAUSE) {
				state.getSmells().add(new SmellOccurance(dep.getSmell()));
			}

			if (dep.getType() == DependencyType.SOLVE) {

				SmellOccurance tempSmellOccurance = null;
				boolean isSolved = false;

				for (SmellOccurance smellOccurance : state.getSmells()) {
					if (smellOccurance.getSmell() == dep.getSmell()) {

						tempSmellOccurance = smellOccurance;
						isSolved = true;
						break;
					}
				}

				if (isSolved) {
					state.getSmells().remove(tempSmellOccurance);
				}

			}
		}
	}

	public static long calculateSmellsWeight(State state) {
		long result = 0;

		for (SmellOccurance so : state.getSmells()) {
			result += so.getSmell().getWeight();
		}

		return result;
	}

	public static void calculateFitness(State state, long initSmellsWeight) {

		long fitness = 0;

		fitness += (initSmellsWeight - calculateSmellsWeight(state));

		fitness = fitness << 2;

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
			fitness -= (currentState.getSourceRelation().getUsedRepair()
					.getWeight(currentState.getSourceRelation().getFixedSmellOccurance().getSmell()) * 5);
			currentState = currentState.getSourceRelation().getFromState();
		}
		state.setFitness(fitness);
	}

	public static void initializeState(State state) {
		for (Relation r : state.getRelations()) {
			r.setPheromoneTrail(2000);
		}
	}

	public static String createHash(State s) {

		StringBuilder sb = new StringBuilder();

		for (SmellOccurance so : s.getSmells()) {
			sb.append(so.getSmell().getId() + "_");
		}

		return sb.toString();
	}
}
