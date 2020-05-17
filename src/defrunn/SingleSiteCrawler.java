package defrunn;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rss.Sha256;

public class SingleSiteCrawler 
{
	private String hostname;
	private String baseUrl;
	private Set<String> seenUrls = new HashSet<>();
	
	private Queue<String> pendingQueue = new LinkedList<>();
	private PageListener pageListener;
	private LinkListener linkListener;
	private File cache;
	
	public SingleSiteCrawler(String url, File cacheFile) throws MalformedURLException, URISyntaxException {
		URL parsedUrl = new URL(url);
		this.hostname = parsedUrl.getHost();
		this.cache = cacheFile;
		this.pendingQueue.add(url);
		
		URIBuilder builder = new URIBuilder(parsedUrl.toURI());
		builder.setPath(null);
	
		baseUrl = builder.build().toString();
	}
	private void parse(Document doc) {
		Elements links = doc.getElementsByAttribute("href");
		
		for(Element href : links) {
			try {
				String url = href.attr("href");
				/**only parse urls that have not been seen*/
				if(!seenUrls.contains(url) && url.startsWith("http")) {
					URL linkUrl = new URL(url);
					/**check if url is part of the same host*/
					if(linkUrl.getHost().equals(hostname)) {
						pendingQueue.add(url);
						if(linkListener != null)
							linkListener.onNewLink(url);
					}
				}
				seenUrls.add(url);	
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void start(PageListener pageListener, LinkListener linkListener, int  minDelay, int maxDelay) {
		this.linkListener = linkListener;
		this.pageListener = pageListener;
		
		Random random = new Random();
		while(!pendingQueue.isEmpty())
		{
			String nextUrl = pendingQueue.poll();
			try {
				File htmlFile = new File(cache, Sha256.hash(nextUrl) + ".html");
				Document doc = null;
				boolean cached = false;
				if(htmlFile.exists()) {
					cached = true;
					doc = Jsoup.parse(htmlFile, "UTF-8", baseUrl);
					System.out.printf("%-10d %-40s\n", pendingQueue.size(), "Skipping " + nextUrl);
				}
				else {
					cached = false;
					doc= Jsoup.connect(nextUrl).get();
					long sleep = random.nextInt(maxDelay - minDelay) + minDelay;
					try {Thread.sleep(sleep);} catch (InterruptedException e) {}
				
				}
				parse(doc);
				URL theUrl = new URL(nextUrl);
				if(pageListener != null)
					pageListener.onNewPage(theUrl, doc, pendingQueue.size(), cached);
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	
	
	
	public static interface PageListener{
		public void onNewPage(URL url,Document page, int pending, boolean cahced);
	}
	public static interface LinkListener{
		public void onNewLink(String link);
	}
}