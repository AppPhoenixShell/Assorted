package py;

public interface map extends let {
	public void put(String key, let o);
	public void put(String key, String value);
	public void put(let key, let o);
	public void put(String key, Number num);
	public void put(String key, boolean bol);
	
	public void path(String path, Number num);
	public void path(String path, String value);
	public void path(String path, let object);
	public void path(String path, boolean bol);
	
	public map child(String path);
	
	
	public let get(String key);
	public let get(let key);
	
	public int pathInt(String path);
	public float pathFloat(String path);
	public double pathDouble(String path);
	public String pathString(String path);
	public long pathLong(String path);
	public boolean pathBool(String path);
	
	public long getLong(String key);
	public int getInt(String key);
	public float getFloat(String key);
	public float getDouble(String key);
	public String getString(String key);
	public boolean getBool(String key);
}
