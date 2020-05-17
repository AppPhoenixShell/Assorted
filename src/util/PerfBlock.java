package util;

public class PerfBlock {
	
	private long[] timers;
	private boolean started[];
	
	private long[] totalTime;
	private int[] counter;
	
	
	
	private long[] min;
	private long[] max;
	
	
	public PerfBlock(int size) {
		timers = new long[size];
		min = new long[size];
		max = new long[size];
		started= new boolean[size];
		totalTime = new long[size];
		counter = new int[size];
	}
	
	public void start(int index) {
		if(started[index])
			throw new RuntimeException("Cannot start a timer already started");
		timers[index] = System.nanoTime();
		started[index]= true;
	}
	public long end(int index) {
		if(!started[index])
			return 0;
		long delta = System.nanoTime()- timers[index];
		
		//update min and max times
		if(delta < min[index])
			min[index] = delta;
		if(delta > max[index])
			max[index] = delta;
		//update average
		totalTime[index] += delta;
		++counter[index];
		
		started[index]= false;
		return delta;
	}
	
	public long getMin(int index) {return min[index];}
	public long getMax(int index) {return max[index];}
	public long getAverage(int index) {
		int count = counter[index];
		if(count == 0)
			return 0;
		return totalTime[index]/ counter[index];}
	public long getTotalTime(int index) {return totalTime[index];}
	
}
