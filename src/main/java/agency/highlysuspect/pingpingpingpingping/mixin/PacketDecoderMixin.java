package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PacketDecoder.class)
public class PacketDecoderMixin {
	private final ThreadLocal<Integer> readableByteCache = ThreadLocal.withInitial(() -> 0);
	
	//Obviously creating the packet from the network consumes bytes from the buf, so we need two injections:
	//one to store the readableBytes value before the packet clobbers it, and another later when we do have the packet
	//so we can associate the size with that packet.
	
	@Inject(method = "decode", at = @At("HEAD"))
	public void ping5$sizeIncomingPackets1(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci) {
		if(PingPingPingPingPing.CAPTURING) readableByteCache.set(byteBuf.readableBytes());
	}
	
	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(
		method = "decode",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/network/ConnectionProtocol;createPacket(Lnet/minecraft/network/protocol/PacketFlow;ILnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/Packet;"
		),
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	public void ping5$sizeIncomingPackets2(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci, int i, FriendlyByteBuf friendlyByteBuf, int j, Packet<?> packet) {
		if(PingPingPingPingPing.CAPTURING) {
			PingPingPingPingPing.RECEIVED_PACKET_SIZES.put(packet, readableByteCache.get());
			readableByteCache.remove();
		}
	}
}
