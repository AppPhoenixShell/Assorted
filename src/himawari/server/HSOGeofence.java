package himawari.server;

import com.firebase.geofire.util.Geo;

import himawari.HWLocation;

public class HSOGeofence implements HSORGeofence 
{
	
	public static final int ENTERED = 0;
	public static final int REMAIN = 1;
	public static final int LEAVE = 2;
	public static final int OUTSIDE = 3;
	
	
	private String title;
	private float lat,lng,radius;
	private boolean triggered;
	private String entermessage, exitmessage;
	
	public HSOGeofence() {
		
	}
	
	public HSOGeofence(float lat, float lng, float radius) {
		this(lat, lng, radius, "you entered some geofence", "you have exited some geofence");
	}
	
	public HSOGeofence(float lat, float lng, float radius, String entermessage, String exitmessage) {
		this.lat = lat;
		this.lng = lng;
		this.radius = radius;
		this.triggered = false;
		this.entermessage = entermessage;
		this.exitmessage = exitmessage;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int push(HSORUserLocation loc) {
			
		double dist = Geo.distance(loc.getLat(), loc.getLng(), lat, lng);
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
	
	public float getLat() 		{return lat;}
	public float getLng() 		{return lng;}
	public String getEntermessage() 	{return entermessage;}
	public String getExitmessage() 	{return exitmessage;}
	public float getRadius()  	{return radius;}
	
	@Override
	public String toString() {
		return String.format("geofence:{%f,%f-%.1f %s|%s}", lat,lng, radius, entermessage, exitmessage);
	}
}
