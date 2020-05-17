package google;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import easyhttp.JsonListener;
import easyhttp.URLRequest;

public abstract class GoogleRequest extends URLRequest<JSONObject>
{
	protected String apiKey;
	protected JsonListener callback;
	protected boolean pending;
	
	public GoogleRequest(int what) {
		super(what);
	}

	@Override
	public final void fireObserver() {
	pending = false;
	if(callback != null)
		callback.onResult(getWhat(), getResult());
	}
	
	@Override
	protected final JSONObject convertResult(String result) {
		JSONParser par = new JSONParser();
		try {
			return (JSONObject) par.parse(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
