package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Hall;
import model.Seat;

public class SeatDAO {

	 public static Seat get(String num, Hall hall) { Connection conn =
	 ConnectionManager.getConnection(); PreparedStatement pstmt = null; ResultSet
	 rs = null;
	 try {
	  
	 String query = "SELECT * FROM seats WHERE num = ? AND hall = ?"; pstmt =
	 conn.prepareStatement(query); int i = 1; pstmt.setString(i++, num);
	 pstmt.setInt(i, hall.getId()); System.out.println(pstmt);
	 
	 rs = pstmt.executeQuery();
	 
	 if(rs.next()) { 
		 int index = 1; 
		 String no = rs.getString(index++); 
		 Hall h = HallDAO.get(rs.getInt(index++)); 
		 boolean available = rs.getBoolean(index);
	 
	 return new Seat(no, h, available);
	 
	 }
	  
	 } catch (Exception e) { e.printStackTrace(); }
	 finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	 
	 return null; 
	 }
	 

	public static List<Seat> getSeatsFromHall(Hall hall) {
		List<Seat> seats = new ArrayList<Seat>();

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String query = "SELECT num FROM seats WHERE hall = ?";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setInt(i, hall.getId());
			System.out.println(pstmt);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int index = 1;
				String no = rs.getString(index++);
				Hall h = HallDAO.get(rs.getInt(index++));
				boolean available = rs.getBoolean(index);

				Seat s = new Seat(no, h, available);
				seats.add(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return seats;
	}

	public static List<Seat> getAvailableSeatsFromHall(Hall hall) {

		List<Seat> seats = new ArrayList<Seat>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String query = "SELECT * FROM seats WHERE hall = ? AND available = 1";
			pstmt = conn.prepareStatement(query);
			int i = 1;
			pstmt.setInt(i, hall.getId());
			System.out.println(pstmt);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int index = 1;
				String no = rs.getString(index++);
				Hall h = HallDAO.get(rs.getInt(index++));
				boolean available = rs.getBoolean(index);

				Seat s = new Seat(no, h, available);
				seats.add(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return seats;
	}
}
