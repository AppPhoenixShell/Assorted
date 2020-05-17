package os;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.client.util.ArrayMap;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import base.Api;
import himawari.HWClient;
import util.ClockUtil;

public class HimawariOS implements Himawari{
	
	private static final String REF_APP_ROOT = "appdata";
	
	protected static final int WHAT_SHUTDOWN = -1;
	protected static final int WHAT_NEW_APPLICATION = 10;
	protected final int WHAT_APP_TICK = 11;
	protected static final int WHAT_APP_DESTROY = 12;
	protected static final int WHAT_QUITLOOPER = -10;
	
	
	
	private final Looper osLooper;
	private final MessageHandler osHandler;
	private final ClockManager osClock;
	private final TokenManager osTokens;
	private final HWClient client;
	
	private Set<ShutdownListener> callbacks = new HashSet<>();
	
	private ArrayMap<String, Application<?,?>> apps;
	
	
	/** Adds to unique set of application list*/
	private void addUniqueAppToList(Application<?,?> app) {
		for(int i=0; i < apps.size(); i++) {
			if(apps.getValue(i).getClass() == app.getClass())
				throw new RuntimeException("Cannot app two applications of the same class");
		}
		apps.add(app.getId(), app);
	}
	
	/**Main handler callback for top level OS messages*/
	private MessageHandler.Callback osCallback = new MessageHandler.Callback() {
		
		@Override
		public void handleMessage(MyMessage message) {
			switch(message.what) {
				case WHAT_SHUTDOWN:{
					for(ShutdownListener l : callbacks)
						l.onShutdown();
					Api.log("Shutdown");
					client.sendSMS("Himawari OS Shutting Down");
					osHandler.postEmptyDelay(WHAT_QUITLOOPER, ClockUtil.SECOND * 10);
					
				}break;
				case WHAT_QUITLOOPER:{
					System.out.println("looper quiting");
					osLooper.quit();
				}break;
				case WHAT_NEW_APPLICATION:{
					Application<?,?> app = (Application<?,?>)message.obj;
					
					Api.log("App Starting %s", app.getId());
					
					app.onCreate();
					
					app.onStart();
					message.what = WHAT_APP_TICK;
					message.setDelay(0);
					osHandler.post(message);
				}break;
				case WHAT_APP_TICK:{
					Application<?,?> app = (Application<?,?>)message.obj;
					try {
						app.doUnitOfWork(HimawariOS.this);
					}catch(Exception e) {
						app.onWorkError();
					}
					
					message.setDelay(0);
					osHandler.post(message);
				}break;
				case WHAT_APP_DESTROY:{
					Application<?,?> app = (Application<?,?>)message.obj;
					apps.remove(app.getId());
					app.onDestory();
				}break;
				
			}
		}
	};
	
	public HimawariOS() {
		apps = new ArrayMap<>();
		osLooper = Looper.registerThreadAsMainLopper();
		osHandler = MessageHandler.registerAsMainHandler(osCallback);
		osClock = new ClockManager(this);
		osTokens= new TokenManager(this);
		client = HWClient.initializeAndGetClient();
	}
	
	/** Initialized the OS with a firebase reference*/
	
	/** Add an application class to the OS system*/
	public void addApplication(Application<?,?> app) {
		app.bindOS(this);
		addUniqueAppToList(app);
		MyMessage msg = osHandler.obtain(WHAT_NEW_APPLICATION, app);
		msg.send();
	}
	
	/** removes an application from the runtime*/
	public void removeApp(Application<?,?> app) {
		MyMessage msg = osHandler.obtain(WHAT_APP_DESTROY, app);
		msg.send();
	}

	/** Start the OS message loop*/
	public void start() {
		client.sendSMS("Himawari OS Executing");
		osLooper.loop();
	}

	@Override
	public MessageHandler getHandler() {
		return osHandler;
	}
	
	@Override
	public ClockManager getClockManager() {
		return osClock;
	}

	@Override
	public Looper getLooper() {
		return osLooper;
	}
	
	@Override
	public <T extends Application<?,U>,U> void registerObserver(Class<T> appClass, U obs){
		
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				T theApp = getApplication(appClass);
				if(theApp == null)
					throw new RuntimeException("app not avalable to register observer");
				theApp.registerObserver(obs);
			}
		};
		osHandler.post(ru);
	}
	
	private <T> T getApplication(Class<T> clazz) {
		
		for(int i=0; i < apps.size(); i++) {
			Application<?,?> app = apps.getValue(i);
			if(clazz.isInstance(app))
				return clazz.cast(app);
		}
		return null;
	}

	@Override
	public <I, T extends Application<I, ?>> I getInput(Class<T> appClass) {
		Application<I,?> app = getApplication(appClass);
		if(app != null)
			return app.getInput();
		return null;
	}

	@Override
	public <T extends Application<?, U>, U> void unregisterObserver(Class<T> appClass, U obs) {
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				T theApp = getApplication(appClass);
				theApp.unregisterObserver(obs);
			}
		};
		osHandler.post(ru);
		
	}

	@Override
	public DatabaseReference getRoot() {
		return FirebaseDatabase.getInstance().getReference();
	}

	@Override
	public DatabaseReference getAppRoot(Application<?,?> app) {
		return getRoot().child(REF_APP_ROOT).child(app.getId());
	}

	@Override
	public void runOnMainThread(Runnable run) {
		osHandler.post(run);
	}

	public void quit() {
		osHandler.postEmptyDelay(WHAT_SHUTDOWN, ClockUtil.SECOND);	
	}
	
	@Override
	public void addShutdownListener(ShutdownListener callback) {
		callbacks.add(callback);
	}

	@Override
	public HWClient getClient() {
		return client;
	}
	
}
