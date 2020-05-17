package application;

import com.google.api.client.util.ArrayMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import base.Api;
import himawari.HWConst;
import himawari.server.HSOGeofence;
import himawari.server.HSORGeofence;
import himawari.server.HSORUserLocation;
import himawari.server.HSOUserLocation;
import os.Himawari;
import os.SmallApp;
import server.SimpleChildListener;

public class GeofenceModule extends SmallApp<Void,Void>
{
	private ArrayMap<String, HSORGeofence> geofences = new ArrayMap<>();
	
	private LocationApp.Observer obs= new LocationApp.Observer() {
		
		@Override
		public void onLocation(HSOUserLocation location) {
			//System.out.println("Geofence listening");
			
			
		
			for(int i=0; i < geofences.size(); i++) {
				HSORGeofence fence = geofences.getValue(i);
				int result = fence.push(location);
				
				if(result == HSOGeofence.ENTERED) {
					Api.log(fence.getEntermessage());
					getOS().getClient().sendSMS(fence.getEntermessage());
					//server.sendSms(fence.getEntermessage());
					//server.firelog(fence.getEntermessage());
				}
				else if(result == HSOGeofence.LEAVE) {
					getOS().getClient().sendSMS(fence.getExitmessage());
					Api.log(fence.getExitmessage());
				}
				else if(result == HSOGeofence.REMAIN) {
					Api.log("dwelling in " + fence.getTitle());
					//getOS().getClient().sendSMS("Dwelling in " + fence.getTitle());
				}
			}	
		}
	};
	
	private SimpleChildListener fenceListener = new SimpleChildListener() {

		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			HSOGeofence geofence = snapshot.getValue(HSOGeofence.class);
			addGeoFence(snapshot.getKey(), geofence);
			
		}

		@Override
		public void onChildRemoved(DataSnapshot snapshot) {
			HSOGeofence geofence = snapshot.getValue(HSOGeofence.class);
			removeGeoFence(snapshot.getKey(), geofence);
		}
	};
	
	private void addGeoFence(String key, HSOGeofence fence) {
		getOS().getHandler().post(new Runnable() {
			
			@Override
			public void run() {
				geofences.put(key, fence);
				Api.log("adding:(%s)|%s", key, fence.toString());
			}	
		});
	}
	private void removeGeoFence(String key, HSOGeofence fence) {
		getOS().getHandler().post(new Runnable() {
			
			@Override
			public void run() {
				geofences.remove(key);
				Api.log("adding:(%s)|%s", key, fence.toString());
			}	
		});
	}
	
	@Override
	public void onCreate() {
		Himawari os = getOS();
		DatabaseReference root = FirebaseDatabase.getInstance().getReference();
		root.child(HWConst.REF_GEOFENCES).addChildEventListener(fenceListener);
		
		os.registerObserver(LocationApp.class, obs);
		
	}


	
	
	public void onUserLocation(HSORUserLocation location) {
		//System.out.println("Geofence listening");
		for(int i=0; i < geofences.size(); i++) {
			HSORGeofence fence = geofences.getValue(i);
			int result = fence.push(location);
			
			if(result == HSOGeofence.ENTERED) {
				Api.log(fence.getEntermessage());
				//server.sendSms(fence.getEntermessage());
				//server.firelog(fence.getEntermessage());
			}
			else if(result == HSOGeofence.LEAVE) {
				Api.log(fence.getExitmessage());
			}
		}	
	}

	@Override
	public String getId() {
		return "id.geofence";
	}

}

