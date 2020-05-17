package himawari.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.joda.time.DateTime;

import com.google.api.client.util.ArrayMap;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import base.Api;

public class HimawariServer 
{	
	private static final String SERVER_STATE = "server_state";
	
	private OnConnectedListener conListener;
	
	
	private boolean alive;
	private boolean connected;
	private ServerState currentState;
	
	private ArrayMap<String, ServerModule> modules;
	
	public HimawariServer() {
		modules = new ArrayMap<>();
	}
	
	
	private ValueEventListener serverStateListener = new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			
			currentState = snapshot.getValue(ServerState.class);
			if(!connected) {
				Api.log("Himawari Server Connected");
				conListener.onConnected(currentState);
				connected = true;
			}
		}
		
		
		@Override
		public void onCancelled(DatabaseError error) {
			
		}
	};
	
	private void onPostAlive() {	
		for(int i=0; i < modules.size(); i++) {
			ServerModule mod = modules.getValue(i);
			mod.create(this);
		}
	}

	public HimawariServer createConnection(OnConnectedListener listener) {
		this.conListener = listener;
		return this;
	}
	public void start() {
		System.out.println("Server connecting");
		alive = init();
		if(!alive) {
			System.out.println("connection failed");
			
			return;
		}
		//system is alive
		onPostAlive();
		firelog("Himawari Started");
	}

	private boolean init() {
		
		return false;
	}

	public boolean alive() {
		return alive;
	}
	
	public void sendSms(String message) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sms");
		ref.push().setValueAsync(message);
	}
	public void firelog(String message) {
		DateTime time = DateTime.now();
		String timestamp = time.toString("dd/MM/YYYY HH:mm:ss");
		FirebaseDatabase.getInstance().getReference("firelog").push().setValueAsync(timestamp + " - " +message);
	}
	
	
	public interface OnConnectedListener{
		void onConnected(ServerState state);
	}

	public <T> T getModule(String key, Class<T> clazz){
		return clazz.cast(modules.get(key));
	}

	public void addModule(String key, ServerModule module) {
		modules.add(key, module);
	}
	
}
