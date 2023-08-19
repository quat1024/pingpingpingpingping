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

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
	@Inject(method = "handleBundlePacket", at = @At("HEAD"))
	public void onBundle(ClientboundBundlePacket bundle, CallbackInfo ci) {
		if(!PingPingPingPingPing.CAPTURING) return;
		
		for(Packet<?> p : bundle.subPackets()) PingPingPingPingPing.recorder.record(p, PacketFlow.CLIENTBOUND);
	}
}
