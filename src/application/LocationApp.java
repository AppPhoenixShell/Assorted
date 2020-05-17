package application;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.joda.time.DateTime;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import base.Api;
import himawari.HWConst;
import himawari.server.HSOUserLocation;
import os.Himawari;
import os.MessageHandler;
import os.ShutdownListener;
import os.SmallApp;
import util.MessageCap;

public class LocationApp extends SmallApp<Void,LocationApp.Observer> implements ShutdownListener {

	public static final String APP_ID = "appid.location";	
	public static final int MSG_INTERVAL_MIN = 5; 
	
	private DateTime lastMsg = new DateTime(0);
	private MessageCap capper = MessageCap.fromMinutes(5);
	
	private Object lock= new Object();
	private HSOUserLocation lastLocation;
	
	private PrintWriter writer;
	
	/**recieving location updates using firebase*/
	private ValueEventListener locationEvent = new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			HSOUserLocation location = snapshot.getValue(HSOUserLocation.class);
			postLocationToObservers(location);
			lastLocation = location;
			
			writer.println(String.format("location:[%f,%f]", location.lat, location.lng));
			
		}	
		
		@Override
		public void onCancelled(DatabaseError error) {
			
		}
	};
	/**send location to all observes using on main thread*/
	private void postLocationToObservers(HSOUserLocation location) {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				Api.log(location);
				for(Observer ob : observers) {
					ob.onLocation(location);
				}
			}
		};
		getOS().runOnMainThread(run);
	}
	
	@Override
	public void onCreate() {
		DatabaseReference root = getOS().getRoot();
		root.child(HWConst.REF_LOCATION).addValueEventListener(locationEvent);
		File file = new File("user_location.txt");
		getOS().addShutdownListener(this);
		try {
			writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file, true)));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getId() {
		return APP_ID;
	}
	
	public static interface Observer{
		void onLocation(HSOUserLocation location);
	}

	@Override
	public void onShutdown() {
		
		if(writer != null)
			writer.close();
		
	}
}
