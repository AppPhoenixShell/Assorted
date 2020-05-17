package google;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import easyhttp.JsonListener;
import easyhttp.URLRequest;

public class SearchPlace extends GoogleRequest
{
	private static final String SEARCH_TEXT = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=%s&inputtype=textquery&key=%s";
	
	private String text;
	//protected String apiKey;
	//protected JsonListener callback;
	
	public SearchPlace(int what,String text) {
		super(what);
		this.text = text;
		//this.apiKey = client.getApiKey();
		//this.callback = callback;
		setSearchText(text);
	}
	
	public void setSearchText(String text) {
		if(text == null)
			text = "null";
		text= text.replace("  ", " ");
		text = text.replace(" ", "%20");
	}

	

	@Override
	public String getUrl() {
		return String.format(SEARCH_TEXT, text, apiKey);
	}
}
