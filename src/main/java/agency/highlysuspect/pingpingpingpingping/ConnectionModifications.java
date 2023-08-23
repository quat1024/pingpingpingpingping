package agency.highlysuspect.pingpingpingpingping;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.NotNull;

public class ConnectionModifications {
	public static void modify(Connection connection, ChannelHandlerContext ctx) {
		if(connection.getSending() != PacketFlow.SERVERBOUND) return;
		
		boolean local = connection.isMemoryConnection();
		PingPingPingPingPing.LOGGER.warn("local: {}, connection: {}, sending: {}", local, connection, connection.getSending());
		
		ChannelPipeline pipe = ctx.pipeline();
		
		pipe.addBefore("packet_handler", "ping5-log-incoming-packet", new InboundStateCollectingAdapter());
		pipe.addBefore("packet_handler", "ping5-log-outgoing-packet", new OutboundStatCollectingAdapter());
		
		//pipe.addBefore("packet_handler", "ping5-receiving", new InLogspam("got packet:\t"));
		//pipe.addBefore("packet_handler", "ping5-sending", new OutLogspam("sent packet:\t"));
		
		//local channels only have one pipeline element (named "packet_handler"), it just kicks Packet objects back and forth
		
		if(!local) {
			//the sending half of netty pipelines is "backwards", in that data enters from the addLast end and goes up through addFirst
			//this is the reverse of the receiving half, which enters through addFirst and goes down through addLast, as you'd expect
			//pipe.addFirst("ping5-received", new InLogspam("got bytebuf:\t"));
			//pipe.addFirst("ping5-sent", new OutLogspam("sent bytebuf:\t"));
		}
	}
	
	//TODO: Im a super Netty noob
	// maybe MessageToMessageEncoder would be a good class to look at
	
	public static class InboundStateCollectingAdapter extends ChannelInboundHandlerAdapter {
		@Override
		public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
			if(msg instanceof Packet<?> packet && PingPingPingPingPing.CAPTURING) {
				Integer size = PingPingPingPingPing.RECEIVED_PACKET_SIZES.getIfPresent(packet);
				if(size == null) size = 0;
				
				PingPingPingPingPing.recorder.record(packet, PacketFlow.CLIENTBOUND, size);
			}
			
			super.channelRead(ctx, msg);
		}
	}
	
	public static class OutboundStatCollectingAdapter extends ChannelOutboundHandlerAdapter {
		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			if(msg instanceof Packet<?> packet && PingPingPingPingPing.CAPTURING) {
				
				//TODO: fundamentally broken, the packet hasn't been encoded to bytes yet !!!
				
				Integer size = PingPingPingPingPing.SENT_PACKET_SIZES.getIfPresent(packet);
				if(size == null) size = 0;
				
				PingPingPingPingPing.recorder.record(packet, PacketFlow.SERVERBOUND, size);
			}
			
			super.write(ctx, msg, promise);
		}
	}
	
	public static class InLogspam extends ChannelInboundHandlerAdapter {
		public InLogspam(String logMessage) {
			this.logMessage = logMessage;
		}
		
		String logMessage;
		
		@Override
		public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
			PingPingPingPingPing.LOGGER.info(this.logMessage + msg);
			super.channelRead(ctx, msg);
		}
	}
	
	public static class OutLogspam extends ChannelOutboundHandlerAdapter {
		public OutLogspam(String logMessage) {
			this.logMessage = logMessage;
		}
		
		private String logMessage;
		
		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			PingPingPingPingPing.LOGGER.info(this.logMessage + msg);
			super.write(ctx, msg, promise);
		}
	}
}
