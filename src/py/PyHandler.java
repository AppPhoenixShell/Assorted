package py;

public class PyHandler {
	private PyCallback callback;
	private PyLooper looper;
	
	public PyHandler(PyCallback callback, PyLooper looper) {
		this.callback = callback;
		this.looper = looper;
	}
	
	public void dispatch(PyMessage message) {
		callback.handleMessage(message);
	}
	
	public void post(PyMessage message) {
		message.handler = this;
		looper.post(message);
	}
}
