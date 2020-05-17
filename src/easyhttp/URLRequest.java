package easyhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public abstract class URLRequest<T> implements HttpRequest<T>
{
	private int what;
	private int responseCode;
	
	
	
	private String rawResult;
	private String uuid;
	
	
	
	public URLRequest(int what) {
		this.what = what;
		this.uuid = UUID.randomUUID().toString();
	}
	
	
	@Override
	public T getResult() {
		return convertResult(rawResult);
	}
	
	
	
	public boolean hasResult() {
		return rawResult != null;
	}
	
	protected abstract T convertResult(String result);

	
	

	@Override
	public int getWhat() {
		return what;
	}

	@Override
	public void doRequestAndPostResult() {
		try {
			URL url = new URL(getUrl());
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
	
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			
			responseCode = con.getResponseCode();
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			rawResult = response.toString();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String getUUID() {
		return uuid;
	}
	
	


}
