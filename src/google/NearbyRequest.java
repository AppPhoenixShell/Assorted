package google;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NearbyRequest extends GoogleRequest{

	
	
	private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=%s&location=%f,%f&radius=%f";
	
	private double lat,lng,radius;
	private List<PlaceType> types = new ArrayList<>(4);
	private boolean opennow;
	
	public NearbyRequest(int what) {
		super(what);
	}
	
	private String getTypeString() {
		StringBuilder build = new StringBuilder("");
		if(types.size() > 0) {
			build.append("&type=");
			build.append(types.get(0).name);
		}
		//
		for(int i=1; i < types.size(); i++) {
			build.append("|");
			build.append(types.get(i).name);
		}
		return build.toString();
			
	}
	
	public void setLocation(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	public void setOpenNow(boolean open) {
		this.opennow = open;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void addType(PlaceType type) {
		types.add(type);
	}

	@Override
	public String getUrl() {
		String base = String.format(BASE_URL, apiKey, lat, lng, radius);
		StringBuilder build = new StringBuilder(base);
		
		build.append(getTypeString());
		if(opennow)
			build.append("&opennow");
		
		return build.toString();
	}
}
