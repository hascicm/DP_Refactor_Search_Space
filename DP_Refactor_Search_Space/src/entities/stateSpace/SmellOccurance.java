package entities.stateSpace;

import entities.SmellType;

public class SmellOccurance {
	private SmellType smell;

	
	
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
	
	
}
