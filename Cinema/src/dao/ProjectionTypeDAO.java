package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.EProjectionType;
import model.Hall;
import model.Movie;
import model.Projection;
import model.ProjectionType;
import model.User;

public class ProjectionTypeDAO {
	
	public static ArrayList<EProjectionType> getProjectionTypesFromString(String pt){
		ArrayList<EProjectionType> projectionTypes = new ArrayList<>();
		for(String s : pt.split(",")) {
			try {
				EProjectionType type = EProjectionType.valueOf(s);
				projectionTypes.add(type);
				
				return projectionTypes;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ProjectionType getPTypeByName(String name) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projectionTypes WHERE name like ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, name);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index);
				
				return new ProjectionType(id,name);
				
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
	
	
	public static ProjectionType get(int id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM projectionTypes WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				String name = rs.getString(++index);
				
				return new ProjectionType(id,name);
				
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
	
	
	public static List<ProjectionType> getAll() {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProjectionType> pt = new ArrayList<>();
		try {
			String query = "SELECT * FROM projectionTypes";
			pstmt = conn.prepareStatement(query);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String name = rs.getString(index);
				
				ProjectionType t = new ProjectionType(id,name);
				pt.add(t);
				return pt;
				
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
}
