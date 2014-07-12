package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles all database related actions.
 * TODO explain further
 */
public final class DatabaseHandler {
	
	private static Connection CONN = null;
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
	 * Connects to the MYSQL server as the root user.
	 * Initializes the required 'mysite' database and all
	 * the associated tables. TODO create db and tables
	 */
	public static boolean connectDatabase() {
		// check if already connected
		if (CONN != null) {
			return true;
		}
		// check driver loaded
		else if (loadDriver()) {
			// try to establish connection
			try {
			    CONN = DriverManager.getConnection("jdbc:mysql://singhb.local:3306/", "root", "");
			    // Do something with the Connection TODO
			    return true;
			} catch (SQLException ex) {
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		};
		// not connected
		return false;
	}
	
	/**
	 * Close the database connection.
	 * @return true if the database is not connected.
	 */
	public static boolean disconnectDatabase() {
		// check for existing connection
		if (CONN != null) {
			try {
				CONN.commit();
				CONN.close();
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
