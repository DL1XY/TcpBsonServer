
package de.darc.dl1xy.tcpbsonserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Properties;

import de.darc.dl1xy.tcpbsonserver.netty.TcpBsonServerInitializer;
import de.darc.dl1xy.tcpbsonserver.util.PropertyReader;

public class TcpBsonServer {

	/**
	 * @param args
	 */

	public static void main(String[] args) 
	{
	
		if (args.length == 1)
		{
			try {
					
				// read properties from cmd line
				final PropertyReader pr = new PropertyReader();
				final Properties props = pr.readProps(args[0]);
				
				if (props == null)
				{
					System.exit(-1);
					return;
				}
						
				// tcp				
				final int port = Integer.parseInt(props.getProperty("port"));
				
				final EventLoopGroup bossGroup = new NioEventLoopGroup();
				final EventLoopGroup workerGroup = new NioEventLoopGroup();
				final LoggingHandler nettyLoggingHandler = new LoggingHandler(LogLevel.ERROR);
				
				try {
					
					final TcpBsonServerInitializer tcpInitializer = new TcpBsonServerInitializer();
					
					// tcp
					final ServerBootstrap b = new ServerBootstrap();
					b.group(bossGroup, workerGroup)
						.channel(NioServerSocketChannel.class)
						.handler(nettyLoggingHandler)
						.childHandler(tcpInitializer)
						.option(ChannelOption.SO_BACKLOG, 16 * 1024)
						.option(ChannelOption.TCP_NODELAY, true)
						.option(ChannelOption.SO_KEEPALIVE, false)
						.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
						.childOption(ChannelOption.TCP_NODELAY, true)
						.childOption(ChannelOption.SO_KEEPALIVE, false);

					final ChannelFuture tcpFuture = b.bind(port).sync();
					tcpFuture.channel().closeFuture().sync();					
					
				} catch (Exception e) {
					e.printStackTrace();				
				} finally {
					bossGroup.shutdownGracefully();
					workerGroup.shutdownGracefully();
				}				
			} 
			catch (NumberFormatException e)
			{
				e.printStackTrace();
				System.exit(-1);
			} 
			catch (Exception e) 
			{
				
				e.printStackTrace();
				System.exit(-1);
			}
		}
		else 
		{
			printUsage("Wrong number of arguments");
		}
	}
	
	public static void printUsage(final String msg)
	{
		
		System.out.println("Usage: java TcpBsonServer <config_file>");
	}	
}
