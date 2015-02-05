package de.darc.dl1xy.tcpbsonserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

public class TcpBsonServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(final SocketChannel ch) throws Exception {
		
		final BsonFactory factory = new BsonFactory();
		final ObjectMapper mapper = new ObjectMapper(factory);
		final TcpBsonServerHandler serverHandler = new TcpBsonServerHandler(factory, mapper, ch);
		
		final ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ByteToBSONDecoder(serverHandler));
		pipeline.addLast(serverHandler);
	}
}