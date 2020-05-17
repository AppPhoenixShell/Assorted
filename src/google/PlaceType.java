package google;

public enum PlaceType {
	AIRPORT("airport"),
	CAFE("cafe");
	
	
	public final String name;
	
	PlaceType(String name) {
		this.name = name;
	}
}
