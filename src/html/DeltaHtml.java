package html;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import himawari.HWClient;
import rss.Sha256;

public class DeltaHtml
{
	private String url;
	private String query;
	
	private String currentHash = "";
	
	private Set<String> previous= new HashSet<>();
	
	
	public DeltaHtml(String url, String query) {
		this.url = url;
		this.query = query;
	}
	public void init() {
		update(false, false);
	}
	
	public void update(boolean print, boolean sms)
	{
		HWClient client = HWClient.getInstance();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements els = doc.select(query);
		
			StringBuilder newsBlock = new StringBuilder();
		
			Set<String> currentSet = new HashSet<>();
			
			for(Element el : els) {
				String news = el.text();
				if(!previous.contains(news)) {
					System.out.println(news);
					if(sms)
						client.sendSMS(news);
					//System.out.println();
				}
				newsBlock.append(news);
				currentSet.add(news);
			}
			previous = currentSet;
			currentHash = Sha256.hash(newsBlock.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
	public String getHash() {
		return currentHash;
	}

}
