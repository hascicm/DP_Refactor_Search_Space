package configuration;

import entities.RefactorProcessOptimizer;


public class Main {
	
	public static void main(String[] args) {	
		RefactorProcessOptimizer model = new RefactorProcessOptimizer();
		model.findRefactoringPath();	
	}
}
