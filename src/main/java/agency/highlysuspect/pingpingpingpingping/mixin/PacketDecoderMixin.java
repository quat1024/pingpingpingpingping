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
	
	@Inject(method = "decode", at = @At("HEAD"))
	public void ping5$cacheReadableBytes(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci) {
		readableByteCache.set(byteBuf.readableBytes());
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
	public void pasdklasjdlkas(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci, int i, FriendlyByteBuf friendlyByteBuf, int j, Packet packet) {
		if(PingPingPingPingPing.CAPTURING) PingPingPingPingPing.PACKET_SIZES.put(packet, readableByteCache.get());
		readableByteCache.remove();
	}
}
