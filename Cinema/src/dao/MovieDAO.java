package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Movie;


public class MovieDAO {
	
	public static Movie get(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM movies WHERE id = ? AND deleted = false";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			//System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				int ID = rset.getInt(index++);
				String name = rset.getString(index++);
				String dir = rset.getString(index++);
				ArrayList<String> directors = new ArrayList<String>();
				for(String d : dir.split(","))
					directors.add(d);
				String act = rset.getString(index++);
				ArrayList<String> actors = new ArrayList<String>();
				for(String a : act.split(","))
					actors.add(a);
				String gen = rset.getString(index++);
				ArrayList<String> genre = new ArrayList<String>();
				for(String g : gen.split(","))
					genre.add(g);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String originCountry = rset.getString(index++);
				int year = rset.getInt(index++);
				String overview = rset.getString(index++);
				boolean deleted = rset.getBoolean(index++);

				return new Movie(ID, name, directors, actors, genre, duration, distributor, originCountry, year, overview, deleted);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static Movie findByName(String name) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM movies WHERE name LIKE ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			//System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String n = rset.getString(index++);
				String dir = rset.getString(index++);
				ArrayList<String> directors = new ArrayList<String>();
				for(String d : dir.split(","))
					directors.add(d);
				String act = rset.getString(index++);
				ArrayList<String> actors = new ArrayList<String>();
				for(String a : act.split(","))
					actors.add(a);
				String gen = rset.getString(index++);
				ArrayList<String> genre = new ArrayList<String>();
				for(String g : gen.split(","))
					genre.add(g);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String originCountry = rset.getString(index++);
				int year = rset.getInt(index++);
				String overview = rset.getString(index++);
				boolean deleted = rset.getBoolean(index++);


				return new Movie(id, n, directors, actors, genre, duration, distributor, originCountry, year, overview, deleted);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static List<Movie> getAll(String mname, String mgenre, int durationFrom, int durationTo, String mdistributor, String country, int yearFrom, int yearTo){
		List<Movie> movies = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM movies " + 
					"WHERE name LIKE ? AND genres LIKE ? AND duration >= ? AND duration <= ? AND distributor LIKE ? AND originCountry LIKE ? AND year >= ? AND year <= ? AND deleted = false";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
		
			pstmt.setString(index++, mname);
			pstmt.setString(index++,"%"+ mgenre +"%");
			pstmt.setInt(index++, durationFrom);
			pstmt.setInt(index++, durationTo);
			pstmt.setString(index++, "%" +mdistributor +"%");
			pstmt.setString(index++, country);
			pstmt.setInt(index++, yearFrom);
			pstmt.setInt(index++, yearTo); 	

			rset = pstmt.executeQuery();

			while (rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				String name = rset.getString(index++);
				String dir = rset.getString(index++);
				ArrayList<String> directors = new ArrayList<String>();
				for(String d : dir.split(","))
					directors.add(d);
				String act = rset.getString(index++);
				ArrayList<String> actors = new ArrayList<String>();
				for(String a : act.split(","))
					actors.add(a);
				String gen = rset.getString(index++);
				ArrayList<String> genre = new ArrayList<String>();
				for(String g : gen.split(","))
					genre.add(g);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String originCountry = rset.getString(index++);
				int year = rset.getInt(index++);
				String overview = rset.getString(index++);
				boolean deleted = rset.getBoolean(index++);


				Movie movie = new Movie(id, name, directors, actors, genre, duration, distributor, originCountry, year, overview, deleted);
				movies.add(movie);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return movies;
	}
	
	
	
	public static boolean add(Movie movie) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO movies(name, directors, actors, genres, duration, distributor, "+
								"originCountry, year, overview, deleted) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, false)";
			
			pstmt = conn.prepareStatement(query);
			//System.out.println(pstmt);
		
			int index = 1;
			pstmt.setString(index++, movie.getName());
			//ArrayList<String> dir = movie.getDirectors();
			String directors = "";
			for (String s : movie.getDirectors())
				directors += s + ",";
			pstmt.setString(index++, directors);
			String act = "";
			for (String s : movie.getActors())
			    act += s + ",";
			pstmt.setString(index++, act);
			String genre = "";
			for (String s : movie.getGenre())
			    genre += s + ",";
			pstmt.setString(index++, genre);
			pstmt.setInt(index++, movie.getDuration());
			pstmt.setString(index++, movie.getDistributor());
			pstmt.setString(index++, movie.getOriginCountry());
			pstmt.setInt(index++, movie.getYear());
			pstmt.setString(index++, movie.getOverview());
			
			return pstmt.executeUpdate() == 1;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	
	public static boolean update(Movie movie) {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE movies SET name = ?, directors = ?, actors = ?, genres = ?, duration = ?, distributor = ?, "+
								"originCountry = ?, year = ?, overview = ?, deleted = ? WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			//System.out.println(pstmt);
		
			int index = 1;
			pstmt.setString(index++, movie.getName());
			//ArrayList<String> dir = movie.getDirectors();
			String directors = "";
			for (String s : movie.getDirectors())
				directors += s + ",";
			pstmt.setString(index++, directors);
			String act = "";
			for (String s : movie.getActors())
			    act += s + ",";
			pstmt.setString(index++, act);
			String genre = "";
			for (String s : movie.getGenre())
			    genre += s + ",";
			pstmt.setString(index++, genre);
			pstmt.setInt(index++, movie.getDuration());
			pstmt.setString(index++, movie.getDistributor());
			pstmt.setString(index++, movie.getOriginCountry());
			pstmt.setInt(index++, movie.getYear());
			pstmt.setString(index++, movie.getOverview());
			pstmt.setInt(index++, movie.getId());
			pstmt.setBoolean(index++, movie.isDeleted());
			
			return pstmt.executeUpdate() == 1;

		} catch (Exception ex) {
			ex.printStackTrace();
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
			String query = "DELETE FROM movies where id = ?";
			
			pstmt = conn.prepareStatement(query);
//			System.out.println(pstmt);
			pstmt.setInt(1, id);

			return pstmt.executeUpdate() == 1;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	
	
	}

}
