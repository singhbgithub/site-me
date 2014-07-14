package server.responders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import server.Responder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import database.DatabaseHandler;

/**
 * This class creates a new user in the database.
 * 
 * TODO:
 * - User needs to have password.
 * - Clean way of adding new information to a user without corrupting old data entries.
 * - Encoding password.
 */
public class UserCreator extends Responder {

	@Override
	public void processRequest(ChannelHandlerContext ctx, HttpRequest req, String content) {
		try {
			
			JsonParser parser = new JsonParser(); // Should use 1 static parser instance? TODO
			JsonObject json = parser.parse(content).getAsJsonObject();
			
			// check for relevant information
			if (json.has("username") && json.has("password") && json.has("email")) {
				
				String user = json.get("username").getAsString();
				String password = json.get("password").getAsString();
				String email = json.get("email").getAsString();
				
				// add users to database
				if (DatabaseHandler.registerUser(user, password, email)) {
					sendHttpResponse(ctx, req, "Added user!");	
				}
				// user not added
				else {
					sendHttpResponse(ctx, req, "Could not add user.");
				}
			}
			// invalid post data
			else {
				sendHttpError(ctx, req, "No user, password, or email in json.");
			}
		} catch (JsonParseException e) {
			sendHttpError(ctx, req, "Invalid json: " + content);
		}
	}
	
}
