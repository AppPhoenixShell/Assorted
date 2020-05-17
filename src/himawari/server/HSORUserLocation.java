package himawari.server;

public interface HSORUserLocation {
	public float getLat();
	public float getLng();
	public long getLocaltime();
	public long getServertime();
}
