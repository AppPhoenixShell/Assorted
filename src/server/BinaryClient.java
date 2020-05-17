package server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BinaryClient {

	private ClientListener listener;
	
	private String sessionKey;
	
	public void connect(ClientListener listener){
		this.listener = listener;
		this.sessionKey = UUID.randomUUID().toString();
		
		try {
			
			BufferedInputStream in;
			
			
			FileInputStream serviceAccount = new FileInputStream("files/token.json");
			
			System.out.println(serviceAccount.toString());
			
			FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    .setDatabaseUrl("https://geofire-game.firebaseio.com/")
		    .build();
			
 
			FirebaseApp.initializeApp(options);
			
			FirebaseDatabase db = FirebaseDatabase.getInstance();
			
			DatabaseReference connections = db.getReference("connections");
			String session = UUID.randomUUID().toString();
			
			connections.child(session).setValueAsync("my_encruption_key");
			
			db.getReference("messages").addChildEventListener(new ChildEventListener() {
				
				@Override
				public void onChildRemoved(DataSnapshot snapshot) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
					
					if(snapshot.getKey().equals(session))
						return;
					
					DataSnapshot data = snapshot.child("data");
					int type = snapshot.child("type").getValue(Integer.class);
					
					listener.onResponse(snapshot.getKey(), type, data);
					

				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					
					
				}
			});
			
	
			//System.out.print("DONE");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void time() {
		DatabaseReference messages = FirebaseDatabase.getInstance().getReference("time");
		messages.push().setValueAsync(""+System.currentTimeMillis());
		
	}
}
