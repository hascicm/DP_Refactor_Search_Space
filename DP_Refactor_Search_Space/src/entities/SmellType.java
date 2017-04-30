package entities;

public class SmellType {
	private int id;
	private String name;
	private String description;

	
	public SmellType() {
	}

	public SmellType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public SmellType(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

}
