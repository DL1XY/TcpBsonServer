package de.darc.dl1xy.tcpbsonserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.ByteOrder;
import java.util.List;


public class ByteToBSONDecoder extends ReplayingDecoder<ByteBuf> 
{
	private JomeiServerHandler serverHandler;
	
	public ByteToBSONDecoder(final JomeiServerHandler serverHandler)
	{
		this.serverHandler = serverHandler;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out)
	{	
		try
		{
			final int oldIndex = buf.readerIndex();
			final int msgLength = buf.order(ByteOrder.LITTLE_ENDIAN).readInt();
			out.add(buf.readerIndex(oldIndex).order(ByteOrder.LITTLE_ENDIAN).readBytes(msgLength).copy());
		} 
		catch (Exception e)
		{
			// connection lost, shut down
			if (ctx != null)
				serverHandler.logout(ctx);
		}
	}
}