package himawari.server;

public interface HSORGeofence {
	public float getLat();
	public float getLng();
	public String getEntermessage();
	public String getExitmessage();
	public String getTitle();
	public int push(HSORUserLocation user);
}
