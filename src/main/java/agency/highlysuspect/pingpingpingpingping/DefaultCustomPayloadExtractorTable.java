package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

//TODO this api sucks, it's just taped together to get some tiny bit of insight into sync spam at blanketcon
public class DefaultCustomPayloadExtractorTable {
	public static final Map<ResourceLocation, Extractor> TABLE = new HashMap<>();
	
	static {
		//https://github.com/OnyxStudios/Cardinal-Components-API/blob/a7891cf786289b1ce8249ef6f26228520cb788df/cardinal-components-chunk/src/main/java/dev/onyxstudios/cca/internal/chunk/CcaChunkClientNw.java#L38
		// packet id source: https://github.com/OnyxStudios/Cardinal-Components-API/blob/a7891cf786289b1ce8249ef6f26228520cb788df/cardinal-components-chunk/src/main/java/dev/onyxstudios/cca/internal/chunk/ComponentsChunkNetworking.java#L32
		TABLE.put(new ResourceLocation("cardinal-components", "chunk_sync"), new Extractor() {
			@Override
			public void ping5$decorate(Object self, Map<String, String> data) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				
				try {
					buf.markReaderIndex();
					data.put("chunkX", Integer.toString(buf.readInt()));
					data.put("chunkZ", Integer.toString(buf.readInt()));
					data.put("componentTypeId", buf.readResourceLocation().toString());
				} catch (Exception e) {
					data.put("error", "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
		
		//https://github.com/Layers-of-Railways/Railway/blob/3d22c766429397d42af84c2cd9500041974e5859/common/src/main/java/com/railwayteam/railways/multiloader/PacketSet.java#L132
		TABLE.put(new ResourceLocation("railways", "s2c"), new Extractor() {
			@Override
			public void ping5$decorate(Object self, Map<String, String> data) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				try {
					buf.markReaderIndex();
					data.put("s2cPacketType", Integer.toString(buf.readVarInt()));
				} catch (Exception e) {
					data.put("extraction error", "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
	}
}