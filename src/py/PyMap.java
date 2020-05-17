package py;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PyMap<T> implements Iterable<T> {
	
	private Map<String, T> map = new HashMap<>();
	
	public void put(String key, T element) {
		map.put(key, element);
		
		
	}
	public T getDef(String key, T def) {
		T val =  map.getOrDefault(key, def);
		map.put(key, val);
		return val;
	}
	
	public T get(String key) {
		return map.get(key);
	}
	public boolean in(String key) {
		return map.containsKey(key);
	}
	public boolean notin(String key) {
		return !map.containsKey(key);
	}
	@Override
	public Iterator<T> iterator() {
		Iterator<String> keyIt = map.keySet().iterator();
		
		Iterator<T> it= new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return keyIt.hasNext();
			}

			@Override
			public T next() {
				String key = keyIt.next();
				return map.get(key);
			}
		};
		

		return it;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	public static map create() {
		// TODO Auto-generated method stub
		return new PyLetMap();
	}
}
