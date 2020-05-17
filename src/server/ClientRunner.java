package server;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import himawari.HWLocation;
import himawari.Himawari;

public class ClientRunner 
{
	
	
	public static void main(String[] args){
		BinaryClient client = new BinaryClient();
		
		
		client.connect(new ClientListener() {
			
			@Override
			public void onResponse(String uuid, int type, DataSnapshot object) {
				
			}
		});
		
		Himawari hw  = Himawari.get();
		hw.getClient().publishLocation(new HWLocation(1, 2, 99));
		
		hw.getClient().postGeoFence(new HWLocation(0,0,System.currentTimeMillis()), "Home", 10);
		

		System.out.println("Client Running");
		
		Scanner scan = new Scanner(System.in);
		
		
		
		
		
		
		
		
		
		while(true) {
			String location = scan.nextLine();
			String[] token = location.split(",");
			
			double lat = Double.parseDouble(token[0]);
			double lng = Double.parseDouble(token[1]);
			
			DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/location");
			
			HWLocation loc= new HWLocation(lat, lng, System.currentTimeMillis());
			ref.setValueAsync(loc);
		
			
			
			
			
			
			
			
		}
	}
}
