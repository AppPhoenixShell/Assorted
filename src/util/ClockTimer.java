package util;

public class ClockTimer {

	private long time;
	private long next;
	
	public ClockTimer(long time)
	{
		this.time =time;
		this.next = System.currentTimeMillis();
	}
	
	public boolean time(long now) {
		boolean bol = false;
		if(now >= next) {
			next = now + time;
			bol = true;
		}
		return bol;
	}
	
	public boolean time() {
		return time(System.currentTimeMillis());
	}
}
