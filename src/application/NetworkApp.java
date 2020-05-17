package application;

import org.json.simple.JSONObject;

import com.firebase.geofire.GeoLocation;
import com.google.gson.JsonObject;

import apprunner.WeatherRequest;
import easyhttp.EasyHttp;
import easyhttp.HttpRequest;
import easyhttp.JsonListener;
import easyhttp.StringRequest;
import os.SmallApp;

public class NetworkApp extends SmallApp<Void, Void>{

	//private HttpRequest weatherRequest = new StringRequest("");
	
	private static final int WHAT_WEATHERRESULT = 1;
	private static final int WHAT_PLACERESULT= 2;
	
	private WeatherRequest weatherRequest;
	
	private EasyHttp httpClient;



	
	private UserLocationListener userLocation = new UserLocationListener() {

		@Override
		public void onLocation(GeoLocation location, String user) {
			weatherRequest.setLatLng(0, 0);
			//HttpRequest<?> req = new StringRequest(weatherRequest.getUrl());
			
			//httpClient.requestJson(WHAT_WEATHERRESULT, req, callback);
			
			
		}
		
	};
	
	private JsonListener jsonListener = new JsonListener() {

		@Override
		public void onResult(int requestCode, JSONObject result) {
			switch(requestCode) {
			case WHAT_PLACERESULT:handlePlaceResult(result);break;
			case WHAT_WEATHERRESULT:handleWeatherResult(result);break;
			}
		}

		
	};
	
	
	
	private void handlePlaceResult(JSONObject object) {
		//TODO something
	}
	private void handleWeatherResult(JSONObject object) {
		//TODO something
	}
	
	
	@Override
	public String getId() {
		return "id.networkapp";
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
	}

}
