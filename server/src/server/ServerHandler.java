package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;

/**
 * Handles incoming HTTP requests and delegate to the correct {@link Responder}
 * to respond.
 */
public final class ServerHandler extends SimpleChannelInboundHandler<Object> {
	private HttpRequest req;
	private Responder responder;
	
	/** Finished receiving message. */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /** Message was received. */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
    	
    	// HTTP request
        if (msg instanceof HttpRequest) {
        	req = (HttpRequest) msg;
        }
        
        // HTTP request content
        if (msg instanceof HttpContent) {
        	
        	// get responder for URI
        	String uri = req.getUri();
            responder = Server.URL_MAPPER.get(uri);
            // get UTF-8 decoded content
            String content = ((HttpContent) msg).content().toString(CharsetUtil.UTF_8);
            
            // respond to request
            if (responder != null) {
            	responder.processRequest(ctx, req, content);
            }
            // the URI doesn't have a responder
            else {
            	Responder.sendHttpError(ctx, req, 
            			String.format("The uri: %s is not supported.", req.getUri()));
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