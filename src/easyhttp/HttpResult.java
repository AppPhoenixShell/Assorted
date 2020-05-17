package easyhttp;

import easyhttp.EasyHttp.Listener;

public class HttpResult
{
	protected Listener callback;
	protected Object result;
	
	public HttpResult(Listener callback, Object result) {
		this.callback = callback;
		this.result = result;
	}
	
}
