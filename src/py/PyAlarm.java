package py;

public class PyAlarm 
{
	private PyAlarmCallback callback;
	public PyMessage message;
	
	public PyAlarm(PyAlarmCallback callback, PyMessage message) {
		this.callback = callback;
		this.message = message;
	}
	public void fire() {
		callback.onAlarm(message);
	}
	
	
}
