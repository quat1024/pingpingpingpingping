package agency.highlysuspect.pingpingpingpingping.weirder;

import agency.highlysuspect.pingpingpingpingping.DefaultNameTable;
import agency.highlysuspect.pingpingpingpingping.DetailSet;
import agency.highlysuspect.pingpingpingpingping.ExtractorUtils;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//todo NOT USED
public class DefaultExtractor2s {
	public static final List<Extractor2> CHAIN = new ArrayList<>();
	
	static {
		//naming extractor
		CHAIN.add((inc, details, subpacket) -> {
			Object packet = inc.packet();
			
			String name = DefaultNameTable.TABLE.get(packet.getClass());
			if(name == null) name = packet.getClass().getName();
			
			if(packet instanceof ClientboundCustomPayloadPacket p) name += " " + p.getIdentifier();
			
			details.collect(DetailSet.NAME, 0, name);
		});
		
		//basic hardcoded stuff
		CHAIN.add((inc, details, subpacket) -> {
			Object packet = inc.packet();
			
			BlockPos pos = null;
			if(packet instanceof ClientboundBlockUpdatePacket p) pos = p.getPos();
			else if(packet instanceof ClientboundBlockEventPacket p) pos = p.getPos();
			else if(packet instanceof ClientboundBlockDestructionPacket p) pos = p.getPos();
			else if(packet instanceof ClientboundBlockEntityDataPacket p) pos = p.getPos();
			if(pos != null) details.collect(DetailSet.BLOCK_POS, 1, pos);
			
			BlockEntityType<?> be = null;
			if(packet instanceof ClientboundBlockEntityDataPacket p) be = p.getType();
			if(be != null) details.collect(DetailSet.BLOCK_ENTITY_TYPE, 1, be);
			
			Function<? super Level, ? extends Entity> entityGetter = null;
			int entityId = -1;
			if(packet instanceof ClientboundMoveEntityPacket p) entityGetter = p::getEntity;
			else if(packet instanceof ClientboundEntityEventPacket p) entityGetter = p::getEntity;
			else if(packet instanceof ClientboundRotateHeadPacket p) entityGetter = p::getEntity;
			else if(packet instanceof ClientboundTeleportEntityPacket p) entityId = p.getId();
			else if(packet instanceof ClientboundSetEntityDataPacket p) entityId = p.id();
			else if(packet instanceof ClientboundSetEntityMotionPacket p) entityId = p.getId();
			if(entityGetter != null) details.collect(DetailSet.ENTITY_FROM_ENTITY_GETTER, 1, entityGetter);
			if(entityId != -1) details.collect(DetailSet.ENTITY_FROM_ID, 1, entityId);
			
			if(packet instanceof ClientboundSoundPacket s) {
				details.collect("type", 1, ExtractorUtils.soundEventId(s.getSound()))
					.collect("x", 2, s.getX())
					.collect("y", 2, s.getY())
					.collect("z", 2, s.getZ());
			}
		});
	}
}
