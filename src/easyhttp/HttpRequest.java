package easyhttp;

public interface HttpRequest<T>
{
	public T getResult();
	//public void setCallback(EasyHttp.Listener callback);
	//public Listener getCallback();

	public String getUrl();
	public int getWhat();
	//public int getWhat();
	//public boolean hasResult();
	public void doRequestAndPostResult();
	public void fireObserver();
	public String getUUID();
	
	

}
