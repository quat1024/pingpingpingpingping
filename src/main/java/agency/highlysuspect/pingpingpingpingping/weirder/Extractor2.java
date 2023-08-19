package agency.highlysuspect.pingpingpingpingping.weirder;

import agency.highlysuspect.pingpingpingpingping.DetailSet;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//todo NOT USED
public interface Extractor2 {
	void addInfo(IncomingPacket inc, DetailSet details, Consumer<IncomingPacket> subpacket);
	
	static Map<IncomingPacket, DetailSet> runPipeline(List<Extractor2> extractors, IncomingPacket packet) {
		Map<IncomingPacket, DetailSet> result = new IdentityHashMap<>();
		
		Deque<IncomingPacket> queue = new ArrayDeque<>(4);
		queue.addLast(packet);
		
		while(!queue.isEmpty()) {
			IncomingPacket p = queue.removeFirst();
			
			DetailSet details = new DetailSet();
			extractors.forEach(ex -> ex.addInfo(p, details, queue::addLast));
			result.put(packet, details);
		}
		
		return result;
	}
}
