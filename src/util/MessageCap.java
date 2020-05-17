package util;

public class MessageCap 
{
	private long interval;
	private long time;
	
	public MessageCap(long millis) {
		this.interval = millis;
		this.time = 0;
	}
	
	public static MessageCap fromSeconds(int seconds) {
		return new MessageCap(ClockUtil.getSeconds(seconds));
	}
	public static MessageCap fromMinutes(int minutes) {
		return new MessageCap(ClockUtil.getMinutes(minutes));
	}
	
	public boolean canMessage() {
		long now = System.currentTimeMillis();
		if(now > time + interval) {
			
			time = now;
			return true;
		}
		return false;
	}
}
