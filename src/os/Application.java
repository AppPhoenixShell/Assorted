package os;

public interface Application<I,T> 
{
	public void registerObserver(T observer);
	public I getInput();
	public void bindOS(Himawari os);
	public Himawari getOS();
	
	public void onCreate();
	public void onDestory();
	public void onStart();
	
	public void doUnitOfWork(Himawari os);
	public String getId();
	
	public void unregisterObserver(T obs);
	public void onWorkError();
}
