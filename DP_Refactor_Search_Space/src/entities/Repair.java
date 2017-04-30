package entities;

import java.util.ArrayList;
import java.util.List;

public class Repair {
	private String name;
	private String description;
	List<SmellType> smells;
	private int weight = 3; //priority of repair 1 - most used; 5 - least used. (Default - 3)
	
	public Repair(String name, List<SmellType> smells) {
		super();
		this.name = name;
		this.smells = smells;
	}
	
	public Repair(String name, SmellType smell) {
		super();
		this.name = name;
		this.smells = new ArrayList<SmellType>();
		this.smells.add(smell);
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
