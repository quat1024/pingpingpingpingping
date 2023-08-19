package agency.highlysuspect.pingpingpingpingping;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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
import net.minecraft.network.protocol.Packet;
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
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class PingPingPingPingPing implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("PingPingPingPingPing");
	
	//this field is read *very* often but doesnt change a lot. hmm
	public static boolean CAPTURING = false;
	
	//awkward situation: Packet is an interface and not a concrete class, so I can't slap a "size" field on it
	//and packets do not go down the pipeline one-at-a-time in order
	public static final Cache<Packet<?>, Integer> PACKET_SIZES = CacheBuilder.newBuilder()
		.weakKeys()
		.concurrencyLevel(10)
		.expireAfterWrite(5, TimeUnit.SECONDS)
		.build();
	
	public static volatile PacketRecorder recorder = new PacketRecorder();
	
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, reg) -> {
			LiteralArgumentBuilder<FabricClientCommandSource> ppppp = ClientCommandManager.literal("pingpingpingpingping")
				.then(ClientCommandManager.literal("start").executes(src -> {
					resetRecorder();
					startCapturing();
					
					send(src, "Cleared capture buffer, started capturing packets.");
					send(src, "Finish and save report with §7/pingpingpingpingping stop§r.");
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
					if(CAPTURING) {
						PacketRecorder recorder = PingPingPingPingPing.recorder;
						
						long seconds = Duration.between(recorder.getStart(), Instant.now()).toSeconds();
						
						send(src, "Capturing packets for §7%d§r seconds.".formatted(seconds));
						send(src, "Use §7/pingpingpingpingping stop§r to finish and save report.");
						
						int recv = recorder.getReceivedCount();
						send(src, "Received §7%d§r packets (§7%s§r/s, §7%s§r)".formatted(recv, formatPerSecond(recv, seconds), formatBytes(recorder.getReceivedSize())));
						
						int sent = recorder.getSentCount();
						send(src, "Sent §7%d§r packets (§7%s§r/s, §7%s§r)".formatted(sent, formatPerSecond(sent, seconds), formatBytes(recorder.getSentSize())));
					} else {
						send(src, "Not capturing packets. Use §7/pingpingpingpingping start§r to begin.");
					}
					
					return 0;
				}));
			
			LiteralCommandNode<FabricClientCommandSource> p = dispatcher.register(ppppp);
			dispatcher.register(ClientCommandManager.literal("ping5").redirect(p));
		});
	}
	
	private static void send(CommandContext<FabricClientCommandSource> s, String msg) {
		send(s, Component.literal(msg));
	}
	
	private static void send(CommandContext<FabricClientCommandSource> s, Component msg) {
		s.getSource().sendFeedback(msg);
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
		List<String> report = new ArrayList<>();
		recorder.printReport(report::add);
		
		Path p = FabricLoader.getInstance()
			.getGameDir()
			.resolve("pingpingpingpingping")
			.resolve(NOT_FILENAME_SAFE.matcher("report-" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".txt").replaceAll(""));
		try {
			Files.createDirectories(p.getParent());
			Files.write(p, report, StandardCharsets.UTF_8);
		} catch (Exception e) {
			LOGGER.error("Failed to write report", e);
			throw new RuntimeException(e);
		}
		
		return p;
	}
	
	public static String formatPerSecond(int packets, long seconds) {
		if(seconds < 1) seconds = 1;
		return "%.3f".formatted((double) packets / seconds);
	}
	
	//proguarded
	private static final int a = 1024;
	private static final int b = 1024 * 1024;
	private static final int c = 1024 * 1024 * 1024;
	
	public static String formatBytes(double bytes) {
		if(bytes < a) {
			return ((int) bytes) + " b";
		} else if(bytes < b) {
			return "%.3f".formatted(bytes / a) + " KiB";
		} else if(bytes < c) {
			return "%.3f".formatted(bytes / b) + " MiB";
		} else {
			return "%.3f".formatted(bytes / c) + " GiB"; //god i hope not
		}
	}
	
	public static String formatBytesPerSecond(int bytes, long seconds) {
		if(seconds < 1) seconds = 1;
		return formatBytes((double) bytes / seconds);
	}
}
