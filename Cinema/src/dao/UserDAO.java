package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.ERole;
import model.User;

public class UserDAO {

	
	public static User getUser(String username, String password) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM users WHERE username like ? AND password like ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				String uName = rs.getString(index++);
				String pword = rs.getString(index++);
				Date regDate = rs.getDate(index++);
				String role = rs.getString(index++);
				ERole erole = ERole.valueOf(role);
				
				return new User(uName, pword, regDate, erole);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	public static User findByUsername(String username) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM users WHERE username like ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				String uName = rs.getString(index++);
				String password = rs.getString(index++);
				Date regDate = rs.getDate(index++);
				String role = rs.getString(index++);
				ERole erole = ERole.valueOf(role);
				
				return new User(uName, password, regDate, erole);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	
	
	public static List<User> getAll() {
		List<User> users = new ArrayList<User>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM users";
			pstmt = conn.prepareStatement(query);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				String uName = rs.getString(index++);
				String password = rs.getString(index++);
				Date regDate = rs.getDate(index++);
				String role = rs.getString(index++);
				ERole erole = ERole.valueOf(role);
				
				User user = new User(uName, password, regDate, erole);
				users.add(user);
				return users;
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	
	public static boolean add(User user) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "INSERT INTO users(username, password, registrationDate, role) VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setString(i++, user.getUsername());
			pstmt.setString(i++, user.getPassword());
			pstmt.setDate(i++, user.getRegistrationDate());
			pstmt.setString(i++, user.getRole().toString());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	
	public static boolean update(User user) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "UPDATE users SET password = ?, registrationDate = ?, role = ? WHERE username = ?";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setString(i++, user.getPassword());
			pstmt.setDate(i++, user.getRegistrationDate());
			pstmt.setString(i++, user.getRole().toString());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	
	public static boolean delete(String username) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "DELETE FROM users WHERE username = ?";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setString(i++, username);
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}
}
