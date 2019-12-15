package models;

import java.util.ArrayList;

public class Hall {

	private int id;//generise app
	private String naziv;
	private ArrayList<EProjectionType> projtypes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public ArrayList<EProjectionType> getProjtypes() {
		return projtypes;
	}
	public void setProjtypes(ArrayList<EProjectionType> projtypes) {
		this.projtypes = projtypes;
	}
	public Hall(int id, String naziv, ArrayList<EProjectionType> projtypes) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.projtypes = projtypes;
	}
	@Override
	public String toString() {
		return "Hall [id=" + id + ", naziv=" + naziv + ", projtypes=" + projtypes + "]";
	}
	
	
	
}
