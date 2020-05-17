package os;

import base.Api;

/** A Looper loops on a thread processing a message queue*/
public class Looper 
{
	

	
	
	private static Looper mainLooper;
	private static Thread mainThread;
	
	protected final LooperMessageQueue queue;
	
	private boolean isLooping = false;
	private Thread owner;
	
	private String debug;
	public Looper() {
		this(null);
	}
	public Looper(String debug) {
		this.debug = debug;
		this.owner = Thread.currentThread();
		this.queue = new LooperMessageQueue(debug);
	}

	/**Loop must be called on the same thread it was created on*/
	public void loop() {
		isLooping = true;
		
		if(Thread.currentThread() != owner)
			throw new RuntimeException("Looper.loop() must be called on the same thread as the looper was created");
		
		while(isLooping) {
			
			MyMessage nextMessage = queue.takeMessage();
			nextMessage.dispatch();

			if(nextMessage.repeating) 
				nextMessage.resend(nextMessage.period);
			else
				nextMessage.recycle();
			
		}		
	}
	public void quit() {
		isLooping = false;
	}
	
	public boolean isLooping(){
		return isLooping;
	}

	public LooperMessageQueue getMessageQueue() {
		return queue;
	}

	public Thread getOwner() {
		return owner;
	}

	public static Looper getMainLooper() {
		if(mainLooper == null)
			throw new RuntimeException("MainLooper not initialized yet");
		return mainLooper;
	}
	
	
	public static boolean isMainThread() {
		return mainThread == Thread.currentThread();
	}

	public static Looper registerThreadAsMainLopper() {
		if(mainLooper !=  null)
			throw new RuntimeException("MainLooper is already registered use getMainLooper");
		mainLooper = new Looper("main");
		mainThread = Thread.currentThread();
		return mainLooper;
	}
	
	public String toString() {
		return debug;
	}
}
