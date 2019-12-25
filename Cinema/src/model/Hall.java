package model;

import java.util.ArrayList;

public class Hall {

	private int id;//generise app
	private String name;
	private ArrayList<EProjectionType> projtypes;
	
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
	public ArrayList<EProjectionType> getProjtypes() {
		return projtypes;
	}
	public void setProjtypes(ArrayList<EProjectionType> projtypes) {
		this.projtypes = projtypes;
	}
	public Hall(int id, String name, ArrayList<EProjectionType> projtypes) {
		super();
		this.id = id;
		this.name = name;
		this.projtypes = projtypes;
	}
	@Override
	public String toString() {
		return "Hall [id=" + id + ", name=" + name + ", projtypes=" + projtypes + "]";
	}
	
	
	
}
