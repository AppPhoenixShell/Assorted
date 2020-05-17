package py;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.google.common.primitives.Ints;

public class PyMessage implements Delayed
{
	protected int what;
	protected long when;
	public Object object;
	
	public String debug;

	PyHandler handler;
	
	public PyMessage(long when) {
		this.when = when;
	}

	public PyMessage() {
		this.when = System.currentTimeMillis();
	}
	
	public void dispatch() {
		handler.dispatch(this);
	}

	@Override
	public int compareTo(Delayed o) {
		return Ints.saturatedCast(
			     this.when - ((PyMessage) o).when);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff =  when - System.currentTimeMillis();
		return diff;//  unit.convert(diff, TimeUnit.MILLISECONDS);
		
	}
	public static PyMessage what(int what, Object object) {
		return create(System.currentTimeMillis(), what, object);
	}
	public static PyMessage what(int what, Object object, long delay) {
		return create(System.currentTimeMillis() + delay, what, object);
	}
	public static PyMessage what(int what, long delay) {
		return what(what, null, delay);
	}
	
	public static PyMessage create(long when, int what, Object object) {
		PyMessage message= new PyMessage();
		message.when = when;
		message.what = what;
		message.object = object;
		return message;
	}
	
	public static PyMessage what(int what) {
		return what(what, null);
		
	}
	

}
