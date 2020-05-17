package util;

public class DataContainer<T> {
	private T data;
	private boolean fault;
	private String faultMsg;
	
	private boolean inUse;
	
	public DataContainer(T data) {
		if(data == null)
			throw new RuntimeException("data cannot be null, data container assumes all values are non null");
		inUse= true;
		this.data = data;
	}
	
	
	public void flagFault(String msg) {
		this.fault = true;
		this.faultMsg = msg;
	}
	
	public boolean isFaulty() {
		return fault;
	}
	public String getFaultMsg() {
		return faultMsg;
	}
	
	public T get() {
		return data;
	}
	
	public void reset() {
		inUse = false;
		fault = false;
		data = null;
	}
}
