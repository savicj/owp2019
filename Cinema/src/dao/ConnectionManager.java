package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class ConnectionManager {

	private static final String DATABASE = "cinema.db";
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String WINDOWS_PATH = "E:" + FILE_SEPARATOR + "aps" + FILE_SEPARATOR + "SQLite" + FILE_SEPARATOR + DATABASE;
	private static final String LINUX_PATH = "SQLite"+ FILE_SEPARATOR + DATABASE;

	private static final String PATH = WINDOWS_PATH;
	
	private static Connection conn;

	public static void open() {
		try {
			Class.forName("org.sqlite.JDBC");
			
			conn = DriverManager.getConnection("jdbc::sqlite:"+PATH);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return conn;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return null;
	}

	public static void close() {
		try {
			conn.close();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}


}
