package entities.stateSpace;

import java.util.List;

import entities.SmellType;

public class SmellOccurance {
	private SmellType smell;
	private List<LocationPart> location;
	
	public SmellOccurance(SmellType smell) {
		super();
		this.smell = smell;
	}

	public SmellType getSmell() {
		return smell;
	}

	public void setSmell(SmellType smell) {
		this.smell = smell;
	}

	public List<LocationPart> getLocation() {
		return location;
	}

	public void setLocation(List<LocationPart> location) {
		this.location = location;
	}	
}
