package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.types.UserData;

/**
 * This class handles all database related actions; specifically,
 * it provides an API to interact with MYSQL to help ensure safe
 * and consistent database calls. 
 *
 * TODO check connection, and if so, notify any client facing 
 * APIs of failure.
 * 
 */
public final class DatabaseHandler {
	
	private static Connection conn = null;
	// config file TODO
	private static final String DB_NAME = "webapp";
	
	/**
	 * Connects to the MYSQL server as the root user.
	 * Initializes the required 'mysite' database and all
	 * the associated tables.
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
				// config file TODO
			    conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
			    initDatabase();
			    return true;
			} catch (SQLException e) {
			    // TODO log
			    System.out.println("SQLException: " + e.getMessage());
			    System.out.println("SQLState: " + e.getSQLState());
			    System.out.println("VendorError: " + e.getErrorCode());
			}
		};
		// not connected
		return false;
	}
	
	/**
	 * Load the JDBC driver for MYSQL
	 * @return 
	 * 		True if the driver was loaded.
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
		PreparedStatement stmt = conn.prepareStatement(
				"CREATE DATABASE IF NOT EXISTS " + DB_NAME);
		stmt.execute();
		
		// select the database for usage
		stmt = conn.prepareStatement("USE " + DB_NAME);
		stmt.execute();
		
		// create all required schema
		stmt = conn.prepareStatement(new UserData().getSchema());
		stmt.execute();
	}
	
	/**
	 * Close the database connection.
	 * @return 
	 * 		True if the database is not connected.
	 */
	public static boolean disconnectDatabase() {
		// check for existing connection
		if (conn != null) {
			try {
				conn.commit();
				conn.close();
				return true;
			} catch (SQLException e) {
				// TODO log
				e.printStackTrace();
			}
			return false;
		}
		// not connected
		else {
			return true;
		}
	}
	
	/**
	 * Adds a record with the specified values to the named table.
	 * The size of the cols and vals arrays should be equivalent.
	 * @param table
	 * 		The table to add the record to.
	 * @param cols
	 * 		The columns of the record.
	 * @param vals
	 * 		The values of the record.
	 * @throws SQLException 
	 * 		If a SQL Error occurs.
	 */
	public static void addRecord(String table, String[] cols, String[] vals) throws SQLException {
			StringBuilder sb = new StringBuilder();
			// convert vals array to CSV String
			for (int i = 0; i < vals.length; i++) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append("\"" + vals[i] + "\"");
			}
			String values = sb.toString();
			
			sb = new StringBuilder();
			// convert cols array to CSV String
			for (int i = 0; i < cols.length; i++) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(cols[i]);
			}
			String columns = sb.toString();
			
			// execute SQL insert statement
			String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
					table, columns, values);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();
	}
	
	/**
	 * Deletes records from the named table that match the given where clause.
	 * @param table
	 * 		The table whose records to delete.
	 * @param where
	 * 		The WHERE clause.
	 * @throws SQLException 
	 * 		If a SQL Error occurs.
	 * 
	 * TODO support joins (should use String[] tables)
	 */
	public static void deleteRecords(String table, String where) throws SQLException {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM ").append(table)
			.append(" WHERE ").append(where);
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.execute();
	}
	
	/**
	 * Updates records from the named table that match the given where clause.
	 * The size of the cols and vals arrays should be equivalent.
	 * @param table
	 * 		The table whose records to update.
	 * @param cols
	 * 		The columns to update.
	 * @param vals
	 * 		The values to update the corresponding columns to.
	 * @param where
	 * 		The WHERE clause.
	 * @throws SQLException 
	 * 		If a SQL Error occurs.
	 * TODO support joins (String[] tables)
	 * TODO update values
	 */
	public static void updateRecords(String table, String[] cols, String[] vals, String where) 
			throws IllegalArgumentException, SQLException {
		
		if (vals.length != cols.length) {
			throw new IllegalArgumentException("Invalid: cols don't match vals.");
		}
		
		StringBuilder sb = new StringBuilder();
		// convert vals array to CSV String
		for (int i = 0; i < vals.length; i++) {
			if (sb.length() > 0) { // TODO change to i
				sb.append(",");
			}
			sb.append(cols[i] + "=\"" + vals[i] + "\"");
		}
		String set = sb.toString();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(table)
		.append(" SET ").append(set)
		.append(" WHERE ").append(where);
		PreparedStatement stmt = conn.prepareStatement(sql.toString());
		stmt.execute();
	}
	
}
