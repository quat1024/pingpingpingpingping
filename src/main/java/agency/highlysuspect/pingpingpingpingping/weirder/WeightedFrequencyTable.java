package agency.highlysuspect.pingpingpingpingping.weirder;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class WeightedFrequencyTable<T> {
	private final ConcurrentHashMap<T, Entry> table = new ConcurrentHashMap<>();
	private int totalFrequency;
	private int totalWeight;
	
	private static class Entry {
		MutableInt frequency = new MutableInt(0);
		MutableInt weight = new MutableInt(0);
		
		static Comparator<Entry> COMPARING_BY_FREQUENCY = Comparator.comparing(Entry::getFrequency);
		static Comparator<Entry> COMPARING_BY_WEIGHT = Comparator.comparing(Entry::getWeight);
		
		void count(int w) {
			frequency.increment();
			weight.add(w);
		}
		
		int getFrequency() {
			return frequency.intValue();
		}
		
		int getWeight() {
			return weight.intValue();
		}
	}
	
	public void add(T item, int weight) {
		table.computeIfAbsent(item, __ -> new Entry()).count(weight);
		totalFrequency++;
		totalWeight += weight;
	}
	
	public Set<T> keySet() {
		return table.keySet();
	}
	
	public int totalFrequency() {
		return totalFrequency;
	}
	
	public int totalWeight() {
		return totalWeight;
	}
	
	public Stream<Map.Entry<T, Integer>> streamByFrequency() {
		return table.entrySet().stream()
			.sorted(Map.Entry.<T, Entry>comparingByValue(Entry.COMPARING_BY_FREQUENCY).reversed())
			.map(e -> Map.entry(e.getKey(), e.getValue().getFrequency())); //meh
	}
	
	public Stream<Map.Entry<T, Integer>> byWeight() {
		return table.entrySet().stream()
			.sorted(Map.Entry.<T, Entry>comparingByValue(Entry.COMPARING_BY_WEIGHT).reversed())
			.map(e -> Map.entry(e.getKey(), e.getValue().getWeight())); //meh
	}
}
