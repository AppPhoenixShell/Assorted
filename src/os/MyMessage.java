package os;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.google.common.primitives.Ints;

public class MyMessage implements Delayed {

	public int what;
	public long when;
	public Object obj;
	private MessageHandler target;
	
	protected boolean repeating;
	protected long period;
	String debug;
	

	public Runnable callback;
	
	protected MyMessage(MessageHandler target) {
		this.target = target;
		this.when = System.currentTimeMillis();
	}
	
	//dispatches the message to the handler to be handled
	protected void dispatch() {
		target.dispatchMessage(this);
	}
	protected void setTarget(MessageHandler handler) {
		this.target = handler;
	}
	
	public void setDelay(long delay) {
		when = System.currentTimeMillis() + delay;
	}
	
	//sends the message to the target queue
	public void send() {
		target.post(this);
	}
	public void resend(long delay) {
		setDelay(delay);
		send();
	}
	
	

	@Override
	public int compareTo(Delayed o) {
		return Ints.saturatedCast(
			     this.when - ((MyMessage) o).when);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff =  when - System.currentTimeMillis();
		return diff;//  unit.convert(diff, TimeUnit.MILLISECONDS);
		
	}
	
	protected void setRepeating(long period) {
		this.period = period;
		this.repeating = true;
	}
	
	protected void updateWhen() {
		when += period;
	}

	public void setDebug(String string) {
		this.debug = string;
		
	}

	public void recycle() {
		// TODO Auto-generated method stub
		
	}

}
