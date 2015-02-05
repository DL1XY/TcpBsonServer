package de.darc.dl1xy.tcpbsonserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.io.EOFException;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.darc.dl1xy.tcpbsonserver.commands.CommandManager;
import de.darc.dl1xy.tcpbsonserver.commands.client.IClientCmd;
import de.darc.dl1xy.tcpbsonserver.commands.server.BaseServerCmd;
import de.darc.dl1xy.tcpbsonserver.commands.server.IServerCmd;
import de.darc.dl1xy.tcpbsonserver.commands.server.ServerCmdLogout;
import de.darc.dl1xy.tcpbsonserver.gameplay.Gameplay;
import de.undercouch.bson4jackson.BsonFactory;

@Sharable
public class TcpBsonServerHandler extends ChannelInboundHandlerAdapter {

	private SocketChannel socketChannel;
	private Channel channel;
	private CommandManager cmdMgr;
	private Gameplay gameplay;
	private BsonFactory factory; 
	private ObjectMapper mapper;
	
	public TcpBsonServerHandler(final BsonFactory factory, final ObjectMapper mapper, final SocketChannel sc)
	{
		this.socketChannel = sc;
		this.factory = factory;
		this.mapper = mapper;
		this.init();	
	}
	
	private void init()
	{
			this.cmdMgr = new CommandManager(this);
			// ad more gameplay related classes here
	}
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		
		this.channel = ctx.channel();
	}
	
	@Override
	public void channelRead(final ChannelHandlerContext ctx,final  Object msg) 
	{
		final IServerCmd cmd = this.getCmd(ctx, ((ByteBuf)msg).array());
		if (cmd != null)
			cmd.execute(gameplay, cmdMgr);
	}
	
	@Override
	public void channelReadComplete(final ChannelHandlerContext ctx) {
		ctx.flush();
	}
	
	/**
	 * Writer 
	 * @param cmd
	 */
	public void write(final IClientCmd cmd)
	{
		try 
		{
			channel.writeAndFlush(Unpooled.wrappedBuffer(mapper.writeValueAsBytes(cmd)));		    
		} 
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
	// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
	
	private BaseServerCmd getCmd(final ChannelHandlerContext ctx, final byte[] data)
	{
		try {
			 
			final JsonParser parser = factory.createJsonParser(data);
			parser.nextToken();
		
			while (parser.nextToken() != JsonToken.END_OBJECT) 
			{
				final String fieldname = parser.getCurrentName();
				parser.nextToken();
				
				if ("meta".equals(fieldname)) 
				{									
					final String metaFilename = "com.jomei.pirates.commands.server."+ parser.getText();
					
					@SuppressWarnings("rawtypes")
					final Class myClass = Class.forName(metaFilename);
					
					if (myClass != null) 
					{
						//LogUtil.getLogger().debug("Start mapper!");
						@SuppressWarnings("unchecked")
						final BaseServerCmd cmd = (BaseServerCmd) mapper.readValue(data, myClass);
						if (cmd instanceof ServerCmdLogout)
						{
							this.logout(ctx);
						}
						else
						{
							return cmd;															
						}
						return null;
					}
				}
			}
		} 
		catch (EOFException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		this.logout(ctx);
		return null;
	}	
	
	public void logout(final ChannelHandlerContext ctx)
	{
		if (gameplay != null)
		{
			gameplay.logout();
			gameplay = null;
		}
		
		if (ctx != null)
		{
			ctx.close();
			ctx.channel().close();
			ctx.channel().disconnect();
		}
		
		if (socketChannel != null)
		{
			socketChannel.close();
		}
	}
}
