package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class ConnectionMixin {
	@Shadow public abstract PacketFlow getReceiving();
	
	@Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
	private void pingpingpingpingping$onPacket(ChannelHandlerContext bla, Packet<?> packet, CallbackInfo ci) {
		if(!PingPingPingPingPing.CAPTURING || getReceiving() != PacketFlow.CLIENTBOUND) return;
		
		Integer size = PingPingPingPingPing.PACKET_SIZES.getIfPresent(packet);
		if(size == null) size = 0;
		
		PingPingPingPingPing.recorder.record(packet, getReceiving(), size);
	}
}
