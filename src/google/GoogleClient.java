package google;

import easyhttp.EasyHttp;
import easyhttp.JsonListener;
import os.Looper;

public class GoogleClient
{
	
    private static final String PLACE_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json?key=%s&placeid=%s";
    private static final String ADDRESS = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
	
	
	private static final String KEY = "AIzaSyBA4luwo-79DQR3sBrH5KCgTsyPYw8XSv4";
	private static GoogleClient instance;
	
	private EasyHttp http;
	
	private final String apiKey;
	
	private GoogleClient(String key) {
		this.apiKey = key;
		http = new EasyHttp(Looper.getMainLooper());
	}
	
	public void request(GoogleRequest request, JsonListener callback) {
		if(request.pending)
			throw new RuntimeException("cannot request an already pending request");
		request.apiKey = apiKey;
		request.callback = callback;
		request.pending= true;
		
		http.request(request);
		
		
	}
	
	public static GoogleClient get() {
		if(instance == null)
			instance = new GoogleClient(KEY);
		return instance;
	}

	public String getApiKey() {
		return apiKey;
	}
}
