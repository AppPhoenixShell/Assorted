package apprunner;

import easyhttp.EasyHttp;
import easyhttp.HttpToken;

public class TestApi {
	private static int WHAT_WEATHER = 1;
	
	private final WeatherRequest weather;
	private HttpToken token;
	
	public TestApi() {
		weather = new WeatherRequest(WHAT_WEATHER, callback);
		weather.setLatLng(35, 139);
	}
	
	private final WeatherRequest.Callback callback = new WeatherRequest.Callback() {
		
		@Override
		public void onWeatherResult(WeatherRequest owner, WeatherResult result) {
			if(owner == weather) {
				System.out.println(result.raw);
			}
		}
	};
	
	
	
	
	
	

	public void run(EasyHttp http) {
		token = http.requestToken(weather);
		
	}

}
