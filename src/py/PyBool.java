package py;

import java.util.Iterator;

public class PyBool implements let {
	
	
	
	public boolean value;
	
	public PyBool(boolean val) {
		this.value = val;
	}

	@Override
	public Iterator<let> iterator() {
		return null;
	}
	
	@Override
	public String toString() {
		if(value)
			return "true";
		else
			return "false";
	}
}
