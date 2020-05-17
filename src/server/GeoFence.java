package server;

import com.firebase.geofire.util.Geo;

import himawari.HWLocation;

public class GeoFence {
	
	public static final int ENTERED = 0;
	public static final int REMAIN = 1;
	public static final int LEAVE = 2;
	public static final int OUTSIDE = 3;
	
	
	private double lat,lng,radius;
	private boolean triggered;
	private String message;
	
	public GeoFence(double lat, double lng, double radius) {
		this(lat, lng, radius, "you entered some geoFence");
	}
	
	public GeoFence(double lat, double lng, double radius, String message) {
		this.lat = lat;
		this.lng = lng;
		this.radius = radius;
		this.triggered = false;
		this.message = message;
	}

	public int push(HWLocation loc) {
			
		double dist = Geo.distance(loc.lat, loc.lng, lat, lng);
		boolean localEntered = dist < radius;
		
		if(!triggered && localEntered) {
			triggered = true;
			return ENTERED;
		}
		else if(triggered && !localEntered) {
			triggered = false;
			return LEAVE;
		}
		else if(triggered && localEntered) {
			return REMAIN;
		}
		return OUTSIDE;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
}
