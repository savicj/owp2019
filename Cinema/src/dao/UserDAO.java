package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ERole;
import model.User;

public class UserDAO {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static User getUser(String username, String password) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				Integer id = rs.getInt(index++);
				String uName = rs.getString(index++);
				String pword = rs.getString(index++);
				Date regDate = rs.getTimestamp(index++);
				String role = rs.getString(index++);
				ERole erole = ERole.valueOf(role);
				boolean deleted = rs.getBoolean(index++);
				
				return new User(id, uName, pword, regDate, erole, deleted);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	public static User getUser(int ID) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			String query = "SELECT * FROM users WHERE username = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, ID);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				Integer id = rs.getInt(index++);
				String uName = rs.getString(index++);
				String pword = rs.getString(index++);
				Date regDate = rs.getTimestamp(index++);
				String role = rs.getString(index++);
				ERole erole = ERole.valueOf(role);
				boolean deleted = rs.getBoolean(index++);
				
				return new User(id, uName, pword, regDate, erole, deleted);
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
			
			String query = "SELECT * FROM users WHERE username = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int index = 1;
				Integer id = rs.getInt(index++);
				String uName = rs.getString(index++);
				String pword = rs.getString(index++);
				Date regDate = rs.getTimestamp(index++);
				String role = rs.getString(index++);
				ERole erole = ERole.valueOf(role);
				boolean deleted = rs.getBoolean(index++);
				
				return new User(id, uName, pword, regDate, erole, deleted);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	
	
	public static List<User> getAll(String username, String role) {
		List<User> users = new ArrayList<User>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM users WHERE username LIKE ? AND role LIKE ? AND deleted = false";
			pstmt = conn.prepareStatement(query);
			//System.out.println(pstmt);
			int i=1;
			if(username == null || username == "") {
				pstmt.setString(i++, "%");
			}else
				pstmt.setString(i++, "%" + username + "%");
			if(role == null || role == "") {
				pstmt.setString(i++, "%");
			}else
				pstmt.setString(i++, "%" + role + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int index = 1;
				Integer id = rs.getInt(index++);
				String uName = rs.getString(index++);
				String pword = rs.getString(index++);
				Date regDate = rs.getTimestamp(index++);
				String r = rs.getString(index++);
				ERole erole = ERole.valueOf(r);
				boolean deleted = rs.getBoolean(index++);
				
				User user = new User(id, uName, pword, regDate, erole, deleted);
				users.add(user);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return users;
	}
	
	
	public static boolean add(User user) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "INSERT INTO users(username, password, registrationDate, role, deleted) VALUES(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setString(i++, user.getUsername());
			pstmt.setString(i++, user.getPassword());
			pstmt.setTimestamp(i++, new Timestamp(user.getRegistrationDate().getTime()));
			pstmt.setString(i++, user.getRole().toString());
			pstmt.setBoolean(i++, user.isDeleted());
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
			
			String query = "UPDATE users SET password = ?, role = ?, deleted = ? WHERE username = ?";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setString(i++, user.getPassword());
			pstmt.setString(i++, user.getRole().toString());
			pstmt.setBoolean(i++, user.isDeleted());
			pstmt.setString(i++, user.getUsername());

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
			pstmt.setString(1, username);
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
