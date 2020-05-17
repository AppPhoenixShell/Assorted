package py;

public class PyTime {
	
	private long time;
	
	
	public PyTime() {
		time = System.currentTimeMillis();
	}
	public PyTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public boolean passed(int seconds) {
		
		long delta = seconds * 1000;
		
		return (System.currentTimeMillis() - time) < delta;
	}
}
