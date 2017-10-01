package usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.stateSpace.Relation;
import entities.stateSpace.State;

public class AntColonyPathSearch extends PathSearchStrategy {

	private List<Ant> ants;
	private static boolean done = false;
	private static int x = 0;
	private State bestState;

	public AntColonyPathSearch(RelationCreator relationCreator) {
		super(relationCreator);
		this.ants = new ArrayList<Ant>();

		for (int i = 0; i < 100; i++)
			ants.add(new Ant());

	}

	@Override
	public List<Relation> findPath(State rootState, int depth) {

		for (Ant a : ants)
			reinitializeAnt(a, rootState);

		while (x < 1000) {
			x++;
			for (Ant a : ants) {
				// if there is a possible way
//				System.out.println(a.currentState.toString());
				if (a.finalState == null) {
					expandCurrentState(a.currentState);
					makeAntMove(a);
				} else {
					// Ant has returned to root
					if (a.currentState == rootState) {
						reinitializeAnt(a, rootState);
					} else {
						// backtrack to root
						backtrackToRoot(a);
					}
				}
			}
		}

		return null;

	}

	private void reinitializeAnt(Ant a, State rootState) {
		a.finalState = null;
		a.currentState = rootState;
	}

	public void makeAntMove(Ant ant) {

		State currentState = ant.currentState;

		if (!currentState.getRelations().isEmpty()) {
			Relation nextRelation = rouletteWheel(ant.currentState.getRelations());
			ant.currentState = nextRelation.getToState();
		} else {
			ant.setFinalState(currentState);
			// TODO heuristic
			// System.out.println("a");
			if (bestState == null || ant.getFinalState().getFitness() < bestState.getFitness()) {
				bestState = ant.getFinalState();
				System.out.println("new best state " + bestState.toString());
			}
			ant.setPheromone(100);

		}
	}

	public void backtrackToRoot(Ant ant) {
		State state = ant.getFinalState();
		Relation relation = state.getSourceRelation();
		// TODO evaporation
		relation.setPheromoneTrail(ant.pheromone);
		ant.currentState = relation.getFromState();

	}

	private Relation rouletteWheel(List<Relation> posibleMoves) {
		int sum = 0;
		int partialsum = 0;
		Relation output = null;

		for (Relation r : posibleMoves) {
			sum += r.getPheromoneTrail();
		}
		int x = new Random().nextInt(sum);

		for (Relation r : posibleMoves) {
			partialsum += r.getPheromoneTrail();
			output = r;
			if (partialsum > x)
				break;
		}
		return output;
	}

	private class Ant {
		private int pheromone;
		private State finalState;
		private State currentState;

		public State getFinalState() {
			return finalState;
		}

		public void setFinalState(State finalState) {
			this.finalState = finalState;
		}

		public int getPheromone() {
			return pheromone;
		}

		public void setPheromone(int pheromone) {
			this.pheromone = pheromone;
		}

	}
}
