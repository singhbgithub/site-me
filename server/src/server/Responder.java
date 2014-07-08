package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import com.google.gson.JsonObject;

/**
 * Interface to provide convenience methods for
 * responding to requests appropriately.
 */
public abstract class Responder {
	
	/**
	 * Send a default HttpResponse (200 SUCCESS).
	 * @param ctx
	 * @param req
	 */
	public static final void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, String msg) {
		System.out.println("Success: " + msg + "\n" + req.getUri());// TODO
	};
	
	/**
	 * Send a default HttpError.
	 * @param ctx
	 * @param req
	 */
	public static final void sendHttpError(ChannelHandlerContext ctx, HttpRequest req, String group, String details) {
		System.out.println("Fail. " + req.getUri());// TODO
	};
	
	/**
	 * Send a JSON response HttpResponse (200 SUCCESS).
	 * @param ctx
	 * @param req
	 */
	public static final void sendJsonResponse(ChannelHandlerContext ctx, HttpRequest req, JsonObject msg) {
		// TODO
	};
	
	/**
	 * Process requests uniquely.
	 * @param ctx - the context.
	 * @param req - the request to process.
	 */
	public abstract void processRequest(ChannelHandlerContext ctx, HttpRequest req);
}
