package py;

import java.util.Iterator;

public class num implements let{
	public Number number;
	
	public num(Number val) {
		number = val;
	}
	
	public String toString() {
		return number.toString();
	}

	@Override
	public Iterator<let> iterator() {
		return null;
	}


	public let bigger(let another) {
		if(!(another instanceof num))
			throw new RuntimeException("cannot compare");
		num nanother = (num)another;
		
		float fthis = number.floatValue();
		float fanother = nanother.number.floatValue();
		
		
		
		
		return new PyBool(fthis > fanother);
	}
}
