package py;

import java.util.Random;

public class rng {
	private Random random;
	
	public rng(long seed) {
		this.random = new Random(seed);
	}
	
	public rng() {
		this.random= new Random();
	}
	
	public let integer(int bound) {
		return new num(random.nextInt(bound));
	}
	
	public let index(list array) {
		return integer(array.size());
	}
	
	public let integer(int min, int max) {
		return new num(0);
	}
}
