package configuration;

import entities.RefactorProcessOptimizer;


//REFACTOR - Lazy Class
  public class Main {
	
	//REFACTOR - Feature Envy
  public static void main(String[] args) {	
		RefactorProcessOptimizer model = new RefactorProcessOptimizer();
		model.findRefactoringPath();	
	}
}
