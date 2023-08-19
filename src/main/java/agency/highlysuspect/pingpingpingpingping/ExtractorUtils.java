package agency.highlysuspect.pingpingpingpingping;

import agency.highlysuspect.pingpingpingpingping.mixin.MultiPlayerGameModeAccessor;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Function;

public class ExtractorUtils {
	//TODO: entity getter stuff doesn't seem to be reliable pre-JOIN
	public static Level tryToGetLevel() {
		MultiPlayerGameMode gm = Minecraft.getInstance().gameMode;
		if(gm == null) return null;
		
		ClientPacketListener pl = ((MultiPlayerGameModeAccessor) gm).ping5$connection();
		if(pl == null) return null;
		
		return pl.getLevel();
	}
	
	public static String entityNameFromEntityGetter(Function<? super Level, ? extends Entity> getter) {
		Level l = tryToGetLevel();
		if(l == null) return "<no level>";
		
		Entity e = getter.apply(tryToGetLevel());
		if(e == null) return "<no entity?>";
		
		return BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).toString();
	}
	
	public static String entityNameFromRawId(int id) {
		Level l = tryToGetLevel();
		if(l == null) return "<no level>";
		
		Entity e = l.getEntity(id);
		if(e == null) return "<no entity?>";
		
		return BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).toString();
	}
	
	public static String blockEntityTypeName(BlockEntityType<?> t) {
		ResourceLocation rl = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(t);
		if(rl == null) return "<unregistered>";
		else return rl.toString();
	}
	
	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static <T> T unifyEither(Either<T, T> yeah) {
		return yeah.left().orElseGet(() -> yeah.right().get());
	}
	
	public static ResourceLocation soundEventId(Holder<SoundEvent> e) {
		//prefer the ResourceKey location if one exists (i guess)
		return unifyEither(e.unwrap().mapLeft(ResourceKey::location).mapRight(SoundEvent::getLocation));
	}
}
