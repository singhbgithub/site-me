package server.responders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import server.Responder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

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
			JsonParser parser = new JsonParser(); // Should use 1 static parser instance?
			JsonObject json = parser.parse(content).getAsJsonObject();
			
			if (json.has("user") && json.has("password")) { // TODO add e-mail
				String user = json.get("user").getAsString();
				String password = json.get("password").getAsString();
				// add user to db TODO
				sendHttpResponse(ctx, req, "Added user!");
			}
			else {
				sendHttpError(ctx, req, "No user.");
			}
		} catch (JsonParseException e) {
			sendHttpError(ctx, req, "Invalid json: " + content);
		}
	}
	
}
