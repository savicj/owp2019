package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.EProjectionType;
import model.Hall;

public class HallDAO {
	
	public static Hall findByName(String name) {

		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM halls WHERE name like ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			//System.out.println(pstmt);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String hallName = rs.getString(index++);
				String projTypes = rs.getString(index);
				ArrayList<EProjectionType> projectionTypes = ProjectionTypeDAO.getProjectionTypesFromString(projTypes);
				
				return new Hall(id, hallName, projectionTypes);
			}
			
			
		}catch (Exception e) {
				e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	
	}
	
	public static Hall findByProjection(String ID) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM halls WHERE projection = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ID);
			//System.out.println(pstmt);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String name = rs.getString(index++);
				
				String projTypes = rs.getString(index);
				
				ArrayList<EProjectionType> projectionTypes = new ArrayList<EProjectionType>();
				for(String s : projTypes.split(",")) {
					try {
//						System.out.println(s);
						EProjectionType type = EProjectionType.valueOf(s);
						projectionTypes.add(type);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				return new Hall(id, name, projectionTypes);
			}
			
			
		}catch (Exception e) {
				e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static Hall get(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT name, projectionTypes FROM halls WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			//System.out.println(pstmt);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				String name = rs.getString(index++);
				
				String projTypes = rs.getString(index);
				
				ArrayList<EProjectionType> projectionTypes = new ArrayList<EProjectionType>();
				for(String s : projTypes.split(",")) {
					try {
//						System.out.println(s);
						EProjectionType type = EProjectionType.valueOf(s);
						projectionTypes.add(type);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				return new Hall(id, name, projectionTypes);
			}
			
			
		}catch (Exception e) {
				e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	
	public static List<Hall> getAll(){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Hall> halls = new ArrayList<>();
		try {
			
			
			String query = "SELECT * FROM halls";
			pstmt = conn.prepareStatement(query);
//			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String name = rs.getString(index++);
				String projTypes = rs.getString(index);
				ArrayList<EProjectionType> projectionTypes = new ArrayList<>();
				for(String s : projTypes.split(",")) {
					try {
						EProjectionType type = EProjectionType.valueOf(s);
						projectionTypes.add(type);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				Hall hall = new Hall(id, name, projectionTypes);
				halls.add(hall);
				return halls;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return halls;
	}
	
	public static List<Hall> getAll(String pt){
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Hall> halls = new ArrayList<>();
		try {
			
			
			String query = "SELECT * FROM halls where projectionTypes LIKE ?";
			pstmt = conn.prepareStatement(query);
			if(pt == "" || pt == null)
				pstmt.setString(1, "%");
			else
				pstmt.setString(1, "%"+ pt+ "%");
//			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int index = 1;
				int id = rs.getInt(index++);
				String name = rs.getString(index++);
				String projTypes = rs.getString(index);
				ArrayList<EProjectionType> projectionTypes = new ArrayList<>();
				for(String s : projTypes.split(",")) {
					try {
						EProjectionType type = EProjectionType.valueOf(s);
						projectionTypes.add(type);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				Hall hall = new Hall(id, name, projectionTypes);
				halls.add(hall);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return halls;
	}
	//NIJE POTREBNO.........................................
	/*public static boolean add(Hall hall) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "INSERT INTO halls(name, projectionTypes) VALUES (?, ?)";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, hall.getNname());
			String projTypes = "";
			for(EProjectionType pt : hall.getProjtypes()) {
				projTypes += pt.toString() + "," ;
			}
			pstmt.setString(index++, projTypes);
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean update(Hall hall) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "UPDATE halls SET name = ?, projectionTypes = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, hall.getNname());
			String projTypes = "";
			for(EProjectionType pt : hall.getProjtypes()) {
				projTypes += pt.toString() + "," ;
			}
			pstmt.setString(index++, projTypes);
			pstmt.setString(index++, hall.getId());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	
	public static boolean delete(String id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String query = "DELETE FROM WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, id);
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	
	}*/
	
	
}
