package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class handles all database related actions.
 * TODO explain further
 */
public final class DatabaseHandler {
	
	private static Connection conn = null;
	
	/**
	 * Connects to the MYSQL server as the root user.
	 * Initializes the required 'mysite' database and all
	 * the associated tables. TODO create db and tables
	 */
	public static boolean connectDatabase() {
		// check if already connected
		if (conn != null) {
			return true;
		}
		// check driver loaded
		else if (loadDriver()) {
			// try to establish connection
			try {
			    conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", ""); // TODO change from localhost?
			    initDatabase();
			    return true;
			} catch (SQLException ex) {
			    // handle any errors, TODO log them
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		};
		// not connected
		return false;
	}
	
	/**
	 * Load the JDBC driver for MYSQL
	 * @return true if the driver was loaded.
	 */
	private static boolean loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return true;
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Creates the database if it does not already exist.
	 * Creates the tables as well, if they don't exist.
	 * @throws SQLException
	 */
	private static void initDatabase() throws SQLException {
		// create the database if not exists
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE DATABASE IF NOT EXISTS webapp"); // TODO predefine database name in config file
		PreparedStatement stmt = conn.prepareStatement(sql.toString());
		stmt.execute();
		
		// select the database for usage
		stmt = conn.prepareStatement("USE webapp");
		stmt.execute();
		
		// create tables if not exists
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS users(");
		sql.append("id INT PRIMARY KEY AUTO_INCREMENT,");
		sql.append("username VARCHAR(25) NOT NULL,");
		sql.append("password VARCHAR(65) NOT NULL,");
		sql.append("email VARCHAR(255) NOT NULL)");
		stmt = conn.prepareStatement(sql.toString());
		stmt.execute();
	}
	
	/**
	 * Close the database connection.
	 * @return true if the database is not connected.
	 */
	public static boolean disconnectDatabase() {
		// check for existing connection
		if (conn != null) {
			try {
				conn.commit();
				conn.close();
				return true;
			} catch (SQLException e) {
				// TODO log?
				e.printStackTrace();
			}
			return false;
		}
		// not connected
		else {
			return true;
		}
	}
}
