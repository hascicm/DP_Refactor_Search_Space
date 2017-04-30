package entities;

import java.util.List;

public class Repair {
	private String name;
	private String description;
	List<SmellType> smells;
	
	
	public Repair(String name, List<SmellType> smells) {
		super();
		this.name = name;
		this.smells = smells;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<SmellType> getSmells() {
		return smells;
	}
	public void setSmells(List<SmellType> smells) {
		this.smells = smells;
	}
	
}
