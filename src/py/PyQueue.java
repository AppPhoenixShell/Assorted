package py;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

public class PyQueue 
{
	private BlockingQueue<PyMessage> messageQueue = new DelayQueue<PyMessage>();
	
	public PyMessage take() {
		PyMessage message = null;
		try {
			message = messageQueue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	
	public void post(PyMessage message) {
		messageQueue.add(message);
	}
	
	public void remove(PyMessage message) {
		messageQueue.remove(message);
	}
}
