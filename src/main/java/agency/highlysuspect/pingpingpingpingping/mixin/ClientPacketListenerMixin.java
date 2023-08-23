package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//TODO can this be handled somewhere else, bundle packets are weird
// lotsa packet stuff seems to happen in net.minecraft.network.protocol.BundlerInfo
// see also ClientBundleUnpacker, and the netty pipeline stage called "unbundler"
@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
	@Inject(method = "handleBundlePacket", at = @At("HEAD"))
	public void onBundle(ClientboundBundlePacket bundle, CallbackInfo ci) {
		if(!PingPingPingPingPing.CAPTURING) return;
		
		for(Packet<?> p : bundle.subPackets()) PingPingPingPingPing.recorder.record(p, PacketFlow.CLIENTBOUND, 0);
	}
}
