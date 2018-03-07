package entities;

import dataprovider.BasicDataProvider;
import dataprovider.DataProvider;
import dataprovider.DatabaseDataProvider;
import usecases.BeePathSearchStrategy;
import usecases.DefaultPathSearchStrategy;
import usecases.MinMaxProbabilityCalculationStrategy;
import usecases.MultiAgent;
import usecases.PathSearchStrategy;
import usecases.RelationCreator;

public class RefactorProcessOptimizer {
	
	private DataProvider dataProvider;
	private PathSearchStrategy pathSearchStrategy;
		
	public RefactorProcessOptimizer(){
		init();	
	}

	private void init() {
		//this.dataProvider = new DatabaseDataProvider();
		this.dataProvider = new BasicDataProvider(); 
		this.pathSearchStrategy = new DefaultPathSearchStrategy(new RelationCreator(this.dataProvider.getSmellTypes(), this.dataProvider.getRepairs()));
		//this.pathSearchStrategy = new BeePathSearchStrategy(new RelationCreator(this.dataProvider.getSmellTypes(), this.dataProvider.getRepairs()));
	
		//Probability Calculation Strategy
		this.pathSearchStrategy.setProbabolityCalculationStrategy(new MinMaxProbabilityCalculationStrategy());
	}

	public void findRefactoringPath(){
		//MultiAgent ma = new MultiAgent();
		//ma.findPath(this.dataProvider.getRootState(), this.pathSearchStrategy);
		Long startTime = System.currentTimeMillis();
		this.pathSearchStrategy.findPath(this.dataProvider.getRootState(), //REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 0);
		System.out.println("");
		System.out.println("Time: ");
		System.out.println(((startTime - System.currentTimeMillis())///REFACTOR - Magic Number
  //SMELL: #SmellType(Magic Numbers)
 1000.0));
	}
	
	public void setPathSearchStrategy(PathSearchStrategy pathSearchStrategy) {
		this.pathSearchStrategy = pathSearchStrategy;
	}
	
}
