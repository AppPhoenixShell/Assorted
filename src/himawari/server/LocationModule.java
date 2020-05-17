package himawari.server;

import java.util.HashSet;
import java.util.Set;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import himawari.HWConst;

public class LocationModule extends ServerModule {
	
	private Set<LocationModule.Listener> locationListeners = new HashSet<>();
	
	public void addListener(Listener listener) {
		locationListeners.add(listener);
	}
	public void removeListener(Listener listener) {
		locationListeners.remove(listener);
	}
	
	
	private ValueEventListener locationEvent = new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			//System.out.println("location module update");
			HSOUserLocation location = snapshot.getValue(HSOUserLocation.class);
			
			for(LocationModule.Listener list : locationListeners) {
				list.onUserLocation(location);
			}
			
			//System.out.printf("%f,%f\n", location.lat, location.lng);
			
		}
		
		@Override
		public void onCancelled(DatabaseError error) {
			
		}
	};

	@Override
	public void onCreate(DatabaseReference root) {
		root.child(HWConst.REF_LOCATION).addValueEventListener(locationEvent);
	}
	
	
	public static interface Listener{
		void onUserLocation(HSORUserLocation location);
	}

}
