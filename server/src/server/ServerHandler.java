package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Handles incoming HTTP requests and delegate to the correct {@link Responder}
 * to respond.
 */
public final class ServerHandler extends SimpleChannelInboundHandler<Object> {

	/** Finished receiving message. */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /** Message was received. */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
    	// check for HTTP requests only
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            String uri = req.getUri();
            // get the appropriate responder
            Responder responder = Server.URL_MAPPER.get(uri);
            
            // process request
            if (responder != null) {
            	responder.processRequest(ctx, req);
            }
            // the url doesn't have a responder
            else {
            	Responder.sendHttpError(ctx, req, "Unsupported", 
            			String.format("The uri:%s is not supported.", uri));
            }
        }
    }

    /** Exception handling. */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace(); // TODO log?
        ctx.close();
    }
}