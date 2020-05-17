package os;

import base.Api;

public class Alarm {
	
	public static final int TYPE_WHEN = 0;
	public static final int TYPE_DELAY = 1;
	

	private long when;
	private int alarmType;
	
	private AlarmListener listener;
	
	private int requestCode;
	private Object object;
	private boolean isCancelled;
	
	
	protected Token UUID;
	
	private long period;
	private boolean repeating;
	
	protected MyMessage alarmMessage;

	
	private boolean hasFired = false;
	
	
	protected Alarm(AlarmListener callback, int code, Object obj) {
		this.listener = callback;
		this.requestCode= code;
		this.object = obj;
	}
	protected void set(int type, long value) {
		if(type == TYPE_DELAY)
			setDelay(value);
		else if(type == TYPE_WHEN)
			setWhen(value);
		this.alarmType = type;
	}
	
	private void setDelay(long delay) {
		this.when = System.currentTimeMillis() + delay;
	}
	private void setWhen(long when){
		this.when = when;
	}
	protected void cancel() {	
		isCancelled = true;
		alarmMessage = null;
		UUID = null;
	}
	/*Public API Methods*/
	public Object getObject() {
		return object;
	}
	public int getWhat() {
		return requestCode;
	}
	public long getWhen() {
		return when;
	}
	
	protected void setRepeating(long period) {
		this.period = period;
		this.repeating = true;
	}
	
	protected void fireAndResendIfRepeating() {
		/*check alarm valid and fire listener*/
		if(isCancelled)
			return;
		listener.onAlarm(this);
		hasFired = true;
		/*resend message is repeating*/
		if(isCancelled)
			return;
		if(!repeating) {
			cancel();
		}
		else {
			this.when += period;
			alarmMessage.when = when;
			alarmMessage.send();
		}
	}
	
	@Override
	public String toString() {
		long delay = when - System.currentTimeMillis();
		float seconds = delay / 1000f;
		float min = seconds / 60f;
		float minSec = seconds % 60f;
		
		return String.format("alarm:{%.0f MIN %.0f SEC}", min, minSec);
	}
	
	public static long delay2When(int delay){
		return System.currentTimeMillis() + delay;
	}
	
	
}
