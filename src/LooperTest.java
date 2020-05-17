import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.firebase.database.FirebaseDatabase;

import application.AlarmApp;
import application.Anotherapp;
import application.AppListen;
import application.GeofenceModule;
import application.HotCopperApp;
import application.LocationApp;
import application.LogApp;
import application.RSSModule;
import application.ReminderApp;
import application.ReminderApp.Reminder;
import application.TestApp;
import application.TestApplication;
import application.WeatherApp;
import base.Api;
import easyhttp.EasyHttp;
import easyhttp.EasyHttp.Listener;
import easyhttp.GetRequest;
import easyhttp.JsonListener;
import easyhttp.StringListener;
import easyhttp.StringRequest;
import google.GoogleClient;
import google.NearbyRequest;
import google.PlaceType;
import google.SearchPlace;
import himawari.server.HimawariServer;
import himawari.server.LocationModule;
import himawari.server.ServerState;
import himawari.server.HimawariServer.OnConnectedListener;
import os.HimawariOS;
import os.Looper;
import os.MessageHandler;
import os.MyMessage;
import py.Py;
import util.ClockUtil;

public class LooperTest
{
	public static void main(String[] args) {
		
		HimawariOS os = new HimawariOS();
		//os.addApplication(new ReminderApp());
		//os.addApplication(new HotCopperApp());
		//os.addApplication(new WeatherApp());
		//os.addApplication(new LocationApp());
		//os.addApplication(new LogApp());
		//os.addApplication(new AlarmApp());
		//os.addApplication(new TestApplication());
		//os.registerObserver(TestApplication.class, obs);
		///os.addApplication(new Anotherapp());
		os.addApplication(new RSSModule());
		//os.addApplication(new GeofenceModule());
		//os.addApplication(new AppListen());		
		//testStuff();
		
		Runnable runio = new Runnable() {
			boolean running = true;
			@Override
			public void run() {
				Scanner scanner = new Scanner(System.in);
				while(running) {
					
					String command = scanner.nextLine();
					if(command.equalsIgnoreCase("quit")) {
						System.out.println("recieved quit");
						os.quit();
						running = false;
					}
				}
				scanner.close();
				
			}
			
		};
		Thread t1 = new Thread(runio);
		t1.start();
	
		os.start();
		
		Api.log("Program terminated");
	}
	private static void testStuff() {
		
		
		EasyHttp http = new EasyHttp(Looper.getMainLooper());
		
		StringRequest req = new StringRequest("https://www.asx.com.au/asxpdf/20190129/pdf/44242j4bylz0n7.pdf");
		req.doRequestAndPostResult();
		
		String result = req.getResult();
		
		System.out.println(result);
		
		
		
		
		
		
		
	}
}
