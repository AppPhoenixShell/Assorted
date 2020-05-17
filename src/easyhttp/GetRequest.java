package easyhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRequest
{
	public static final int ERROR_DEFAULT = 0;
	public static final int ERROR_MALFORMED_URL = 1;
	public static final int ERROR_IO_EXCEPTION = 2;
	public static final int ERROR_UNKNOWN_HOST = 3;
	
	private final String requestUrl;
	private int responseCode;
	private String result;
	
	private int error = ERROR_DEFAULT;
	
	public GetRequest(String url) {
		this.requestUrl = url;
	}
	
	public void doRequestAndPostResult() {
		try {
			URL url = new URL(requestUrl);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			
	
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	
			responseCode = con.getResponseCode();
	
			/*if connection OK go ahead and read it*/
			if(responseCode == HttpURLConnection.HTTP_OK) 
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				result = response.toString();
			}
		} 
		catch (MalformedURLException e) {
			error = ERROR_MALFORMED_URL;
			e.printStackTrace();
		} 
		catch (IOException e) {
			error = ERROR_IO_EXCEPTION;
			e.printStackTrace();
		}
	}
	
}
