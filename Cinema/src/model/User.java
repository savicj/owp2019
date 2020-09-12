package model;

import java.util.Date;

public class User {

	private Integer id;
	private String username;//jedinstveno, alfanumericki karakteri bez razmaka
	private String password;//alfanumericki karakteri bez razmaka
	private Date registrationDate;//generise app
	private ERole role;//automatski je user
	private boolean deleted;

	
	public User(Integer id, String username, String password, Date registrationDate, ERole role, boolean deleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
		this.role = role;
		this.deleted = deleted;
	}
	public User(String username, String password, Date registrationDate, ERole role, boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
		this.role = role;
		this.deleted = deleted;
	}
	
	public User() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", registrationDate=" + registrationDate
				+ ", role=" + role + "]";
	}
	
	
}
