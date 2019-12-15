package model;

import java.util.Date;

public class Ticket {

	private int id;//generise app
	private Projection projection;
	private Seat seat;//ne sme biti zauzeto
	private Date date;//ne sme biti u proslosti
	private User user;//automatski postavlja app
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Ticket(int id, Projection projection, Seat seat, Date date, User user) {
		super();
		this.id = id;
		this.projection = projection;
		this.seat = seat;
		this.date = date;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", projection=" + projection + ", seat=" + seat + ", date=" + date + ", user="
				+ user + "]";
	}
	
	
}
