package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DetailSet {
	record Datum(int level, String value) {}
	private final Map<String, Datum> datums = new ConcurrentHashMap<>();
	
	public DetailSet collect(String key, int level, Object value) {
		datums.put(key, new Datum(level, value.toString()));
		return this;
	}
	
	public boolean isEmpty() {
		return datums.isEmpty();
	}
	
	public DetailSet trimTo(int maxLevel) {
		DetailSet cut = new DetailSet();
		datums.forEach((k, v) -> {
			if(v.level <= maxLevel) cut.datums.put(k, v);
		});
		return cut;
	}
	
	public String show() {
		return datums.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(e -> e.getKey() + ": " + e.getValue().value) //Great Value
			.collect(Collectors.joining(", "));
	}
	
	public int maxDetail() {
		return datums.values().stream().map(Datum::level).max(Integer::compareTo).orElse(0);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		DetailSet detailSet = (DetailSet) o;
		return datums.equals(detailSet.datums);
	}
	
	@Override
	public int hashCode() {
		return datums.hashCode();
	}
	
	//TODO: remove this?
	
	public <T> DetailSet collect(Key<T> key, int level, T value) {
		return collect(key.name(), level, key.toString(value));
	}
	
	public static Key<BlockPos> BLOCK_POS = Key.make("pos", BlockPos::toShortString);
	public static Key<BlockEntityType<?>> BLOCK_ENTITY_TYPE = Key.make("be", ExtractorUtils::blockEntityTypeName);
	public static Key<Integer> ENTITY_FROM_ID = Key.make("ent", ExtractorUtils::entityNameFromRawId);
	public static Key<Function<? super Level, ? extends Entity>> ENTITY_FROM_ENTITY_GETTER = Key.make("ent", ExtractorUtils::entityNameFromEntityGetter);
	
	//compared by identity
	public interface Key<T> {
		String name();
		String toString(T thing);
		
		static <T> Key<T> make(String name, Function<T, String> stringFunction) {
			return new Key<>() {
				@Override
				public String name() {
					return name;
				}
				
				@Override
				public String toString(T thing) {
					return stringFunction.apply(thing);
				}
			};
		}
		
		static <T> Key<T> make(String name) {
			return new Key<>() {
				@Override
				public String name() {
					return name;
				}
				
				@Override
				public String toString(T thing) {
					return thing.toString();
				}
			};
		}
	}
}
