package agency.highlysuspect.pingpingpingpingping.mixin;

import agency.highlysuspect.pingpingpingpingping.DetailSet;
import agency.highlysuspect.pingpingpingpingping.Extractor;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientboundSectionBlocksUpdatePacket.class)
public class ClientboundSectionBlocksUpdatePacketMixin implements Extractor {
	@Shadow @Final private SectionPos sectionPos;
	
	@Override
	public void ping5$fillDetails(Object self, DetailSet details) {
		details.collect("sectionPos", 1, sectionPos.toShortString());
	}
}
