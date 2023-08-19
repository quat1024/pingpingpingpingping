package agency.highlysuspect.pingpingpingpingping;

import org.jetbrains.annotations.Nullable;

//TODO: make it composable
public interface Extractor {
	default @Nullable String ping5$name(Object self) {
		return DefaultNameTable.TABLE.get(self.getClass());
	}
	
	default void ping5$fillDetails(Object self, DetailSet details) {
	
	}
	
	static Extractor get(Object a) {
		if(a instanceof Extractor e) return e; //DONT GO implementing this on your types, not public api, shhhHH
		
		Extractor e = DefaultExtractorTable.TABLE.get(a.getClass());
		if(e != null) return e;
		
		return DefaultExtractor.INSTANCE;
	}
	
	class DefaultExtractor implements Extractor {
		public static final DefaultExtractor INSTANCE = new DefaultExtractor();
	}
}
