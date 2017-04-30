package entities;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class DependencyRepair extends Repair {
	
	private Map<DependencyType, List<SmellType>> dependencies;
	
	
	public DependencyRepair(String name, List<SmellType> smells) {
		super(name, smells);
		this.dependencies = new HashMap<DependencyType, List<SmellType>>();
	}
		
	
	public Map<DependencyType, List<SmellType>> getDependencies() {
		return dependencies;
	}
	
	public void addDependency(DependencyType type, SmellType smell){
		
		if(!this.dependencies.containsKey(type)){
			this.dependencies.put(type, new ArrayList<SmellType>());
		}
		
		this.dependencies.get(type).add(smell);
	}
	
public void addDependency(DependencyType type, List<SmellType> smells){
		
		if(!this.dependencies.containsKey(type)){
			this.dependencies.put(type, new ArrayList<SmellType>());
		}
		
		this.dependencies.get(type).addAll(smells);
	}
	
}
