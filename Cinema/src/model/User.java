package model;

import java.util.Date;

public class User {

	private String username;//jedinstveno, alfanumericki karakteri bez razmaka
	private String password;//alfanumericki karakteri bez razmaka
	private Date registrationDate;//generise app
	private ERole role;//automatski je user
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public ERole getRole() {
		return role;
	}
	public void setRole(ERole role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", registrationDate=" + registrationDate
				+ ", role=" + role + "]";
	}
	
	
}
