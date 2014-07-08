package server.responders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import server.Responder;

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
	public void processRequest(ChannelHandlerContext ctx, HttpRequest req) {
		sendHttpResponse(ctx, req, "Hello"); // TODO
	}
	
}
