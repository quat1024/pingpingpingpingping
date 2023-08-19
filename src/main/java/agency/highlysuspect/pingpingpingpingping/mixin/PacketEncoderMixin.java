package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PacketEncoder.class)
public class PacketEncoderMixin {
	@Inject(
		method = "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/network/FriendlyByteBuf;writerIndex()I",
			ordinal = 1
		),
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	public void ping5$sizeOutgoingPackets(ChannelHandlerContext channelHandlerContext, Packet<?> packet, ByteBuf byteBuf, CallbackInfo ci, ConnectionProtocol prot, int begin, FriendlyByteBuf buf2, int end) {
		if(!PingPingPingPingPing.CAPTURING) return;
		
		//maybe these locals are named wrong, but yeah, without the abs i do record sizes with the wrong sign
		PingPingPingPingPing.SENT_PACKET_SIZES.put(packet, Math.abs(end - begin));
	}
}
