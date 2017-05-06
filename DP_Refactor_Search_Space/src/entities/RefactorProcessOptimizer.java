package entities;

import dataprovider.BasicDataProvider;
import dataprovider.DataProvider;
import entities.stateSpace.State;
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
		this.dataProvider = new BasicDataProvider();
		this.pathSearchStrategy = new DefaultPathSearchStrategy(new RelationCreator(this.dataProvider.getSmellTypes(), this.dataProvider.getRepairs()));
		
	}

	public void findRefactoringPath(){
		MultiAgent ma = new MultiAgent();
		ma.findPath(this.dataProvider.getRootState(), this.pathSearchStrategy);
		
	}
	
	public void setPathSearchStrategy(PathSearchStrategy pathSearchStrategy) {
		this.pathSearchStrategy = pathSearchStrategy;
	}
	
}
