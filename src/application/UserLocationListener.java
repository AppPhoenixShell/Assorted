package application;

import com.firebase.geofire.GeoLocation;

public interface UserLocationListener {
	public void onLocation(GeoLocation location, String user);
}
