package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Movie;
import model.Projection;
import model.Seat;
import model.Ticket;
import model.User;


public class TicketDAO {
//id, proj, seat, date, user
	
	public static List<Ticket> findByUser(String username) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM tickets WHERE user like ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				int id = rs.getInt(index++);
				Projection p = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
//				for(String s : seatmark.split("|")) {
//					int num = Integer.parseInt(s);
//					int hall = Integer.parseInt(s);
//				}
				String[] s = seatmark.split("|");
				int num = Integer.parseInt(s[0]);
				int hall = Integer.parseInt(s[1]);
				Seat seat = SeatDAO.get(num, HallDAO.get(hall));
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(username);
				
				Ticket t = new Ticket(id, p, seat, datetime, user);
				tickets.add(t);
				return tickets;				
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	
	public static List<Ticket> findByProjection(Projection p) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM tickets WHERE projection = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, p.getId());
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				int id = rs.getInt(index++);
				Projection projection = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				String[] s = seatmark.split("|");
				int num = Integer.parseInt(s[0]);
				int hall = Integer.parseInt(s[1]);
				Seat seat = SeatDAO.get(num, HallDAO.get(hall));
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(rs.getString(index++));
				
				Ticket t = new Ticket(id, p, seat, datetime, user);
				tickets.add(t);
				return tickets;				
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	
	public static List<Ticket> getAll() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM tickets";
			pstmt = conn.prepareStatement(query);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				int id = rs.getInt(index++);
				Projection p = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				String[] s = seatmark.split("|");
				int num = Integer.parseInt(s[0]);
				int hall = Integer.parseInt(s[1]);
				Seat seat = SeatDAO.get(num, HallDAO.get(hall));
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(rs.getString(index++));
				
				Ticket t = new Ticket(id, p, seat, datetime, user);
				tickets.add(t);
				return tickets;				
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	
	
	public static List<Ticket> findByMovie(Movie movie) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM tickets where projection = ?";
			pstmt = conn.prepareStatement(query);
			Projection p = ProjectionDAO.findByMovie(movie);
			pstmt.setInt(1, p.getId());
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 0;
				int id = rs.getInt(index++);
				Projection projection = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				String[] s = seatmark.split("|");
				int num = Integer.parseInt(s[0]);
				int hall = Integer.parseInt(s[1]);
				Seat seat = SeatDAO.get(num, HallDAO.get(hall));
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(rs.getString(index++));
				
				Ticket t = new Ticket(id, projection, seat, datetime, user);
				tickets.add(t);
				return tickets;				
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
		
	}
	
	public static boolean delete(int id) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;		
		try {
			
			String query = "DELETE FROM tickets where id = ?";
			pstmt = conn.prepareStatement(query);
			
			return pstmt.executeUpdate() == 1;				
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
		
	
	}
	
}
