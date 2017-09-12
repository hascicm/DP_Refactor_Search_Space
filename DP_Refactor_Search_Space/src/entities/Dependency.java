package entities;

public class Dependency {
	DependencyType type;
	SmellType smell;
	Double probability;

	public Dependency(DependencyType type, SmellType smell, Double propability) {
		this.type = type;
		this.smell = smell;
		this.probability = propability;
	}

	public DependencyType getType() {
		return type;
	}

	public void setType(DependencyType type) {
		this.type = type;
	}

	public SmellType getSmell() {
		return smell;
	}

	public void setSmell(SmellType smell) {
		this.smell = smell;
	}

	public Double getPropability() {
		return probability;
	}

	public void setPropability(Double propability) {
		this.probability = propability;
	}

}
