package airport;

import java.util.HashMap;
import java.util.Map;

public class JMap
{
	private Map<String, Object> map = new HashMap<>();
	
	private JMap createPath(String path) {
		String[] paths = path.split("/");
		
		JMap parent = this;
		for(int i=0; i < paths.length-1; i++) {
			JMap child = (JMap)map.getOrDefault(paths[i], new JMap());
			parent.map.put(paths[i], child);
			parent = child; 	
		}
		return parent;
	}
	
	public JMap getMap(String path) {
		String[] paths = path.split("/");
		
		JMap parent = this;
		for(int i=0; i < paths.length-1; i++) {
			JMap child = (JMap)map.get(paths[i]);
			if(child == null)
				return null;
			parent = child; 	
		}
		return parent;
	}
	
	private String getLastPath(String path) {
		String[] paths = path.split("/");
		return paths[paths.length-1];
	}
	
	private void putObj(String key, Object object) {
		JMap child = createPath(key);
		String obKey = getLastPath(key);
		
		child.map.put(obKey, object);
	}
	
	private Object get(String key) {
		JMap child = getMap(key);
		return child.map.get(getLastPath(key));
	}

	public int getInt(String key) {
		return (int)get(key);
	}
	public float getFloat(String key) {
		return (float)get(key);
	}
	public double getDouble(String key) {
		return (double)get(key);
	}
	public boolean getBoolean(String key) {
		return (boolean)get(key);
	}
	public long getLong(String key) {
		return (long)get(key);
	}
	public String getString(String key) {
		return (String)get(key);
	}
	
	public void put(String key, int value) {
		putObj(key, value);
	}
	public void put(String key, float value) {
		putObj(key, value);
	}
	public void put(String key, String value) {
		putObj(key, value);
	}
	public void put(String key, boolean value) {
		putObj(key, value);
	}
	public void put(String key, long value) {
		putObj(key, value);
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	
	
}
