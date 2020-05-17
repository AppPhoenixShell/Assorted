package os;

public class MessageHandler
{
	
	protected final Thread owner;
	protected final Looper looper;
	protected final Callback callback;
	
	private static MessageHandler mainHandler;
	
	public MessageHandler(Looper looper, Callback callback) {
		
		//this.messageQueue = new PriorityBlockingQueue<>();
		this.callback = callback;
		this.owner = Thread.currentThread();
		this.looper = looper;
		if(looper.getOwner() != owner)
			throw new RuntimeException("Handler and Looper must have the same thread");
	}
	
	public static MessageHandler registerAsMainHandler(MessageHandler.Callback callback) {
		if(mainHandler != null)
			throw new RuntimeException("Main hanlder already registered");
		Looper mainLooper= Looper.getMainLooper();
		if(mainLooper.getOwner() != Thread.currentThread())
			throw new RuntimeException("MainLooper and MainHandler must be registered in same thread");
		mainHandler = new MessageHandler(Looper.getMainLooper(), callback);
		return mainHandler;
	}
	public static MessageHandler getMainHandler() {
		return mainHandler;
	}
	public static boolean isMainHandler(MessageHandler handler) {
		if(mainHandler != null)
			return handler == mainHandler;
		return false;
	}
	
	
	
	/*Private local methods*/
	private MyMessage getEmptyMessage() {
		return new MyMessage(this);
	}
	private void enqueue(MyMessage message) {
		looper.getMessageQueue().postMessage(message);
	}
	
	protected void dispatchMessage(MyMessage message) {
		if(message.callback != null)
			message.callback.run();
		else
			callback.handleMessage(message);
	}
	
	

	/*public message obtain methods*/
	public MyMessage obtain() {
		return getEmptyMessage();
	}
	public MyMessage obtain(int what) {
		MyMessage message = getEmptyMessage();
		message.what = what;
		return message;
	}
	public MyMessage obtain(int what, Object object) {
		MyMessage message = obtain(what);
		message.obj = object;
		return message;
	}
	public MyMessage obtainDelay(int what, Object object, long delay) {
		MyMessage msg = obtain(what, object);
		msg.setDelay(delay);
		return msg;
	}
	
	
	
	/*obtain runnable messages*/
	public MyMessage obtain(Runnable runnable) {
		MyMessage message = getEmptyMessage();
		message.callback = runnable;
		return message;
	}
	public MyMessage obtain(Runnable runnable, long delay) {
		MyMessage message = obtain(runnable);
		message.setDelay(delay);
		return message;
		
	}
	
	/*obtain repeating messages*/
	public MyMessage obtainRepeating(int what, Object object, long delay, long period) {
		MyMessage message = getEmptyMessage();
		message.what = what;
		message.obj = object;
		message.setDelay(delay);
		message.setRepeating(period);
		return message;
	}
	public MyMessage obtainRepeating(Runnable runnable, long delay, long period) {
		MyMessage message = obtainRepeating(0, null, delay, period);
		message.callback = runnable;
		return message;
	}
	
	public MyMessage obtainRepeating(long period) {
		MyMessage message = getEmptyMessage();
		message.setRepeating(period);
		return message;
	}
	
	/*posting a messages sends it to the queue*/
	public void post(MyMessage myMessage) {
		myMessage.setTarget(this);
		enqueue(myMessage);
	}
	public void postNow(MyMessage msg) {
		msg.setDelay(0);
		post(msg);
		
	}
	
	public void post(Runnable runnable) {
		post(runnable, 0);
	}
	
	public void post(Runnable runnable, long delay) {
		MyMessage message= obtain(runnable, delay);
		post(message);
	}

	public void postEmptyMessage(int what) {
		MyMessage message = obtain(what);
		post(message);
	}
	public void postEmptyDelay(int what, long delay) {
		MyMessage message = obtainDelay(what, null, delay);
		post(message);
	}
	public void postObject(int what, Object object) {
		MyMessage message = obtain(what, object);
		post(message);
	}

	public Thread getOwner() {
		return owner;
	}
	
	public interface Callback{
		void handleMessage(MyMessage message);
	}
}
