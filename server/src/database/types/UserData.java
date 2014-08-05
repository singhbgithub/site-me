package database.types;

import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.JsonObject;

import database.DatabaseHandler;
import database.DatabaseTable;

/**
 * User table.
 */
public final class UserData implements DatabaseTable {
	
	// maximum password length
	private static final int PASS_MAX = 32;
	// minimum password length
	private static final int PASS_MIN = 8;
	// database table name
	private static final String TABLE_NAME = "users";
	// database table column names
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	// convenience constant
	private static final String NEW = "new";
	
	@Override
	public String getSchema() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
		.append("(id BIGINT PRIMARY KEY AUTO_INCREMENT,")
		.append(USERNAME).append(" VARCHAR(25) NOT NULL UNIQUE,")
		.append(PASSWORD).append(" VARCHAR(65) NOT NULL,")
		.append(EMAIL).append(" VARCHAR(255) NOT NULL UNIQUE)")
		.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		return sql.toString();
	}
	
	/**
	 * Registers a new user with the given {@link com.google.gson.JsonObject}.
	 * If the user or email is already registered, an error is thrown.
	 * @param json
	 * 		A {@link com.google.gson.JsonObject} that should contain
	 * 		"username", "password", and "email" key bindings.
	 * @return 
	 * 		True if the user was successfully registered.
	 * @throws PasswordException
	 * 		If the "password" key is not between 8 and 32 characters.
	 * @throws SQLException 
	 * 		If a SQL error occurs
	 * @throws IllegalArgumentException
	 * 		If the json does not contain the "username", "password",
	 * 		and "email" key bindings.
	 * 
	 * TODO hash passwords, and use base64 encoder
	 */
	public static void registerUser(JsonObject json)
			throws PasswordException, SQLException, IllegalArgumentException {
		
		// check for valid key bindings
		if (json.has(USERNAME) && json.has(PASSWORD) && json.has(EMAIL)) {

			String username = json.get(USERNAME).getAsString();
			String password = json.get(PASSWORD).getAsString();
			String email = json.get(EMAIL).getAsString();
		
			// password is not correct length.
			if (password.length() > PASS_MAX ||
					password.length() < PASS_MIN) {
				throw new PasswordException(
						String.format("Password must be between %s and %s characters long.",
								PASS_MIN, PASS_MAX));
			}
			// add record to table
			DatabaseHandler.addRecord(TABLE_NAME,
					new String[] {USERNAME, PASSWORD, EMAIL},
					new String[] {username, password, email});

		}
		else {
		
			throw new IllegalArgumentException(
					String.format("%s does not contain the keys: '%s', '%s', '%s'",
							json, USERNAME, PASSWORD, EMAIL));
			
		}
		
	}
	
	/**
	 * Unregisters a new user with the given {@link com.google.gson.JsonObject}.
	 * If the user or email is not registered or the passwords do not match,
	 * an error is thrown.
	 * @param json
	 * 		A {@link com.google.gson.JsonObject} that should contain
	 * 		"username", "password", and "email" key bindings.
	 * @return 
	 * 		True if the user was successfully unregistered.
	 * @throws SQLException 
	 * 		If a SQL error occurs
	 * @throws IllegalArgumentException
	 * 		If the json does not contain the "username", "password",
	 * 		and "email" key bindings.
	 * 
	 * TODO hash passwords, and use base64 encoder
	 * TODO unregister using username or email, both not required
	 */
	public static void unregisterUser(JsonObject json) 
			throws SQLException, IllegalArgumentException {
		
		// check for valid key bindings
		if (json.has(USERNAME) && json.has(PASSWORD) && json.has(EMAIL)) {

			String username = json.get(USERNAME).getAsString();
			String password = json.get(PASSWORD).getAsString();
			String email = json.get(EMAIL).getAsString();
			
			String where = USERNAME + "=\"" + username + "\" AND "
					+ PASSWORD + "=\"" + password + "\" AND " 
					+ EMAIL + "=\"" + email + "\"";
			
			// delete record from table
			DatabaseHandler.deleteRecords(TABLE_NAME, where);

		}
		else {
		
			throw new IllegalArgumentException(
					String.format("%s does not contain the keys: '%s', '%s', '%s'",
							json, USERNAME, PASSWORD, EMAIL));
			
		}
		
	}
	
	/**
	 * Updates a new user with the given {@link com.google.gson.JsonObject}.
	 * If the user or email is not registered or the passwords do not match,
	 * an error is thrown.
	 * @param json
	 * 		A {@link com.google.gson.JsonObject} that should contain
	 * 		"username", "password", and "email" key bindings.
	 * @return 
	 * 		True if the user was successfully updated.
	 * @throws SQLException 
	 * 		If a SQL error occurs
	 * @throws IllegalArgumentException
	 * 		If the json does not contain the "username", "password",
	 * 		and "email" key bindings.
	 * 
	 * TODO hash passwords, and use base64 encoder
	 * TODO update using username or email, both not required
	 */
	public static void updateUser(JsonObject json) 
			throws SQLException, IllegalArgumentException {
		
		// check for valid key bindings
		if (json.has(USERNAME) && json.has(PASSWORD) && json.has(EMAIL)) {

			String username = json.get(USERNAME).getAsString();
			String password = json.get(PASSWORD).getAsString();
			String email = json.get(EMAIL).getAsString();
			
			// key's whose values should be updated in the database
			HashMap<String, String> setValues = new HashMap<String, String>();
			
			// update password
			if (json.has(NEW + PASSWORD)) {
				
				String newPassword = json.get(NEW + PASSWORD).getAsString();
				setValues.put(PASSWORD, newPassword);
				
			}

			// update email
			if (json.has(NEW + EMAIL)) {
				
				String newEmail = json.get(NEW + EMAIL).getAsString();
				setValues.put(EMAIL, newEmail);
				
			}
			
			String where = USERNAME + "=\"" + username + "\" AND "
					+ PASSWORD + "=\"" + password + "\" AND " 
					+ EMAIL + "=\"" + email + "\"";
			
			// add record to table
			DatabaseHandler.updateRecords(TABLE_NAME,
					setValues,
					where);

		}
		else {
	
			throw new IllegalArgumentException(
					String.format("%s does not contain the keys: '%s', '%s', '%s'",
							json, USERNAME, PASSWORD, EMAIL));
			
		}
		
	}
	
	/**
	 * The exception to be thrown when a password is invalid.
	 */
	public static class PasswordException extends Exception {
		
		private static final long serialVersionUID = -493405386733101157L;
		private String message = null;
	 
	    public PasswordException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    @Override
	    public String toString() {
	        return message;
	    }
	 
	    @Override
	    public String getMessage() {
	        return message;
	    }
	}
}
