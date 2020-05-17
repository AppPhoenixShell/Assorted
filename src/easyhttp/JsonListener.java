package easyhttp;

import org.json.simple.JSONObject;

public interface JsonListener {
	public void onResult(int what, JSONObject result);
}
