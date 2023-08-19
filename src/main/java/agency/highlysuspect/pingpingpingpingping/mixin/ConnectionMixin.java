package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class ConnectionMixin {
	@Shadow public abstract PacketFlow getReceiving();
	@Shadow public abstract PacketFlow getSending();
	
	@Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
	private void pingpingpingpingping$onPacket(ChannelHandlerContext bla, Packet<?> packet, CallbackInfo ci) {
		if(!PingPingPingPingPing.CAPTURING || getReceiving() != PacketFlow.CLIENTBOUND) return;
		
		Integer size = PingPingPingPingPing.RECEIVED_PACKET_SIZES.getIfPresent(packet);
		if(size == null) size = 0;
		
		PingPingPingPingPing.recorder.record(packet, PacketFlow.CLIENTBOUND, size);
	}
	
	@Inject(method = "sendPacket", at = @At("HEAD"))
	private void ping5$outgoing(Packet<?> packet, @Nullable PacketSendListener packetSendListener, CallbackInfo ci) {
		if(!PingPingPingPingPing.CAPTURING || getSending() != PacketFlow.SERVERBOUND) return;
		
		Integer size = PingPingPingPingPing.SENT_PACKET_SIZES.getIfPresent(packet);
		if(size == null) size = 0;
		
		PingPingPingPingPing.recorder.record(packet, PacketFlow.SERVERBOUND, size);
	}
}
