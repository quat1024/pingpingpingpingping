package agency.highlysuspect.pingpingpingpingping;

import agency.highlysuspect.pingpingpingpingping.mixin.MultiPlayerGameModeAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DefaultExtractorTable {
	public static final Map<Class<?>, Extractor> TABLE = new HashMap<>();
	
	static {
		//custom payload
		TABLE.put(ClientboundCustomPayloadPacket.class, new Extractor() {
			@Override
			public @Nullable String ping5$name(Object self) {
				return "ClientboundCustomPayloadPacket " + ((ClientboundCustomPayloadPacket) self).getIdentifier();
			}
		});
		
		//block updates
		mkDecorate(ClientboundBlockUpdatePacket.class, (p, data) -> data.put("pos", p.getPos().toShortString()));
		mkDecorate(ClientboundBlockEventPacket.class, (p, data) -> data.put("pos", p.getPos().toShortString()));
		mkDecorate(ClientboundBlockEntityDataPacket.class, (p, data) -> {
			data.put("pos", p.getPos().toShortString());
			data.put("be", type(p.getType()));
		});
		mkDecorate(ClientboundBlockDestructionPacket.class, (p, data) -> data.put("pos", p.getPos().toShortString()));
		
		//entity stuff
		//TODO: accessors to get the raw entity ID
		mkDecorate(ClientboundMoveEntityPacket.Pos.class   , (p, data) -> data.put("type", entityId(p::getEntity)));
		mkDecorate(ClientboundMoveEntityPacket.PosRot.class, (p, data) -> data.put("type", entityId(p::getEntity)));
		mkDecorate(ClientboundMoveEntityPacket.Rot.class   , (p, data) -> data.put("type", entityId(p::getEntity)));
		mkDecorate(ClientboundEntityEventPacket.class      , (p, data) -> data.put("type", entityId(p::getEntity)));
		mkDecorate(ClientboundRotateHeadPacket.class       , (p, data) -> data.put("type", entityId(p::getEntity)));
		mkDecorate(ClientboundTeleportEntityPacket.class   , (p, data) -> data.put("type", entityId2(p.getId())));
		mkDecorate(ClientboundSetEntityDataPacket.class    , (p, data) -> data.put("type", entityId2(p.id())));
		
		//misc
		mkDecorate(ClientboundSoundPacket.class, (p, data) -> {
			data.put("x", Double.toString(p.getX()));
			data.put("y", Double.toString(p.getY()));
			data.put("z", Double.toString(p.getZ()));
		});
	}
	
	private static <T extends Packet<?>> void mkDecorate(Class<T> classs, BiConsumer<T, Map<String, String>> decorator) {
		TABLE.put(classs, new Extractor() {
			@Override
			public void ping5$decorate(Object self, Map<String, String> data) {
				decorator.accept((T) self, data);
			}
		});
	}
	
	//TODO: doesn't seem to be reliable pre-JOIN
	private static Level tryToGetLevel() {
		MultiPlayerGameMode gm = Minecraft.getInstance().gameMode;
		if(gm == null) return null;
		
		ClientPacketListener pl = ((MultiPlayerGameModeAccessor) gm).ping5$connection();
		if(pl == null) return null;
		
		return pl.getLevel();
	}
	
	private static String entityId(Function<Level, Entity> getter) {
		Level l = tryToGetLevel();
		if(l == null) return "<no level>";
		
		Entity e = getter.apply(tryToGetLevel());
		if(e == null) return "<no entity?>";
		
		return BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).toString();
	}
	
	private static String entityId2(int id) {
		Level l = tryToGetLevel();
		if(l == null) return "<no level>";
		
		Entity e = l.getEntity(id);
		if(e == null) return "<no entity?>";
		
		return BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).toString();
	}
	
	private static String type(BlockEntityType<?> t) {
		ResourceLocation rl = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(t);
		if(rl == null) return "<unregistered>";
		else return rl.toString();
	}
}
