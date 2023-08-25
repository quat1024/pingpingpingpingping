package agency.highlysuspect.pingpingpingpingping.mixin.spec;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
	@Inject(method = "setModelProperties", at = @At("RETURN"))
	private void ping5spec$whenSettingModelProps(AbstractClientPlayer abstractClientPlayer, CallbackInfo ci) {
		if(PingPingPingPingPing.HIDE_SPECTATORS && isSpecOrInvis(abstractClientPlayer)) {
			((PlayerRenderer) (Object) this).getModel().setAllVisible(false);
		}
	}
	
	@Inject(method = "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
	private void ping5spec$whenNametag(AbstractClientPlayer abstractClientPlayer, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		if(PingPingPingPingPing.HIDE_SPECTATORS && isSpecOrInvis(abstractClientPlayer)) {
			ci.cancel();
		}
	}
	
	private static boolean isSpecOrInvis(AbstractClientPlayer p) {
		return p != null && (p.isSpectator() || p.isInvisible());
	}
}
