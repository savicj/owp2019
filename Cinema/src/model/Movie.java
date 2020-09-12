package model;

import java.util.ArrayList;

public class Movie {

	private int id;//generise app
	private String name;
	private ArrayList<String> directors;//opciono
	private ArrayList<String> actors;//opciono
	private ArrayList<String> genre;//opciono
	private int duration;//veci od 0
	private String distributor;
	private String originCountry;
	private int year;//veci od 0
	private String overview;//opciono
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	public ArrayList<String> getDirectors() {
		return directors;
	}
	public void setDirectors(ArrayList<String> directors) {
		this.directors = directors;
	}
	public ArrayList<String> getActors() {
		return actors;
	}
	public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}
	public ArrayList<String> getGenre() {
		return genre;
	}
	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;

	}
	
	public String getDistributor() {
		return distributor;
	}
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public Movie() {
		super();
	}
	public Movie(int id, String name, ArrayList<String> directors, ArrayList<String> actors, ArrayList<String> genre,
			int duration, String distributor, String originCountry, int year, String overview) {
		super();
		this.id = id;
		this.name = name;
		this.directors = directors;
		this.actors = actors;
		this.genre = genre;
		this.duration = duration;
		this.distributor = distributor;
		this.originCountry = originCountry;
		this.year = year;
		this.overview = overview;
		this.deleted = false;
	}
	
	public Movie(int id, String name, ArrayList<String> directors, ArrayList<String> actors, ArrayList<String> genre,
			int duration, String distributor, String originCountry, int year, String overview, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.directors = directors;
		this.actors = actors;
		this.genre = genre;
		this.duration = duration;
		this.distributor = distributor;
		this.originCountry = originCountry;
		this.year = year;
		this.overview = overview;
		this.deleted = deleted;
	}
	public Movie( String name, ArrayList<String> directors, ArrayList<String> actors, ArrayList<String> genre,
			int duration, String distributor, String originCountry, int year, String overview) {
		super();
		this.name = name;
		this.directors = directors;
		this.actors = actors;
		this.genre = genre;
		this.duration = duration;
		this.distributor = distributor;
		this.originCountry = originCountry;
		this.year = year;
		this.overview = overview;
		this.deleted = false;
}
	
	public Movie( String name, ArrayList<String> directors, ArrayList<String> actors, ArrayList<String> genre,
			int duration, String distributor, String originCountry, int year, String overview, boolean deleted) {
		super();
		this.name = name;
		this.directors = directors;
		this.actors = actors;
		this.genre = genre;
		this.duration = duration;
		this.distributor = distributor;
		this.originCountry = originCountry;
		this.year = year;
		this.overview = overview;
		this.deleted = deleted;
}
	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", directors=" + directors + ", actors=" + actors + ", genre="
				+ genre + ", duration=" + duration + ", distributor=" + distributor + ", originCountry=" + originCountry
				+ ", year=" + year + ", overview=" + overview + "]";
	}
	
}
