package entities;

public class SmellType {
	private Integer id;
	private String name;
	private String description;
	private int weight = 5; //the priority of smell : 1 - most accepted; 10 - unwanted (Default - 5)

	
	public SmellType() {
	}

	public SmellType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public SmellType(String name) {
		this.name = name;
	}
	
	public SmellType(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public SmellType(Integer id, String name, int weight) {
		this.id = id; 
		this.name = name;
		this.weight = weight;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
