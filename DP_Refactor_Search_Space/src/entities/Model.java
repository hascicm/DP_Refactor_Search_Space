package entities;

import dataprovider.BasicDataProvider;
import dataprovider.DataProvider;
import entities.stateSpace.State;
import usecases.DefaultPathSearchStrategy;
import usecases.PathSearchStrategy;
import usecases.RelationCreator;
import usecases.StateEvaluator;

public class Model {
	private State root; 
	
	private DataProvider dataProvider;
	private PathSearchStrategy pathSearchStrategy;
	private RelationCreator relationCreator;
	
	public Model(){
		init();	
	}

	private void init() {
		this.dataProvider = new BasicDataProvider();
		this.root = this.dataProvider.getRootState();
		this.relationCreator = new RelationCreator(this.dataProvider.getSmellTypes(), this.dataProvider.getRepairs());
		this.pathSearchStrategy = new DefaultPathSearchStrategy();
			
	}

	public void findRefactoringPath(){
		
		this.pathSearchStrategy.findPath(this.root, this.relationCreator);
		
	}
	
	public void setPathSearchStrategy(PathSearchStrategy pathSearchStrategy) {
		this.pathSearchStrategy = pathSearchStrategy;
	}
	
}
