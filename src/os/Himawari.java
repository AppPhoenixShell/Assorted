package os;

import com.google.firebase.database.DatabaseReference;

import himawari.HWClient;

public interface Himawari
{
	public void runOnMainThread(Runnable run);
	public MessageHandler getHandler();
	public ClockManager getClockManager();
	public Looper getLooper();
	public DatabaseReference getRoot();
	public DatabaseReference getAppRoot(Application<?,?> app);
	
	public <T extends Application<?,U>,U> void registerObserver(Class<T> appClass, U obs);
	public <T extends Application<?,U>,U> void unregisterObserver(Class<T> appClass, U obs);
	public <I, T extends Application<I,?>> I getInput(Class<T> appClass);
	
	public HWClient getClient();
	public void addShutdownListener(ShutdownListener callback);

	
}
