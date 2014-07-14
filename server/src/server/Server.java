package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Map;

/**
 * An HTTP web server for bhavneetsingh.com.
 * 
 * TODO
 * - Use HTTPS server, and don't use basic authorization, it's ugly. See if
 * - it's even necessary, or if a local reverse-proxy from Nginx
 *   from a HTTPS port is enough.
 * 
 */
public final class Server {
	// port to listen for requests on
    private final int port;
    // map of supported URIs (API calls)
    final Map<String, Responder> uris;

    // initialize server on a port, and supported APIs
    public Server(int port, Map<String, Responder> uris) {
    	this.port = port;
    	this.uris = uris;
    }
    
    /**
     * Launch the server.
     * @throws Exception
     * @return true if the server launched successfully.
     */
    public boolean launchServer() {
    	// server configuration details
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO)) // TODO change to log4j
             .childHandler(new ServerInitializer(this));

            Channel ch = b.bind(port).sync().channel();

            System.out.println("Open your web browser and navigate to "
            		+ "http://127.0.0.1:" + port + '/'); // TODO log message
            ch.closeFuture().sync();
            
            return true;
        } catch (InterruptedException e) {
			// TODO log? 
			e.printStackTrace();
		} finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        return false;
    }
}