package os;

import java.util.HashSet;
import java.util.Set;

public abstract class SmallApp<I,T> implements Application<I,T>
{
	protected final Set<T> observers = new HashSet<T>();
	private ApplicationLogger logger;
	private Himawari OS;
	
	@Override
	public void unregisterObserver(T obs) {
		observers.remove(obs);
	}

	public Himawari getOS() {
		return OS;
	}
	
	@Override
	public void registerObserver(T observer) {
		observers.add(observer);
	}
	
	@Override
	public void bindOS(Himawari os) {
		this.OS = os;
	}

	public abstract void onCreate();

	@Override
	public void onDestory() {}
	@Override
	public void onStart() {}
	@Override
	public void doUnitOfWork(Himawari os) {}

	@Override
	public I getInput() {
		return null;
	}

	@Override
	public void onWorkError() {
		
	}
	
	

	
}
