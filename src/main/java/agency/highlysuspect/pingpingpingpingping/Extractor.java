package agency.highlysuspect.pingpingpingpingping;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

//TODO: make it composable
public interface Extractor {
	default @Nullable String ping5$name(Object self) {
		return DefaultNameTable.TABLE.get(self.getClass());
	}
	
	default void ping5$decorate(Object self, Map<String, String> data) {
	
	}
	
	//todo; highlight position in-world or something
	default @Nullable Vec3 ping5$extractPos(Object self) {
		return null;
	}
	
	static Extractor get(Object a) {
		if(a instanceof Extractor e) return e; //DONT GO implementing this on your types, not public api, shhhHH
		
		Extractor e = DefaultExtractorTable.TABLE.get(a.getClass());
		if(e != null) return e;
		
		return DefaultExtractor.INSTANCE;
	}
	
	static class DefaultExtractor implements Extractor {
		public static final DefaultExtractor INSTANCE = new DefaultExtractor();
	}
}
