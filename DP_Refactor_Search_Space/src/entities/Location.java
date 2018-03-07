package entities;

import java.util.List;

//REFACTOR - Lazy Class
  //SMELL: #SmellType(Lazy Class)
 public class Location {
	private List<LocationPart> location;
	
	public Location(List<LocationPart> location) {
		super();
		this.location = location;
	}

	public List<LocationPart> getLocation() {
		return location;
	}

	public void setLocation(List<LocationPart> location) {
		this.location = location;
	}
	
	public String toString(){
		return this.location.toString();
	}
}
