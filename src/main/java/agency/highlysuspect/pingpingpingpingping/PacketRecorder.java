package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.network.protocol.Packet;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

//TODO: regular HashMap with synchronization would probably be better
//Or, like,, theres only one packet thread, right
public class PacketRecorder {
	public PacketRecorder() {
		this.start = Instant.now();
	}
	
	private final Instant start;
	private final ConcurrentHashMap<String, Data> dataByName = new ConcurrentHashMap<>();
	private final AtomicInteger recvCount = new AtomicInteger(0);
	
	public void record(Packet<?> packet) {
		recvCount.incrementAndGet();
		
		Extractor ex = Extractor.get(packet);
		
		String name = ex.ping5$name(packet);
		if(name == null) name = packet.getClass().getName();
		
		dataByName.computeIfAbsent(name, Data::new).record(packet, ex);
	}
	
	public int getRecvCount() {
		return recvCount.get();
	}
	
	public Instant getStart() {
		return start;
	}
	
	public void printReport(Consumer<String> out) {
		Instant end = Instant.now();
		Duration length = Duration.between(start, end);
		long seconds = length.toSeconds();
		
		int recvCount = this.recvCount.get();
		
		out.accept("PingPingPingPingPing packet report");
		out.accept("----------------------------------");
		out.accept("");
		out.accept("Capture start:  " + start);
		out.accept("Capture end:    " + end);
		out.accept("Capture length: " + length + " (" + seconds + " seconds)");
		out.accept("");
		out.accept("Total packets: " + recvCount);
		out.accept("Packets/sec:   " + PingPingPingPingPing.formatPacketsPerSecond(recvCount, seconds));
		out.accept("");
		out.accept("Packet breakdown");
		out.accept("----------------");
		out.accept("");
		dataByName.values().stream().sorted().forEach(d -> d.breakdown(out));
	}
	
	public static class Data implements Comparable<Data> {
		public Data(String name) {
			this.name = name;
		}
		
		private final String name;
		private int recvCount;
		private final ConcurrentHashMap<Map<String, String>, MutableInt> seenDatas = new ConcurrentHashMap<>();
		
		public void record(Object packet, Extractor ex) {
			recvCount++;
			
			Map<String, String> data = new TreeMap<>();
			ex.ping5$decorate(packet, data);
			if(!data.isEmpty()) seenDatas.computeIfAbsent(data, __ -> new MutableInt(0)).increment();
		}
		
		@Override
		public int compareTo(@NotNull PacketRecorder.Data o) {
			return Integer.compare(o.recvCount, recvCount); //reversed
		}
		
		public void breakdown(Consumer<String> out) {
			out.accept(recvCount + "x " + name);
			
			seenDatas.entrySet().stream()
				.sorted(Comparator.comparingInt((Map.Entry<?, MutableInt> e) -> e.getValue().intValue()).reversed())
				.map(e -> "\t" + e.getValue() + "x " + showMap(e.getKey()))
				.limit(20)
				.forEach(out);
			if(seenDatas.size() > 20) out.accept("\t... [" + (seenDatas.size() - 20) + " more unique types]");
		}
		
		private String showMap(Map<String, String> ah) {
			return ah.entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.collect(Collectors.joining(", "));
		}
	}
}
