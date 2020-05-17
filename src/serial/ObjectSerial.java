package serial;

public abstract class ObjectSerial<T> {
	
	public abstract void serialize(LightSerial serial, T object);
	public abstract T deserialise(int what, LightSerial serial);

}
