package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.core.BlockPos;
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
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class DefaultExtractorTable {
	public static final Map<Class<?>, Extractor> TABLE = new HashMap<>();
	
	static {
		//custom payload
		TABLE.put(ClientboundCustomPayloadPacket.class, new Extractor() {
			@Override
			public @Nullable String ping5$name(Object self) {
				return "ClientboundCustomPayloadPacket " + ((ClientboundCustomPayloadPacket) self).getIdentifier();
			}
			
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				Extractor next = DefaultCustomPayloadExtractorTable.INCOMING.get(((ClientboundCustomPayloadPacket) self).getIdentifier());
				if(next != null) next.ping5$fillDetails(self, details);
			}
		});
		
		TABLE.put(ServerboundCustomPayloadPacket.class, new Extractor() {
			@Override
			public @Nullable String ping5$name(Object self) {
				return "ServerboundCustomPayloadPacket " + ((ServerboundCustomPayloadPacket) self).getIdentifier();
			}
			
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				//TODO
				Extractor.super.ping5$fillDetails(self, details);
			}
		});
		
		blockpos(ClientboundBlockUpdatePacket.class, 1, ClientboundBlockUpdatePacket::getPos);
		blockpos(ClientboundBlockEventPacket.class, 1, ClientboundBlockEventPacket::getPos);
		blockpos(ClientboundBlockDestructionPacket.class, 1, ClientboundBlockDestructionPacket::getPos);
		TABLE.put(ClientboundBlockEntityDataPacket.class, new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				ClientboundBlockEntityDataPacket s = (ClientboundBlockEntityDataPacket) self;
				details//.collect(DetailSet.BLOCK_POS, 2, s.getPos()) //TODO
					.collect(DetailSet.BLOCK_ENTITY_TYPE, 1, s.getType());
			}
		});
		
		//entity stuff
		entityGetter(ClientboundMoveEntityPacket.Pos.class   , 1, p -> p::getEntity);
		entityGetter(ClientboundMoveEntityPacket.PosRot.class, 1, p -> p::getEntity);
		entityGetter(ClientboundMoveEntityPacket.Rot.class   , 1, p -> p::getEntity);
		entityGetter(ClientboundEntityEventPacket.class      , 1, p -> p::getEntity);
		entityGetter(ClientboundRotateHeadPacket.class       , 1, p -> p::getEntity);
		entityId(ClientboundTeleportEntityPacket.class , 1, ClientboundTeleportEntityPacket::getId);
		entityId(ClientboundSetEntityDataPacket.class  , 1, ClientboundSetEntityDataPacket::id);
		entityId(ClientboundSetEntityMotionPacket.class, 1, ClientboundSetEntityMotionPacket::getId);
		
		//misc
		TABLE.put(ClientboundSoundPacket.class, new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				ClientboundSoundPacket s = (ClientboundSoundPacket) self;
				
				details.collect("type", 1, ExtractorUtils.soundEventId(s.getSound()))
					.collect("x", 2, s.getX())
					.collect("y", 2, s.getY())
					.collect("z", 2, s.getZ());
			}
		});
	}
	
	public static <T extends Packet<?>> void blockpos(Class<T> classs, int level, Function<T, BlockPos> posGetter) {
		TABLE.put(classs, new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				details.collect(DetailSet.BLOCK_POS, level, posGetter.apply((T) self));
			}
		});
	}
	
	public static <T extends Packet<?>> void entityGetter(Class<T> classs, int level, Function<T, Function<? super Level, ? extends Entity>> getterGetter) {
		TABLE.put(classs, new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				details.collect(DetailSet.ENTITY_FROM_ENTITY_GETTER, level, getterGetter.apply((T) self));
			}
		});
	}
	
	public static <T extends Packet<?>> void entityId(Class<T> classs, int level, ToIntFunction<T> entityId) {
		TABLE.put(classs, new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				details.collect(DetailSet.ENTITY_FROM_ID, level, entityId.applyAsInt((T) self));
			}
		});
	}
}
