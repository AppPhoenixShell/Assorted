package himawari;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import base.Api;

public class HWClient {
	
	private static HWClient instance;
	
	public static HWClient getInstance() {
		return instance;
	}
	
	public static HWClient initializeAndGetClient() {
		
		try {
			instance = new HWClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	private HWClient() throws IOException{
		FileInputStream serviceAccount = new FileInputStream("files/token.json");
			
		//Api.log(serviceAccount.toString());
			
		FirebaseOptions options = new FirebaseOptions.Builder()
		   .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		   .setDatabaseUrl("https://geofire-game.firebaseio.com/")
		   .build();
			
		FirebaseApp.initializeApp(options);
	}
	
	public void publishLocation(HWLocation location) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/location");
		
		ref.setValueAsync(location);
	}

	public void postGeoFence(HWLocation hwLocation, String string, int radius) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("queues/geofences");
		
		Map<String, Object> fence = new HashMap<>();
		fence.put("location", hwLocation);
		fence.put("name", string);
		fence.put("radius", radius);
	
		ref.push().setValueAsync(fence);
	}
	
	public void sendSMS(String sms) {
		FirebaseDatabase.getInstance().getReference("sms").push().setValueAsync(sms);
	}
}
