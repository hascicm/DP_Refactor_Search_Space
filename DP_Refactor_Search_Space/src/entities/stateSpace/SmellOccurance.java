package entities.stateSpace;

import java.util.ArrayList;
import java.util.List;

import entities.Location;
import entities.SmellType;

//REFACTOR - Lazy Class
  // smelltag end   : LAZC9 //SMELL: #SmellType(Lazy Class)
 //REFACTOR - Data Class
  // smelltag end   : DC4 //SMELL: #SmellType(Data Class)
 public class SmellOccurance {
	private SmellType smell;
	private List<Location> locations; //na prvom mieste sa nachadza klucova trieda?
	
	
	public SmellOccurance(SmellType smell) {
		super();
		this.smell = smell;
		this.locations = new ArrayList<Location>();
	}
	
	public SmellOccurance(SmellType smell, List<Location> locations) {
		super();
		this.smell = smell;
		this.locations = locations;
	}

	public SmellType getSmell() {
		return smell;
	}

	public void setSmell(SmellType smell) {
		this.smell = smell;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}// smelltag start : DC4 // smelltag start : LAZC9 
