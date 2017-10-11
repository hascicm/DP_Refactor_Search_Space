package entities;

import java.util.ArrayList;
import java.util.List;

public class DependencyRepair extends Repair {
	
	//private Map<DependencyType, List<SmellType>> dependencies;
	private List<Dependency> dependencies; 
	
	public DependencyRepair(String name, List<SmellType> smells) {
		super(name, smells);
		this.dependencies = new ArrayList<Dependency>();
	}
		
	public DependencyRepair(String name, SmellType smell) {
		super(name, smell);
		this.dependencies = new ArrayList<Dependency>();
	}
	
	public List<Dependency> getDependencies() {
		return dependencies;
	}
	
	public void addDependency(DependencyType type, SmellType smell, Double probability){
		this.dependencies.add(new Dependency(type, smell, probability));
	}
	
	@Override
	public double calculateProbability() {	
		double probability = 1.0;
		
		for(Dependency dep : this.dependencies){
			probability *= dep.probability;
		}
		
		return probability;
	}
}
