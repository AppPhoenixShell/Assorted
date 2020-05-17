package os;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

public class LooperMessageQueue
{
	private BlockingQueue<MyMessage> messageQueue;
	private String debug;
	
	
	public LooperMessageQueue(String debug) {
		messageQueue = new DelayQueue<MyMessage>();
		this.debug = debug;
	}

	public void postMessage(MyMessage message) {
		try {
			messageQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public MyMessage takeMessage() {
		try {
			MyMessage next = messageQueue.take();
			return next;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String toString() {
		return debug;
	}

	public boolean removeFromQueue(MyMessage message) {
		return messageQueue.remove(message);
		
		
	}
}
