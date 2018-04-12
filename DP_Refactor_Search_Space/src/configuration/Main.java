package configuration;

import entities.RefactorProcessOptimizer;


//REFACTOR - Lazy Class
  // smelltag end   : LAZC1 //SMELL: #SmellType(Lazy Class)
 public class Main {
	
	//REFACTOR - Feature Envy
  // smelltag end   : FE1 //SMELL: #SmellType(Feature Envy)
 public static void main(String[] args) {	
		RefactorProcessOptimizer model = new RefactorProcessOptimizer();
		model.findRefactoringPath();	
	}// smelltag start : FE1 
}// smelltag start : LAZC1 
