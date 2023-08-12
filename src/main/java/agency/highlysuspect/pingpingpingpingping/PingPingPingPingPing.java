package agency.highlysuspect.pingpingpingpingping;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingPingPingPingPing implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("PingPingPingPingPing");
	
	//this field is read *very* often but doesnt change a lot. hmm
	public static boolean CAPTURING = true;
	
	public static volatile PacketRecorder recorder = new PacketRecorder();
	
	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			PacketRecorder r = recorder;
			LOGGER.info("Packets received before the JOIN event was fired:");
			r.print(LOGGER::info);
			
			recorder = new PacketRecorder();
		});
		
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			PacketRecorder r = recorder;
			LOGGER.info("Packets received this session:");
			r.print(LOGGER::info);
			
			recorder = new PacketRecorder();
		});
	}
}
