package apprunner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.firebase.geofire.GeoLocation;

import easyhttp.URLRequest;
import util.DataContainer;

public class WeatherRequest extends URLRequest<DataContainer<WeatherResult>> {

	private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=8350e08e4f529364bbaa7f0795c41ad5";
	
	private double lat;
	private double lng;
	
	private Callback callback;
	
	
	public WeatherRequest(int what, WeatherRequest.Callback listener) {
		super(what);
		this.callback = listener;
	}
	
	public void setCallback(WeatherRequest.Callback listener) {
		this.callback = listener;
	}
	
	public void setLatLng(double lat, double lng) {
		this.lat= lat;
		this.lng = lng;
	}

	@Override
	public String getUrl() {
		return String.format(BASE_URL, lat,lng);
	}

	@Override
	protected DataContainer<WeatherResult> convertResult(String result) {
		JSONParser p = new JSONParser();
		
		WeatherResult weather =new WeatherResult();
		weather.raw = result;
		DataContainer<WeatherResult> data = new DataContainer<>(weather);
		
		try {
			JSONObject raw = (JSONObject)p.parse(result);
			
		} catch (ParseException e) {
			data.flagFault("ParseException");
			e.printStackTrace();
		}
		return data;	
	}
	
	public static interface Callback{
		public void onWeatherResult(WeatherRequest owner, WeatherResult result);
	}

	@Override
	public void fireObserver() {
		callback.onWeatherResult(this, getResult().get());
	}

}
