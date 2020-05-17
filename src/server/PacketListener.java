package server;

public interface PacketListener {
	public void onPacket(String uuid, String packet);
}
