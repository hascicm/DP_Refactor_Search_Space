package entities;

import java.util.List;

public class DependencyRepair extends Repair {
	
	public DependencyRepair(String name, List<SmellType> smells) {
		super(name, smells);
		// TODO Auto-generated constructor stub
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
		
}
