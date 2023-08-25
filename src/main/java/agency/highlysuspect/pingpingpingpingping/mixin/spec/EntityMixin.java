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
	@Inject(method = "isInvisibleTo", at = @At("RETURN"), cancellable = true)
	private void ping5$isInvisibleTo(Player player, CallbackInfoReturnable<Boolean> cir) {
		if(PingPingPingPingPing.HIDE_SPECTATORS && player.isSpectator()) {
			//isInvisibleTo is called in two places in LivingEntityRenderer, both times
			//passing Minecraft.getInstance().player as the argument.
			//the first is in LivingEntityRenderer#render - if the entity isInvisible but
			//this method returns *false*, the semitransparent rendering mode will be used.
			//I would like to avoid that so i will return 'true'. This is how things like
			//invisible armor stands get rendered in spectator mode (by default this method
			//always returns 'false' for spectating players).
			//The second place is in shouldShowName; returning 'true' will hide the nametag.
			
			//If I was smart I'd modify those calls to do different things. Im not smart tho
			//and the mixins look annoying. So i return true for all non-spectator players and
			//another mixin takes care of removing the models of spectator-mode players
			
			Entity other = (Entity) (Object) this;
			if(other instanceof Player) {
				if(other.isSpectator() || other.isInvisible()) cir.setReturnValue(true);
			} else {
				cir.setReturnValue(true);
			}
		}
	}
}
