package configuration;

import entities.RefactorProcessOptimizer;


public class Main {
	
	public static void main(String[] args) {	
		Long startTime = System.currentTimeMillis();
		
		RefactorProcessOptimizer model = new RefactorProcessOptimizer();
		model.findRefactoringPath();
		
		System.out.println("");
		System.out.println("Time: ");
		System.out.println(((startTime - System.currentTimeMillis())/1000.0));
	}
}
