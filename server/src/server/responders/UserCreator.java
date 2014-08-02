package server.responders;

import java.sql.SQLException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import server.Responder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import database.types.UserData;
import database.types.UserData.PasswordException;

/**
 * This class handles incoming requests dealing with user registration.
 * 
 * Change name to UserRegistration.
 */
public final class UserCreator extends Responder {

	@Override
	public void processRequest(ChannelHandlerContext ctx, HttpRequest req, String content) {
		try {
			
			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(content).getAsJsonObject();
			String uri = req.getUri();
			
			// attempt to add a new user with posted content
			if (uri.equals("/admin/create")) {
				UserData.registerUser(json);
				sendHttpResponse(ctx, req, "Added user!");
			}
			// attempt to update a user
			else if (uri.equals("/admin/update")) {
				UserData.updateUser(json);
				sendHttpResponse(ctx, req, "Updated user!");
			}
			// attempt to delete user with posted content
			else if (uri.equals("/admin/delete")) {
				UserData.unregisterUser(json);
				sendHttpResponse(ctx, req, "Removed user!");
			}

			// could not parse posted content
		} catch (JsonParseException e) {
			sendHttpError(ctx, req, "Invalid json: " + content);
			// incorrect key bindings in json or invalid password
			// SQL Error occurred TODO distinguish username or email exists
		} catch (PasswordException | IllegalArgumentException | SQLException e) {
			sendHttpError(ctx, req, e.getMessage());
		}
	}
	
}
