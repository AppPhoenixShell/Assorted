package py;

import java.util.Iterator;

public class PyString implements let {
	public final String value;
	
	public PyString(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public boolean equals(Object ob) {
		return value.equals(ob);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public Iterator<let> iterator() {
		return null;
	}
}
