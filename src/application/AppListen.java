package application;

import com.google.firebase.database.DataSnapshot;

import base.Api;
import os.SmallApp;
import py.Py;
import server.SimpleChildListener;

public class AppListen extends SmallApp<Void, Void> {

	@Override
	public String getId() {
		return "appid.applisten";
	}

	@Override
	public void onCreate() {
		
		Py.listenChild("sms", new SimpleChildListener() {

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				Api.log("on sms");
			}
			
		});
	}
	
	

}
