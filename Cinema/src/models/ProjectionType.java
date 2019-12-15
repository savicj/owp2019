package models;

public class ProjectionType {
	//2d,3d,4d
	private int id;//generise app
	private String name;
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
	public ProjectionType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return "ProjectionType [id=" + id + ", name=" + name + "]";
	}
	
	
	

}
