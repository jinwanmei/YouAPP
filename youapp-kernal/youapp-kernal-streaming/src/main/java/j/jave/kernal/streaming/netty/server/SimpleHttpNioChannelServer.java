package j.jave.kernal.streaming.netty.server;

import java.io.Closeable;
import java.io.IOException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

public class SimpleHttpNioChannelServer implements Closeable {

	private static final JLogger LOGGER = JLoggerFactory.getLogger(SimpleHttpNioChannelServer.class);
	
	private int port;
	
	private final boolean useSSL;
	
	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workerGroup;

	public SimpleHttpNioChannelServer(int port, boolean useSSL) {
		this.port = port;
		this.useSSL = useSSL;
	}
	
	public SimpleHttpNioChannelServer(int port) {
		this(port,false);
	}
	
	public void start() throws Exception{
		
		 // Configure SSL.
        final SslContext sslCtx;
        if (useSSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(3);
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new SimpleHttpServerInitializer(sslCtx))
             .bind(port);
            this.bossGroup=bossGroup;
            this.workerGroup=workerGroup;
            System.err.println("Open your web browser and navigate to " +
                    (useSSL? "https" : "http") + "://127.0.0.1:" + port + '/');
            
        } catch (Exception e){
        	LOGGER.error(e.getMessage(), e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}

	@Override
	public void close() throws IOException {
		bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		
		SimpleHttpNioChannelServer channelServer=new SimpleHttpNioChannelServer(8080);
		try{
			channelServer.start();
		}catch (Exception e) {
			channelServer.close();
		}
		Thread.sleep(100000);
		
	}
}