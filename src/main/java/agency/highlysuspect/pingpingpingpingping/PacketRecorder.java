package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

//TODO: regular HashMap with synchronization would probably be better
//Or, like,, theres only one packet thread, right
public class PacketRecorder {
	public PacketRecorder() {
		this.start = Instant.now();
	}
	
	private final Instant start;
	private final ConcurrentHashMap<String, Data> receivedData = new ConcurrentHashMap<>();
	private final AtomicInteger receivedCount = new AtomicInteger(0);
	
	private final ConcurrentHashMap<String, Data> sentData = new ConcurrentHashMap<>();
	private final AtomicInteger sentCount = new AtomicInteger(0);
	
	public void record(Packet<?> packet, PacketFlow direction) {
		if(direction == PacketFlow.CLIENTBOUND) receivedCount.incrementAndGet();
		else sentCount.incrementAndGet();
		
		Extractor ex = Extractor.get(packet);
		
		String name = ex.ping5$name(packet);
		if(name == null) name = packet.getClass().getName();
		
		(direction == PacketFlow.CLIENTBOUND ? receivedData : sentData).computeIfAbsent(name, Data::new).record(packet, ex);
	}
	
	public int getReceivedCount() {
		return receivedCount.get();
	}
	
	public int getSentCount() {
		return sentCount.get();
	}
	
	public Instant getStart() {
		return start;
	}
	
	public void printReport(Consumer<String> out) {
		Instant end = Instant.now();
		Duration length = Duration.between(start, end);
		long seconds = length.toSeconds();
		
		int recvCount = this.receivedCount.get();
		int sentCount = this.sentCount.get();
		
		out.accept("PingPingPingPingPing packet report");
		out.accept("----------------------------------");
		out.accept("");
		out.accept("Capture start:  " + start);
		out.accept("Capture end:    " + end);
		out.accept("Capture length: " + length + " (" + seconds + " seconds)");
		out.accept("");
		out.accept("Total received packets: " + recvCount);
		out.accept("Received packets/sec:   " + PingPingPingPingPing.formatPacketsPerSecond(recvCount, seconds));
		
		int maxDetail = receivedData.values().stream().map(Data::maxDetail).max(Integer::compareTo).orElse(0);
		for(int det = 0; det <= maxDetail; det++) {
			out.accept("");
			out.accept("Received packet breakdown at detail level " + det);
			out.accept("-------------------------------------------");
			out.accept("");
			int ugh = det;
			receivedData.values().stream().sorted().forEach(d -> d.breakdown(out, ugh));
		}
		
		out.accept("");
		out.accept("Total sent packets: " + sentCount);
		out.accept("Sent packets/sec:   " + PingPingPingPingPing.formatPacketsPerSecond(sentCount, seconds));
		
		maxDetail = sentData.values().stream().map(Data::maxDetail).max(Integer::compareTo).orElse(0);
		for(int det = 0; det <= maxDetail; det++) {
			out.accept("");
			out.accept("Sent packet breakdown at detail level " + det);
			out.accept("---------------------------------------");
			out.accept("");
			int ugh = det;
			sentData.values().stream().sorted().forEach(d -> d.breakdown(out, ugh));
		}
	}
	
	public static class Data implements Comparable<Data> {
		public Data(String name) {
			this.name = name;
		}
		
		private final String name;
		
		private final AtomicInteger count = new AtomicInteger(0);
		private final ConcurrentHashMap<DetailSet, AtomicInteger> seenDetails = new ConcurrentHashMap<>();
		
		public void record(Object packet, Extractor ex) {
			count.incrementAndGet();
			
			DetailSet details = new DetailSet();
			ex.ping5$fillDetails(packet, details);
			seenDetails.computeIfAbsent(details, __ -> new AtomicInteger(0)).incrementAndGet();
		}
		
		@Override
		public int compareTo(@NotNull PacketRecorder.Data o) {
			return Integer.compare(o.count.getAcquire(), count.getAcquire()); //reversed
		}
		
		public void breakdown(Consumer<String> out, int maxLevel) {
			out.accept(count + "x " + name);
			if(maxLevel <= 0) return;
			
			//trim details to the desired max level, accumulating frequencies
			Map<DetailSet, MutableInt> trimmedDetailFrequencies = new HashMap<>();
			for(Map.Entry<DetailSet, AtomicInteger> entry : seenDetails.entrySet()) {
				DetailSet trim = entry.getKey().trimTo(maxLevel);
				int frequency = entry.getValue().get();
				trimmedDetailFrequencies.computeIfAbsent(trim, __ -> new MutableInt(0)).add(frequency);
			}
			
			//remove any blanks
			trimmedDetailFrequencies.keySet().removeIf(DetailSet::isEmpty);
			
			//show the first 20 trimmed details
			trimmedDetailFrequencies.entrySet().stream()
				.sorted(Map.Entry.<DetailSet, MutableInt>comparingByValue().reversed())
				.map(e -> "\t" + e.getValue() + "x " + e.getKey().show())
				.limit(20)
				.forEach(out);
			
			//if there were more details, tell how many
			if(trimmedDetailFrequencies.size() > 20) out.accept("\t... [" + (trimmedDetailFrequencies.size() - 20) + " more combinations]");
		}
		
		public int maxDetail() {
			return seenDetails.keySet().stream().map(DetailSet::maxDetail).max(Integer::compareTo).orElse(0);
		}
	}
}
