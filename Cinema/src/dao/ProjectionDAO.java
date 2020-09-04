package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.EProjectionType;
import model.Hall;
import model.Movie;
import model.Projection;
import model.ProjectionType;
import model.User;

public class ProjectionDAO {
	
	
	public static Projection findByMovie(Movie m) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projections WHERE movie = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, m.getId());
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String movieName = rs.getString(index++);
				Movie movie = MovieDAO.findByName(movieName);
				
				String projT = rs.getString(index++);
				EProjectionType pt = EProjectionType.valueOf(projT);
				
				String hallName = rs.getString(index++);
				Hall hall = HallDAO.findByName(hallName);
				
				Date date = rs.getDate(index++);
				
				Double price = rs.getDouble(index++);
				
				String user = rs.getString(index++);
				User admin = UserDAO.findByUsername(user);
				
				boolean deleted = rs.getBoolean(index++); 
				
				return new Projection(id, movie, pt, hall, date, price, admin, deleted);
				
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static Projection get(int id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projections WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 2;
				String movieName = rs.getString(index++);
				Movie movie = MovieDAO.findByName(movieName);
				
				String projT = rs.getString(index++);
				EProjectionType pt = EProjectionType.valueOf(projT);
				
				String hallName = rs.getString(index++);
				Hall hall = HallDAO.findByName(hallName);
				
				Date date = rs.getTimestamp(index++);
				
				Double price = rs.getDouble(index++);
				
				String user = rs.getString(index++);
				User admin = UserDAO.findByUsername(user);
				
				boolean deleted = rs.getBoolean(index++); 
				
				return new Projection(id, movie, pt, hall, date, price, admin, deleted);
				
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static List<Projection> getAll(){
		List<Projection> p = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projections WHERE deleted = 0;";
			pstmt = conn.prepareStatement(query);
						
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				Integer movie = rs.getInt(index++);
				Movie m = MovieDAO.get(movie);
				
				String projT = rs.getString(index++);
				EProjectionType pt = EProjectionType.valueOf(projT);
				String hallName = rs.getString(index++);
				Hall hall = HallDAO.findByName(hallName);
				
				Date date = rs.getTimestamp(index++);
				
				Double price = rs.getDouble(index++);
				
				String user = rs.getString(index++);
				User admin = UserDAO.findByUsername(user);				
		
				boolean deleted = rs.getBoolean(index++);
				
				Projection projection = new Projection(id, m, pt, hall, date, price, admin, deleted);
				p.add(projection);
				return p;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static List<Projection> getAllProjForMovieAndDate(Movie movie, Date from, Date to){
		List<Projection> p = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projections WHERE movie = ? AND datetime <= ? AND datetime >= ?";
			pstmt = conn.prepareStatement(query);
			
			int i = 1;
			pstmt.setInt(i++, movie.getId());
			pstmt.setTimestamp(i++, new Timestamp(from.getTime()));
			pstmt.setTimestamp(i++, new Timestamp(to.getTime()));
			//pstmt.setDate(i++, from);
			//pstmt.setDate(i++, to);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String movieName = rs.getString(index++);
				Movie m = MovieDAO.findByName(movieName);
				
				String projT = rs.getString(index++);
				EProjectionType pt = EProjectionType.valueOf(projT);
				
				String hallName = rs.getString(index++);
				Hall hall = HallDAO.findByName(hallName);
				
				Date date = rs.getTimestamp(index++);
				
				Double price = rs.getDouble(index++);
				
				String user = rs.getString(index++);
				User admin = UserDAO.findByUsername(user);				
				
				boolean deleted = rs.getBoolean(index++); 
				
	
				Projection projection = new Projection(id, m, pt, hall, date, price, admin, deleted);
				p.add(projection);
				return p;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	public static List<Projection> getAllProjForToday(Date today){
		List<Projection> p = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projections WHERE datetime = ?";//DA NAMESTIM DA BUDE DANASNJI DATUM
			pstmt = conn.prepareStatement(query);
			
			int i = 1;
			pstmt.setTimestamp(i++, new Timestamp(today.getTime()));
			//pstmt.setDate(i++, today);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String movieName = rs.getString(index++);
				Movie m = MovieDAO.findByName(movieName);
				
				String projT = rs.getString(index++);
				EProjectionType pt = EProjectionType.valueOf(projT);
				
				String hallName = rs.getString(index++);
				Hall hall = HallDAO.findByName(hallName);
				
				Date date = rs.getTimestamp(index++);
				
				Double price = rs.getDouble(index++);
				
				String user = rs.getString(index++);
				User admin = UserDAO.findByUsername(user);				
				
				boolean deleted = rs.getBoolean(index++); 
				
				
				Projection projection = new Projection(id, m, pt, hall, date, price, admin, deleted);
				p.add(projection);
				return p;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	
	public static boolean add(Projection p) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "INSERT INTO projections(movie, projT, hall, date, price, admin) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			
			int i = 1;
			pstmt.setString(i++, p.getMovie().getName());
			pstmt.setString(i++, p.getProjectionType().toString());
			pstmt.setInt(i++, p.getHall().getId());
			pstmt.setTimestamp(i++, new Timestamp(p.getDatetime().getTime()));
			pstmt.setDouble(i++, p.getPrice());
			String user = p.getAdmin().getUsername();
			pstmt.setString(i++, user);
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		
		return false;
	}
	
	
	public static boolean update(Projection p) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "UPDATE projections SET movie = ?, projT = ?, hall = ?, date = ?, price = ?, admin = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			int i = 1;
			pstmt.setString(i++, p.getMovie().getName());
			pstmt.setString(i++, p.getProjectionType().toString());
			pstmt.setInt(i++, p.getHall().getId());
			pstmt.setTimestamp(i++, new Timestamp(p.getDatetime().getTime()));
			pstmt.setDouble(i++, p.getPrice());
			String user = p.getAdmin().getUsername();
			pstmt.setString(i++, user);
			pstmt.setInt(i++, p.getId());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		
		return false;
	}
	
	
	
	public static boolean delete(Projection p) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "DELETE FROM projections(movie, projT, hall, date, price, admin) WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, p.getId());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		
		return false;
	}
}
