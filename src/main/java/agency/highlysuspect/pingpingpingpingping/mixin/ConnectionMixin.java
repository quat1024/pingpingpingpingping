package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.ConnectionModifications;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ConnectionMixin {
	@Inject(method = "channelActive", at = @At("RETURN"))
	private void ping5$onChannelActive(ChannelHandlerContext channelHandlerContext, CallbackInfo ci) {
		ConnectionModifications.modify((Connection) (Object) this, channelHandlerContext);
	}
}
