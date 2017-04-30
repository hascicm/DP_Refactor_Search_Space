package entities;

import java.util.List;

public class DependencyRepair extends Repair {
	
	private DependencyType dependencyType;
	
	public DependencyRepair(String name, List<SmellType> smells, DependencyType dependencyType) {
		super(name, smells);
		this.dependencyType = dependencyType;
	}
	
	List<SmellType> relatedSmells;
	DependencyType type;
	
	public List<SmellType> getRelatedSmells() {
		return relatedSmells;
	}
	public void setRelatedSmells(List<SmellType> relatedSmells) {
		this.relatedSmells = relatedSmells;
	}
	public DependencyType getType() {
		return type;
	}
	public void setType(DependencyType type) {
		this.type = type;
	}
	public DependencyType getDependencyType() {
		return dependencyType;
	}
	public void setDependencyType(DependencyType dependencyType) {
		this.dependencyType = dependencyType;
	}	
}
