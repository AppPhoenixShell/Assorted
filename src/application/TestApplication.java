package application;

import java.util.HashSet;
import java.util.Set;

import base.Api;
import os.Alarm;
import os.AlarmListener;
import os.Application;
import os.ClockManager;
import os.Himawari;
import os.Looper;
import os.MessageHandler;
import os.MyMessage;
import util.ClockUtil;

public class TestApplication implements Application <TestApplication.Input,TestApplication.Output>{

	private MessageHandler mHandler;
	
	//private Output obs;
	private Set<Output> outputs = new HashSet<>();

	
	private Input in = new Input() {

		@Override
		public void out(String msg, Application<?,?> sender) {
			Api.log("message send from %s", sender.getId());
		}
		
	};
	
	
	private int count;
	
	private MessageHandler.Callback callback = new MessageHandler.Callback() {
		
		@Override
		public void handleMessage(MyMessage message) {
			if(message.what == 1)
				Api.log("posting 1 on %s", Thread.currentThread().getName());
		}
	};
	
	Runnable tester = new Runnable() {

		@Override
		public void run() {
			Api.log("tester on() on %s", Thread.currentThread().getName());	
			mHandler.postEmptyMessage(1);
		}
	};
	
	AlarmListener listener = new AlarmListener() {
		
		@Override
		public void onAlarm(Alarm alarm) {
			String msg = (String)alarm.getObject();
			Api.log(msg);
			
			for(Output o : outputs)
				o.send(msg);
		}
	};
	
	
	
	
	@Override
	public void onCreate() {
		Himawari os = getOS();
		ClockManager clock = os.getClockManager();
		
		clock.setDelayAlarm(77, "why hello there", 5000, listener);
		
	
		mHandler = new MessageHandler(Looper.getMainLooper(), callback);
		Thread t1 = new Thread(tester);
		t1.start();
		
		MyMessage mesage = os.getHandler().obtain(-1, null);
		mesage.setDelay(ClockUtil.MINUTE * 5);
		mesage.send();
	}
	
	

	@Override
	public void doUnitOfWork(Himawari env) {
		
	}
	
	@Override
	public String getId() {
		return "id.testapplication";
	}

	@Override
	public void registerObserver(Output observer) {
		this.outputs.add(observer);
		
	}
	
	public static interface Output{
		public void send(String alarm);
	}
	public static interface Input{
		public void out(String msg, Application<?,?> app);
	}

	@Override
	public Input getInput() {
		return in;
	}

	@Override
	public void unregisterObserver(Output obs) {
		outputs.remove(obs);
	}

	@Override
	public void bindOS(Himawari os) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Himawari getOS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onWorkError() {
		// TODO Auto-generated method stub
		
	}

}
