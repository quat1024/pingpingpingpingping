package agency.highlysuspect.pingpingpingpingping.mixin.spec;

import agency.highlysuspect.pingpingpingpingping.PingPingPingPingPing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
	private void ping5$isInvisibleTo(Player player, CallbackInfoReturnable<Boolean> cir) {
		if(PingPingPingPingPing.HIDE_SPECTATORS && player.isSpectator()) cir.setReturnValue(true);
	}
}
