package configuration;

import entities.RefactorProcessOptimizer;


//REFACTOR - Lazy Class
  //SMELL: #SmellType(Lazy Class)
 public class Main {
	
	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 public static void main(String[] args) {	
		RefactorProcessOptimizer model = new RefactorProcessOptimizer();
		model.findRefactoringPath();	
	}
}
