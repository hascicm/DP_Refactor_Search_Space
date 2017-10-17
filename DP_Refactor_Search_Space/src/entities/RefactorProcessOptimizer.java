package entities;

import dataprovider.BasicDataProvider;
import dataprovider.DataProvider;
import dataprovider.DatabaseDataProvider;
import usecases.BeePathSearchStrategy;
import usecases.DefaultPathSearchStrategy;
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
		this.dataProvider = new DatabaseDataProvider();
		//this.pathSearchStrategy = new DefaultPathSearchStrategy(new RelationCreator(this.dataProvider.getSmellTypes(), this.dataProvider.getRepairs()));
		this.pathSearchStrategy = new BeePathSearchStrategy(new RelationCreator(this.dataProvider.getSmellTypes(), this.dataProvider.getRepairs()));
	}

	public void findRefactoringPath(){
		//MultiAgent ma = new MultiAgent();
		//ma.findPath(this.dataProvider.getRootState(), this.pathSearchStrategy);
		Long startTime = System.currentTimeMillis();
		this.pathSearchStrategy.findPath(this.dataProvider.getRootState(), 0);
		System.out.println("");
		System.out.println("Time: ");
		System.out.println(((startTime - System.currentTimeMillis())/1000.0));
	}
	
	public void setPathSearchStrategy(PathSearchStrategy pathSearchStrategy) {
		this.pathSearchStrategy = pathSearchStrategy;
	}
	
}
