package application;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import base.Api;
import easyhttp.EasyHttp;
import easyhttp.StringListener;
import himawari.server.HSOUserLocation;
import os.Alarm;
import os.AlarmListener;
import os.Himawari;
import os.Looper;
import os.SmallApp;
import os.Token;
import util.ClockUtil;

public class WeatherApp extends SmallApp<Void,WeatherApp.Observer>{

	public static final int  WHAT_UPDATE_ALARM = 1;
	private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=8350e08e4f529364bbaa7f0795c41ad5&units=metric";
	
	public static final int UPDATE_INTERVAL_MIN = 20;
	
	private Token updateAlarmId = null;
	
	
	DateTime lastUpdate = new DateTime(0);
	
	HSOUserLocation lastLocation;
	EasyHttp http;

	AlarmListener alarm = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			if(alarm.getWhat() == WHAT_UPDATE_ALARM) {
				
			}
		}
	};
	
	StringListener callback = new StringListener() {
		@Override
		public void onResult(String result) {
			lastUpdate = DateTime.now();
			Api.log("parsing weather result");
			JSONParser convert= new JSONParser();
			try {
				JSONObject object = (JSONObject) convert.parse(result);
				
				Number tempMin = (Number) object.getFromPath("main/temp_min");
				Number tempMax = (Number)object.getFromPath("main/temp_max");
				Number windSpeed = (Number)object.getFromPath("wind/speed");
				Number pressure = (Number)object.getFromPath("main/pressure");
				Number humidity = (Number)object.getFromPath("main/humidity");
				
				//Api.log("min:%f|max:%f|wind:%f|pressure:%d|humidity:%d", tempMin, tempMax, windSpeed, pressure, humidity);
				for(Observer ob : observers)
					ob.onWeatherUpdate(tempMin.doubleValue(), tempMax.doubleValue(), windSpeed.doubleValue(), humidity.intValue(), pressure.intValue());
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Api.log("weather result %s", result);
		}
	};
	
	private void requestWeather(double lat, double lng) {
		
		String myUrl = String.format(BASE_URL, lat, lng);
		Api.log("requesting weather for %s", myUrl);
		//http.requestSimple(myUrl, callback);
		
	}
	
	private void updateWeather() {
		
		if(lastLocation != null) {
			Api.log("Updating the weather for %s", lastLocation.toString());
			requestWeather(lastLocation.lat, lastLocation.lng);
			
		}
		else
			Api.log("Cannot get a GPS fix to update weather");
		
		
		
	}
	
	private boolean canUpdate() {
		
		if(DateTime.now().isAfter(lastUpdate.plusMinutes(UPDATE_INTERVAL_MIN)))
			return true;
		return false;
	}
	
	LocationApp.Observer userLocation = new LocationApp.Observer() {
		
		@Override
		public void onLocation(HSOUserLocation location) {	
			WeatherApp.this.lastLocation =location;
			if(canUpdate())
				updateWeather();
		}
	};
	
	
	
	
	@Override
	public void onCreate() {
		Himawari os = getOS();
		
		updateAlarmId = os.getClockManager().setDelayAlarm(0, null, ClockUtil.getMinutes(30), alarm);
		http = new EasyHttp(os.getLooper());
		
		
		
		os.registerObserver(LocationApp.class, userLocation);
	}



	@Override
	public void doUnitOfWork(Himawari os) {
		
	}
	
	public static interface Observer{
		public void onWeatherUpdate(double minTemp, double maxTemp, double wind,long humidity, long pressure);
	}

	

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "appid.weatherapp";
	}
}
