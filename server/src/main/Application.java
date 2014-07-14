package main;

import server.Server;
import server.URIMapper;
import database.DatabaseHandler;

/**
 * This is the main application; it's role is to launch the Netty web server,
 * connect to MYSQL database server and create relevant schema, and initialize/launch
 * all other components. This application is designed for Unix based systems. 
 * 
 * If a connection cannot be established to MYSQL server, the application will exit
 * with status 10.
 * 
 * If the Netty web server component cannot be launched, the database connection is
 * closed, and the application will exit with status 20.
 * 
 * Upon normal program exit or termination, the application will shutdown the server
 * gracefully and disconnect the database connection after committing pending 
 * transactions. The program is not robust against SIGKILL or thunderstorms.
 */
public final class Application {
	
	public static void main(String[] args) {
		// try to connect to the database
		if (DatabaseHandler.connectDatabase()) {
			
		    System.out.println("Database connected."); // TODO log
			// initialize web server options
		    Server server = new Server(8000, URIMapper.getInstance());
			
			// try to launch the server
			if (server.launchServer()) {
				
			    System.out.println("Server running."); // TODO log
			    shutdownGracefully();
			}
			// disconnect the database, shutdown application with error
			else {
				
				System.err.println("Could not start server."); // TODO log?
				DatabaseHandler.disconnectDatabase();
				System.exit(20);
				
			};
		}
		// could not connect to database, shutdown application with error
		else {
			
			System.err.println("Could connect database."); // TODO log?
			System.exit(10);
			
		};
	}
	
	/**
	 * Adds shutdown hook to ensure all application modules clean up
	 * and close connections before the application terminates.
	 */
	private static void shutdownGracefully() {
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown()));
	}
}

/**
 * Shutdown the application gracefully. The Netty server framework
 * already provides a shutdown gracefully method; this is called
 * in the {@link Server} launchMethod.
 */
class Shutdown implements Runnable {
	
	@Override
	public void run() {
		// close the database connection
		DatabaseHandler.disconnectDatabase();
	}
	
}