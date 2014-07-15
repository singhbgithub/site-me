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
 * This class handles incoming requests dealing with user creation.
 */
public final class UserCreator extends Responder {

	@Override
	public void processRequest(ChannelHandlerContext ctx, HttpRequest req, String content) {
		try {
			
			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(content).getAsJsonObject();
			
			// attempt to add a new user with posted content
			UserData.registerUser(json);
			sendHttpResponse(ctx, req, "Added user!");

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
