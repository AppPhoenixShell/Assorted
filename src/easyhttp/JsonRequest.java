package easyhttp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonRequest extends URLRequest<JSONObject>
{
	private JsonListener callback;
	
	private static final JSONParser parser = new JSONParser();
	private String url;
	
	public JsonRequest(int what, String url, JsonListener listener) {
		super(what);
		this.url = url;
		this.callback = listener;
	}
	@Override
	public void fireObserver() {
		callback.onResult(getWhat(), getResult());
		
	}

	@Override
	protected JSONObject convertResult(String result) {
		try {
			Object jsonResult = parser.parse(result);
			if(!(jsonResult instanceof JSONObject))
				throw new RuntimeException("cannot convert json object");//TODO change
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	
}
