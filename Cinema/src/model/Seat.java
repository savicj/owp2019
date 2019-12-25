package model;

public class Seat {

	private int num;//jedinstven br na nivou sale
	private Hall hall;
	private boolean available;
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Hall getHall() {
		return hall;
	}
	public void setHall(Hall hall) {
		this.hall = hall;
	}
	public Seat(int num, Hall hall, boolean available) {
		this.num = num;
		this.hall = hall;
		this.available = available;
	}
	@Override
	public String toString() {
		return "Seat [num=" + num + ", hall=" + hall + ", available="+ available +"]";
	}
	
}
