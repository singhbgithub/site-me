package server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

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
		
		// HTTP 200 response
		HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
		
		// set content type, plain text
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		
		// check keep alive connection? TODO
		// close the connection
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	};
	
	/**
	 * Send a default HttpError.
	 * @param ctx
	 * @param req
	 */
	public static final void sendHttpError(ChannelHandlerContext ctx, HttpRequest req, String details) {
		
		// HTTP 400 response
		HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST,
				Unpooled.copiedBuffer(details, CharsetUtil.UTF_8));
		
		// close the connection
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	};
	
	/**
	 * Send a JSON response HttpResponse (200 SUCCESS).
	 * @param ctx
	 * 			the context of the channel.
	 * @param req
	 */
	public static final void sendJsonResponse(ChannelHandlerContext ctx, HttpRequest req, JsonObject msg) {
		sendHttpResponse(ctx, req, msg.toString());
	};
	
	/**
	 * Process requests uniquely.
	 * @param ctx
	 * 			the context.
	 * @param req
	 * 			the request to process.
	 * @param content
	 * 			the content associated to the request.
	 */
	public abstract void processRequest(ChannelHandlerContext ctx, HttpRequest req, String content);
}
