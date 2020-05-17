package easyhttp;

public class StringRequest extends URLRequest<String>
{
	private StringListener callback;
	private String url;
	
	public StringRequest(int what, String url, StringListener listener) {
		super(what);
		this.callback= listener;
		this.url = url;
	}
	
	public StringRequest(String url) {
		super(Integer.MIN_VALUE);
		this.url = url;
	}

	@Override
	public String convertResult(String result) {
		return result;
	}

	@Override
	public void fireObserver() {
		if(callback != null)
			callback.onResult(getResult());
	}

	@Override
	public String getUrl() {
		return url;
	}	
}
