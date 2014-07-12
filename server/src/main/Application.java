package main;

import server.Server;
import database.DatabaseHandler;

/**
 * Main application. TODO describe better
 * 
 * Launches the application web server as well as
 * connects to the database server.
 * 
 * TODO clean shutdown
 */
public class Application {
	public static void main(String[] args) {
		// try to connect to the database
		if (DatabaseHandler.connectDatabase()) {
		    System.out.println("Database connected.");
			// try to launch the server
			if (Server.launchServer()) {
				// TODO do anything here?
			    System.out.println("Server running.");
			}
			// disconnect the database, shutdown application with error
			else {
				DatabaseHandler.disconnectDatabase();
				System.exit(20);
			};
		}
		// could not connect to database, shutdown application with error
		else {
			System.exit(10);
		};
	}
}
