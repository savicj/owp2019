package model;

import java.util.Date;

public class Projection {

	private int id;//generise app
	private Movie movie;
	private EProjectionType projectionType;
	private Hall hall;//mora biti slobodna za trazeno vreme i da podrzava tip proj
	private Date datetime;//ne sme biti u proslosti
	private double price;
	private User admin;//povezuje app
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public EProjectionType getProjectionType() {
		return projectionType;
	}
	public void setProjectionType(ProjectionType projprojectionTypeType) {
		this.projectionType = projectionType;
	}
	public Hall getHall() {
		return hall;
	}
	public void setHall(Hall hall) {
		this.hall = hall;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public Projection(int id, Movie movie, EProjectionType projectionType, Hall hall, Date datetime, double price,
			User admin) {
		super();
		this.id = id;
		this.movie = movie;
		this.projectionType = projectionType;
		this.hall = hall;
		this.datetime = datetime;
		this.price = price;
		this.admin = admin;
	}
	@Override
	public String toString() {
		return "Projection [id=" + id + ", movie=" + movie + ", projectionType=" + projectionType + ", hall=" + hall + ", datetime="
				+ datetime + ", price=" + price + ", admin=" + admin + "]";
	}
	
	
}
