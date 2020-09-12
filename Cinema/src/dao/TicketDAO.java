package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Hall;
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
			
			String query = "SELECT * FROM tickets WHERE user = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				Projection p = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				Seat seat = SeatDAO.get(seatmark, p.getHall());
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(username);
				
				Ticket t = new Ticket(id, p, seat, datetime, user);
				tickets.add(t);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return tickets;				
		
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
			
			while(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				Projection projection = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				Seat seat = SeatDAO.get(seatmark, projection.getHall());
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(rs.getString(index++));
				
				Ticket t = new Ticket(id, p, seat, datetime, user);
				tickets.add(t);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return tickets;				
		
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
			
			while(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				Projection projection = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				Seat seat = SeatDAO.get(seatmark, projection.getHall());
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(rs.getString(index++));
				
				Ticket t = new Ticket(id, projection, seat, datetime, user);
				tickets.add(t);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return tickets;				
		
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
			
			while(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				Projection p = ProjectionDAO.get(rs.getInt(index++));
				String seatmark = rs.getString(index++);
				Seat seat = SeatDAO.get(seatmark, p.getHall());
				Date datetime = rs.getDate(index++);
				User user = UserDAO.findByUsername(rs.getString(index++));
				
				Ticket t = new Ticket(id, p, seat, datetime, user);
				tickets.add(t);
			}
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return tickets;				
		
	}
	
	public static boolean add(Ticket t) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setInt(i++, t.getProjection().getId());
			pstmt.setString(i++, t.getSeat().getNum());
			pstmt.setTimestamp(i++, new Timestamp(t.getDate().getTime()));
			pstmt.setString(i++, t.getUser().getUsername());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
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
