package application;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import base.Api;
import himawari.HWConst;
import os.Alarm;
import os.AlarmListener;
import os.Himawari;
import os.Looper;
import os.MessageHandler;
import os.MyMessage;
import os.SmallApp;
import util.ClockUtil;

public class Anotherapp extends SmallApp {
	
	final static String APP_ID = "id.smallap";
	
	int count;
	
	int nextMod = 10;
	
	long startTime = System.currentTimeMillis();
	int sum = 0;
	
	
	ValueEventListener valueEvent = new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			Api.log("Value event on " + Thread.currentThread().getName());
			MyMessage m = mHandler.obtain(0);
			m.setDebug("ValuePosted");
			m.send();
			
		}
		
		@Override
		public void onCancelled(DatabaseError error) {
			
		}
	};
	
	MessageHandler.Callback callback = new MessageHandler.Callback() {
		
		@Override
		public void handleMessage(MyMessage message) {
			if(message.what == 0) {
				Api.log("Value event posted to " + Thread.currentThread().getName());
			}
		}
	};

	MessageHandler mHandler;
	
	AlarmListener alarm = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			Api.log("Alarm running on " + Thread.currentThread().getName());
			
		}
	};
	
	@Override
	public void doUnitOfWork(Himawari os) {
		++count;
		
		if(count % nextMod == 0){
			++sum;
			Api.log("Time Taken for %d = %.0f secnds", sum, (System.currentTimeMillis() - startTime)/1000f);
			nextMod *= 10;
			startTime = System.currentTimeMillis();
		}
	}

	@Override
	public void onCreate() {
		Himawari os = getOS();
		startTime = System.currentTimeMillis();
		os.getClockManager().setDelayAlarm(0, "hello alarmclocjk", ClockUtil.MINUTE, alarm);
		
		FirebaseDatabase.getInstance().getReference(HWConst.REF_RSSFEED).addValueEventListener(valueEvent);

		mHandler = new MessageHandler(os.getLooper(), callback);
	}
	
	
	@Override
	public String getId() {
		return APP_ID;
		
	}


	@Override
	public void registerObserver(Object observer) {
		// TODO Auto-generated method stub
		
	}
	
}
