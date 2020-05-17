package application;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import base.Api;
import os.Himawari;
import os.SmallApp;
import server.SimpleChildListener;

public class LogApp extends SmallApp<Void,LogApp.Observer> {

	public static final String APP_ID = "appid.logapp";
	private int workLoad = 20;
	private List<String> serverMessages = new ArrayList<>();
	
	private int index = 0;
	
	private SimpleChildListener serverLog = new SimpleChildListener() {

		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			String msg = snapshot.getValue(String.class);
			synchronized (serverMessages) {
				serverMessages.add(msg);
			}
			snapshot.getRef().removeValueAsync();
			FirebaseDatabase.getInstance().getReference("firelog_archived").push().setValueAsync(msg);
		}
	};
	
	/*processing server messages*/
	@Override
	public void doUnitOfWork(Himawari os) {
		synchronized (serverMessages) {
			for(int i=0; i < workLoad && index < serverMessages.size(); i++, index++) {
				String msg = serverMessages.get(index);
				for(Observer ob : observers)
					ob.onLogMessage(msg);
				Api.log("firelog %s", msg);	
			}
		}
	}

	@Override
	public void onCreate() {
		DatabaseReference root = FirebaseDatabase.getInstance().getReference("firelog");
		root.addChildEventListener(serverLog);
	}
	
	@Override
	public String getId(){
		return APP_ID;
	}
	
	
	public static interface Observer{
		void onLogMessage(String msg);
	}

}
