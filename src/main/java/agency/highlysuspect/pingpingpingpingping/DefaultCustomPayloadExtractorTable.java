package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

//TODO this api is SHIT, it's just taped together to get some tiny bit of insight into sync spam at blanketcon
public class DefaultCustomPayloadExtractorTable {
	public static final Map<ResourceLocation, Extractor> TABLE = new HashMap<>();
	
	static {
		//https://github.com/OnyxStudios/Cardinal-Components-API/blob/a7891cf786289b1ce8249ef6f26228520cb788df/cardinal-components-chunk/src/main/java/dev/onyxstudios/cca/internal/chunk/CcaChunkClientNw.java#L38
		// packet id source: https://github.com/OnyxStudios/Cardinal-Components-API/blob/a7891cf786289b1ce8249ef6f26228520cb788df/cardinal-components-chunk/src/main/java/dev/onyxstudios/cca/internal/chunk/ComponentsChunkNetworking.java#L32
		TABLE.put(new ResourceLocation("cardinal-components", "chunk_sync"), new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				
				try {
					buf.markReaderIndex();
					details.collect("chunkX", 2, buf.readInt())
						.collect("chunkZ", 2, buf.readInt())
						.collect("componentTypeId", 1, buf.readResourceLocation());
				} catch (Exception e) {
					details.collect("extraction error", 1, "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
		
		TABLE.put(new ResourceLocation("cardinal-components", "entity_sync"), new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				try {
					buf.markReaderIndex();
					details.collect(DetailSet.ENTITY_FROM_ID, 2, buf.readInt())
							.collect("componentTypeId", 1, buf.readResourceLocation());
				} catch (Exception e) {
					details.collect("extraction error", 1, "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
		
		//Lib network stack
		//LEAST confusing alexiil mod. Uhhhhhh i think packets end up being received here
		//https://github.com/AlexIIL/LibNetworkStack/blob/0.10.x-1.20.x/src/main/java/alexiil/mc/lib/net/InternalMsgUtil.java#L156
		//And readVarUnsignedInt is fortunately just vanilla readVarInt
		//https://github.com/AlexIIL/LibNetworkStack/blob/0.10.x-1.20.x/src/main/java/alexiil/mc/lib/net/NetByteBuf.java#L546
		TABLE.put(new ResourceLocation("libnetworkstack", "data"), new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				try {
					buf.markReaderIndex();
					details.collect("lnsType", 1, buf.readVarInt());
				} catch (Exception e) {
					details.collect("extraction error", 1, "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
		
		//I'd like to debug the moonlight:1 packet but i cant see where its actually registered
		//I think moonlight uses unique Identifiers instead of multiplexing a single channel, which, fair enough, they're free on fabric
		
		//https://github.com/Layers-of-Railways/Railway/blob/3d22c766429397d42af84c2cd9500041974e5859/common/src/main/java/com/railwayteam/railways/multiloader/PacketSet.java#L132
		TABLE.put(new ResourceLocation("railways", "s2c"), new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				try {
					buf.markReaderIndex();
					details.collect("s2cPacketType", 1, buf.readVarInt());
				} catch (Exception e) {
					details.collect("extraction error", 1, "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
		
		//https://github.com/cc-tweaked/CC-Tweaked/blob/mc-1.19.x/projects/common/src/main/java/dan200/computercraft/shared/network/NetworkMessages.java#L23
		//https://github.com/cc-tweaked/CC-Tweaked/blob/mc-1.19.x/projects/fabric/src/main/java/dan200/computercraft/shared/platform/NetworkHandler.java#L75
		TABLE.put(new ResourceLocation("computercraft", "main"), new Extractor() {
			@Override
			public void ping5$fillDetails(Object self, DetailSet details) {
				FriendlyByteBuf buf = ((ClientboundCustomPayloadPacket) self).getData();
				try {
					buf.markReaderIndex();
					details.collect("type", 1, buf.readByte());
				} catch (Exception e) {
					details.collect("extraction error", 1, "very yes");
				} finally {
					buf.resetReaderIndex();
				}
			}
		});
	}
}
