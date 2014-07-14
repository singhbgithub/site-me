package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Initialize server channels, handlers.
 */
final class ServerInitializer extends ChannelInitializer<SocketChannel> {

	// initialize with server options
	private final Server server;
	ServerInitializer(Server server) {
		this.server = server;
	}
	
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec()); // Configure HTTP Chunk, decoder, encoder options. TODO
        p.addLast(new ServerHandler(server));
    }
}