package dao;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class ConnectionManager {

	private static final String DATABASE = "cinema.db";
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String WINDOWS_PATH = "C:"+ FILE_SEPARATOR + "Users"+ FILE_SEPARATOR + "savic"+ FILE_SEPARATOR + "git"+ FILE_SEPARATOR + "owp2019"+ FILE_SEPARATOR + "Cinema"+ FILE_SEPARATOR + DATABASE;
	private static final String LINUX_PATH = "SQLite"+ FILE_SEPARATOR + DATABASE;

	private static final String PATH = WINDOWS_PATH;
	
	private static DataSource dataSource;

	public static void open() {
		try {
			Properties dataSourceProperties = new Properties();
			dataSourceProperties.setProperty("driverClassName", "com.sqlite.JDBC");
			dataSourceProperties.setProperty("url", "jdbc::sqlite:" + PATH);
			
			dataSource = BasicDataSourceFactory.createDataSource(dataSourceProperties);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return null;
	}

}
