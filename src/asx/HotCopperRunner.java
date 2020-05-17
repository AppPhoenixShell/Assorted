package asx;

import java.util.Random;

import base.Api;
import himawari.HWClient;
import html.DeltaHtml;

public class HotCopperRunner {

	public static void main(String[] args)
	{
		HWClient client = HWClient.initializeAndGetClient();
		final String URL = "https://hotcopper.com.au/announcements/asx";
		final String QUERY = "td.stock-td > a";
		
		final String URL_NEWS = "https://www.news.com.au/";
		final String NEWS_QUERY = "p.standfirst";
		
		boolean running = true;
		DeltaHtml hot = new DeltaHtml(URL, QUERY);
		DeltaHtml news = new DeltaHtml(URL_NEWS, NEWS_QUERY);	
		
		DeltaHtml delta = hot;
		delta.update(true, false);
		
		Random random = new Random();
		
		while(running)
		{
			delta.update(true, true);		
			String hash = delta.getHash();
			Api.log("newshash %s", hash);
			try {
				Thread.sleep(1000 * 60 * 15 + random.nextInt(1000 * 60 * 5));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
