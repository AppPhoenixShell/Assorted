package application;

import application.ReminderApp.Reminder;
import apprunner.WeatherRequest;
import apprunner.WeatherResult;
import base.Api;
import easyhttp.EasyHttp;
import easyhttp.HttpToken;
import easyhttp.HttpToken.ChangeListener;
import os.Alarm;
import os.AlarmListener;
import os.ClockManager;
import os.Himawari;
import os.SmallApp;
import os.Token;
import util.ClockUtil;

public class TestApp extends SmallApp<Void,Void>{
	
	final static int WHAT_WEATHER = 0;
	final static int WHAT_CHANGE = 1;
	final static int WHAT_UPDATE = 2;

	
	Token alarmCheckWeather;
	
	HttpToken wtoken;
	
	WeatherRequest request;
	
	double lat = 1;
	double lng =1;
	
	private TestApplication.Input testAppIn;
	
	private AlarmListener callback = new AlarmListener() {

		@Override
		public void onAlarm(Alarm alarm) {
			switch(alarm.getWhat()) {
				case WHAT_WEATHER:
					//System.out.println(request.getUrl());
					break;
				case WHAT_CHANGE:
					request.setLatLng(lat, lng);
					++lng;
					//System.out.println(request.getUrl());
					break;
				case WHAT_UPDATE:
					//wtoken.update();
					break;
				case 77:
					Api.log("Next Minute");
					break;
				}
		}
	};
	
	
	
	@Override
	public void onStart() { 
		
	}

	private ChangeListener listener = new ChangeListener() {
		
		@Override
		public void onChange(HttpToken token, String oldhash, String newhash) {
			System.out.println("oldhash: " + oldhash +" |newhash: "  + newhash + ">" + token.getRequest().getUrl());
		}
	};
	
	private WeatherRequest.Callback weatherCallback = new WeatherRequest.Callback() {
		
		@Override
		public void onWeatherResult(WeatherRequest owner, WeatherResult result) {
			
		}
	};
	
	@Override
	public void onCreate() {
		Himawari os = getOS();
		request = new WeatherRequest(WHAT_WEATHER, weatherCallback);
		
		ClockManager clock = os.getClockManager();
		
		alarmCheckWeather = clock.setRepeatingAlarm(WHAT_WEATHER, null, System.currentTimeMillis(), ClockUtil.SECOND*5, callback);
		clock.setRepeatingAlarm(WHAT_CHANGE, null, System.currentTimeMillis() + 12000, ClockUtil.MINUTE * 3, callback);
		clock.setRepeatingAlarm(WHAT_UPDATE, null, System.currentTimeMillis(), 1000, callback);
		clock.setRepeatingAlarm(77, null, ClockUtil.getNextMinute().getMillis(), ClockUtil.MINUTE, callback);
		
		Reminder rem = new Reminder("hello reminder", System.currentTimeMillis() + ClockUtil.MINUTE);
		
		os.getInput(ReminderApp.class).postReminder(rem);
		
		
		EasyHttp http = new EasyHttp(os.getLooper());
		//wtoken = http.requestToken(request);
		//wtoken.addChangeListener(listener);
		
		//testAppIn = os.getInput(TestApplication.class);
		//testAppIn.out("some msg here", this);
	}

	@Override
	public String getId() {
		return "appid.test";
		
	}

}
