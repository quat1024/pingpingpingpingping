package agency.highlysuspect.pingpingpingpingping;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PingPingPingPingPing implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("PingPingPingPingPing");
	
	//this field is read *very* often but doesnt change a lot. hmm
	public static boolean CAPTURING = false;
	
	public static volatile PacketRecorder recorder = new PacketRecorder();
	
	private static void send(CommandContext<FabricClientCommandSource> s, String msg) {
		send(s, Component.literal(msg));
	}
	
	private static void send(CommandContext<FabricClientCommandSource> s, Component msg) {
		s.getSource().sendFeedback(msg);
	}
	
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, reg) -> {
			LiteralArgumentBuilder<FabricClientCommandSource> ppppp = ClientCommandManager.literal("pingpingpingpingping")
				.then(ClientCommandManager.literal("start").executes(src -> {
					resetRecorder();
					startCapturing();
					
					send(src, "Cleared capture buffer, started capturing packets.");
					send(src, "Finish and save report with \u00a77/pingpingpingpingping stop\u00a7r.");
					return 0;
				}))
				.then(ClientCommandManager.literal("stop").executes(src -> {
					stopCapturing();
					send(src, "Saving report...");
					
					Path p = saveReport().toAbsolutePath();
					
					send(src, Component.literal("Report saved to ")
						.append(Component.literal(p.toString()).withStyle(s -> s.withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, p.toString())))));
					
					return 0;
				}))
				.then(ClientCommandManager.literal("info").executes(src -> {
					send(src, CAPTURING ?
						"Capturing packets. Use \u00a77/pingpingpingpingping stop\u00a7r to finish and save report" :
						"Not capturing packets. Use \u00a77/pingpingpingpingping start\u00a7r to begin.");
					if(CAPTURING && recorder != null) {
						int packets = recorder.getRecvCount();
						long seconds = Duration.between(recorder.getStart(), Instant.now()).toSeconds();
						
						send(src, "Captured \u00a77" + packets + "\u00a7r packets over \u00a77" + seconds + "\u00a7r seconds.");
						send(src, "That's \u00a77" + formatPacketsPerSecond(packets, seconds) + "\u00a7r packets/sec.");
					}
					return 0;
				}));
			
			LiteralCommandNode<FabricClientCommandSource> p = dispatcher.register(ppppp);
			dispatcher.register(ClientCommandManager.literal("ping5").redirect(p));
		});
	}
	
	public void startCapturing() {
		CAPTURING = true;
	}
	
	public void stopCapturing() {
		CAPTURING = false;
	}
	
	public void resetRecorder() {
		recorder = new PacketRecorder();
	}
	
	private static final Pattern NOT_FILENAME_SAFE = Pattern.compile("[^a-zA-Z0-9.-]");
	
	public Path saveReport() {
		
		List<String> out = new ArrayList<>();
		recorder.printReport(out::add);
		
		Path p = FabricLoader.getInstance()
			.getGameDir()
			.resolve("pingpingpingpingping")
			.resolve(NOT_FILENAME_SAFE.matcher("report-" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".txt").replaceAll(""));
		try {
			Files.createDirectories(p.getParent());
			Files.write(p, out, StandardCharsets.UTF_8);
		} catch (Exception e) {
			LOGGER.error("Failed to write report", e);
			throw new RuntimeException(e);
		}
		
		return p;
	}
	
	public static String formatPacketsPerSecond(int packets, long seconds) {
		if(seconds < 1) seconds = 1;
		return "%.3f".formatted((double) packets / seconds);
	}
}
