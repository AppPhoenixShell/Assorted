package py;

public class PyLetMap extends PyMap<let> implements let, map
{
	private map getPathObject(String fullPath, boolean createPath) {
		String[] pathValues = fullPath.split("/");
		PyLetMap current = this;
		
		for(int i=0; i < pathValues.length - 1; i++) {
			if(createPath)
				current = (PyLetMap)current.getDef(pathValues[0], new PyLetMap());
			else {
				Object o  = current.get(pathValues[0]);
				current = (PyLetMap) current.get(pathValues[0]);
			}	
		}
		return current;
	}
	private String getLastPath(String fullpath) {
		String[] paths = fullpath.split("/");
		return paths[paths.length-1];
	}
	
	
	@Override
	public let get(let key) {
		return get(key.toString());
	}

	@Override
	public void put(let key, let o) {
		put(key.toString(), o);
		
	}

	@Override
	public void put(String key, Number number) {
		put(key, new num(number));
	}

	@Override
	public void put(String key, boolean bol) {
		put(key, new PyBool(bol));
		
	}

	@Override
	public void put(String key, String value) {
		put(key, new PyString(value));
	}

	@Override
	public void path(String path, Number num) {
		String key = getLastPath(path);
		getPathObject(path, true).put(key, num);
	}

	@Override
	public void path(String path, String value) {
		String key = getLastPath(path);
		getPathObject(path, true).put(key, value);
		
	}

	@Override
	public void path(String path, let object) {
		String key = getLastPath(path);
		getPathObject(path, true).put(key, object);
	}

	@Override
	public void path(String path, boolean bol) {
		String key = getLastPath(path);
		getPathObject(path, true).put(key, bol);
	}

	@Override
	public py.map child(String path) {
		
		String[] pathValues = path.split("/");
		PyLetMap current = this;
		
		for(String p : pathValues) {
			current = (PyLetMap) current.getDef(p, new PyLetMap());
		}
		return current;
	}
	@Override
	public int pathInt(String path) {
		map child = getPathObject(path, false);
		String key = getLastPath(path);
		return child.getInt(key);
	}
	@Override
	public int getInt(String key) {
		let value = get(key);
		num number = (num)value;
		return number.number.intValue();
	}
	@Override
	public float pathFloat(String path) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double pathDouble(String path) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String pathString(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long pathLong(String path) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean pathBool(String path) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public long getLong(String key) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getFloat(String key) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getDouble(String key) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getString(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getBool(String key) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
