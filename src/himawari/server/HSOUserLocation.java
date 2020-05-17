package himawari.server;

public class HSOUserLocation  implements HSORUserLocation
{
	public float lat;
	public float lng;
	public long localtime;
	public long servertime;
	
	public HSOUserLocation() {}

	public float getLat() 		{return lat;}
	public float getLng() 		{return lng;}
	public long getLocaltime()	{return localtime;}
	public long getServertime() {return servertime;}
	
	public String toString() {
		return String.format("location:[%f,%f]", lat, lng);
	}
}
