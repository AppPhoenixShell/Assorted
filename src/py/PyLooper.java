package py;

public class PyLooper 
{	
	private PyQueue queue = new PyQueue();
	private boolean looping;
	
	public void loop() {
		looping = true;
		while(looping) {
			
			PyMessage next = queue.take();
			next.dispatch();
		}
	}
	
	public void post(PyMessage message) {
		queue.post(message);
	}
}
