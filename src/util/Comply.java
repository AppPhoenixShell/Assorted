package util;

public class Comply {
	
	
	public int cap(int min, int max, int value) {
		if(value < min)
			return min;
		if(value > max)
			return max;
		return value;
	}
	
	public int capMin(int min, int value) {return cap(min, Integer.MAX_VALUE, value);}
	public int capMax(int max, int value) {return cap(Integer.MIN_VALUE, max, value);}
}
