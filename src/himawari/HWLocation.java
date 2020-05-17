package himawari;


public class HWLocation {
	public double lat;
	public double lng;
	public long server_timestamp;
	
	public long localtime;
	public String local_timestamp;
	
	public HWLocation() {
		
	}
	
	public HWLocation(double lat, double lng, long time) {
		this.lat = lat;
		this.lng = lng;
		this.localtime = time;
	}
	
	
}
