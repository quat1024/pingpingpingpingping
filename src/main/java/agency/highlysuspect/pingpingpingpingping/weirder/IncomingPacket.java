package agency.highlysuspect.pingpingpingpingping.weirder;

//todo NOT USED
public record IncomingPacket(Object packet, int size) {
	boolean isSynthetic() {
		return size == 0;
	}
}
