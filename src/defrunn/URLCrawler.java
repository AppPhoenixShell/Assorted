package defrunn;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import base.Api;
import defrunn.SiteExtractor.Listener;
import himawari.HWClient;
import rss.Sha256;
import server.SimpleChildListener;
import util.ClockTimer;
import util.ClockUtil;
import util.FileIO;

public class URLCrawler {
	
	private static final String[] START = { 
			"http://fanfox.net/"
			};
	
	public static void main2u36(String[] args) {
		PrintWriter writer = FileIO.getPrinter("some/random/dir/list.txt", 512, false);
		String query = "div.iltext";
		SiteExtractor site= new SiteExtractor(new File("webindex"),query);
		ClockTimer timer = new ClockTimer(ClockUtil.SECOND * 5);
		
		List<String> desc = new ArrayList<>();
		
		site.run(new Listener() {
			
			@Override
			public void onText(String text, int fileNum, int count) {
				//System.out.println(text);
				if(timer.time())
					Api.log("parsing... completed %d/%d", fileNum, count);
				//writer.println(text);
				desc.add(text);
			
			}
		});
		Pattern pattern = Pattern.compile("romance");
		for(String item : desc)
		{
			Matcher matcher = pattern.matcher(item);
			if(matcher.find()) {
				System.out.println(item);
				System.out.println();
			}
			
			
			
		}
		writer.close();
		
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		String url = "https://www.flightcentre.com.au/";
		//Document doc = Jsoup.connect(").get();
		
		
		
		SingleSiteCrawler crawler = new SingleSiteCrawler(url, new File("booking"));
		
		crawler.start(new SingleSiteCrawler.PageListener() {
			
			@Override
			public void onNewPage(URL url, Document page, int pending, boolean cached) {
				
				try {
					String filename = Sha256.hash(url.toString()+".html");
					PrintWriter writer = new PrintWriter(new FileWriter(new File("booking", filename)));
					writer.write(page.toString());
					writer.close();
					
					if(!cached)
						System.out.printf("%s\n", url.toString());
					else
						System.out.printf("reading from cache %s\n", url.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		}, new SingleSiteCrawler.LinkListener() {
			
			@Override
			public void onNewLink(String link) {
				//System.out.println(link);
				
			}
		}, 1000, 1500);
		
		
	}
	
	public static void main3(String[] args) {
		
		
		HWClient.initializeAndGetClient();
		URLCrawler crawler = new URLCrawler(new RoundRobinUrl(START), new HttpSleep(1000));
		crawler.startRecursive(new HashSet<>(), START[0], 0);
	}
	
	private NextUrlAlgorithm algo;
	private HttpBlockingAlgorithm blocking;
	
	private List<String> skipStart = new ArrayList<>();
	private List<String> skipEnds = new ArrayList<>();
	
	private SimpleChildListener skipUrl = new SimpleChildListener() {

		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			String type= snapshot.child("type").getValue(String.class);
			String value =snapshot.child("value").getValue(String.class);
			
			if(type.equals("startsWith"))
				skipStart.add(value);
			else if(type.equals("endsWith"))
				skipEnds.add(value);
			System.out.printf("adding %s %s to skiplist\n", type, value);
		}
	
	};
	
	public URLCrawler(NextUrlAlgorithm url, HttpBlockingAlgorithm block) {
		this.algo = url;
		this.blocking = block;
	}
	
	public void startRecursive(Set<String> urls, String url, int depth) {
		if(depth == 2 || !url.startsWith("http"))
			return;
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.getElementsByTag("a");
			for(Element href : links) {
				String daLink = href.absUrl("href");
				if(urls.contains(daLink))
					continue;
				urls.add(daLink);
				System.out.println(daLink);
				Thread.sleep(1000);
				startRecursive(urls, daLink, ++depth);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			return;
		}
		
		
	}
	
	public void start() {
		
		FirebaseDatabase.getInstance().getReference("skiplist").addChildEventListener(skipUrl);
		skipEnds.add("tumblr.com");
		
		
		while(algo.hasNextUrl()) {
			String nextUrl = algo.nextUrl();
		
			String absUrl = null;
			
			try {
				blocking.startRequest();
				
				Connection con = Jsoup.connect(nextUrl);
				Document doc= con.get();
				blocking.endRequest();
				int bytes= doc.toString().getBytes("UTF-8").length;
				blocking.bytesDownloaded(bytes);
				
				
			
				Elements allLinks = doc.getElementsByTag("a");
				
				checkLoop:
				for(Element link : allLinks) {
					absUrl = link.absUrl("href");
					
					if(!absUrl.startsWith("http"))
						continue;
					URL oburl = new URL(absUrl);
					String host= oburl.getHost();
					for(String skipper : skipStart)
						if(host.startsWith(skipper)) {
							//System.out.printf("skipping %s\n", absUrl);
							continue checkLoop; 
						}
					for(String skipper : skipEnds) 
						if(host.endsWith(skipper)) {
							//System.out.printf("skipping %s\n", absUrl);
							continue checkLoop;
						}
							
					
					
					algo.addNewUrl(absUrl);
				}
				System.out.printf("%-10d + -10%d %-40s\n",algo.getSize(),allLinks.size(),nextUrl);
				
				
			} catch (IOException e) {
				System.out.println(absUrl);
				e.printStackTrace();
			}
			
			 
		}
	}
	
	
	
	
	
	public void main3(String url) throws InterruptedException, IOException {
		
		Set<String> uniqueHosts = new HashSet<>();
		Set<String> allUrls  = new HashSet<>();
		
		Set<String> checkedUrls = new HashSet<>();
		
		//List<String> pendingUrls = new ArrayList<>();
		//Map<String, List<String>> hostListMap = new HashMap<String,List<String>>();

		//List<ArrayList<String>> hostLists = new ArrayList<ArrayList<String>>();
		
		List[] array = new List[512];
		
		for(int i=0; i < array.length; i++) {
			array[i] = new ArrayList();
		}
		
		
		
		boolean running = true;
		Random rand = new Random(1);
		DateTime now = new DateTime();
		String timestamp = now.toString(DateTimeFormat.mediumDateTime());
		int currentIndex = 0;
		
		array[currentIndex].add(url);
		
		//pendingUrls.add(START);
		/*create file writer*/
		File file =new File(System.currentTimeMillis()+".txt");
		file.createNewFile();
		PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
		
		
		
		String currentUrl = null;
		
		/****/
		while(running) {
			currentIndex %= array.length;
			List<String> pendingUrls = array[currentIndex];
			++currentIndex;
			
			if(pendingUrls.isEmpty()) {
				System.out.println("Skipping list empty");
				continue;
			}
			
			writer.flush();
			
			int next = rand.nextInt(pendingUrls.size());
			
			/**get random url from list an add to the set of checked urls*/
			currentUrl = pendingUrls.get(next);
			pendingUrls.remove(next);
			checkedUrls.add(currentUrl);
			
			try {
				
				
				/*
				if(true);//currentUrl.contains("chapter")) {
					//System.out.printf("skipping %s\n", currentUrl);
					//continue;
				}*/
				File saveHtml = new File("asx/pages", Sha256.hash(currentUrl)+".html");
				Document doc = null;
				if(saveHtml.exists()) {
					doc = Jsoup.parse(saveHtml, "UTF-8");
					//.out.printf("skipping %s\n",currentUrl);
				}else {
					long sleep = 100 + rand.nextInt(100);
					Thread.sleep(sleep);
					doc = Jsoup.connect(currentUrl).get();
					System.out.printf("requesting (%d) - %s\n", pendingUrls.size(), currentUrl);
					PrintWriter writeHtml = new PrintWriter(new BufferedOutputStream(new FileOutputStream(saveHtml)));
					writeHtml.write(doc.toString());
					writeHtml.close();
				}
	
				URL hosturl = new URL(currentUrl);
				
				Elements linkPages = doc.getElementsByTag("a");
				
				/**Iterate through all links in a webpage*/
				for(Element page : linkPages) {
					String newurl = page.attr("href");
					
					/**if local path convert to full url*/
					if(newurl.startsWith("/")) {
						//System.out.println(url);
						URL par = new URL(currentUrl);
						try {
							URIBuilder builder = new URIBuilder(par.toURI());
							builder.setPath(newurl);
							
							newurl = builder.toString();
							
							//System.out.println(builder.toString());
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
					if(newurl.startsWith("http")) {
						/**if url has already been seen before skip it entirely*/
						if(allUrls.contains(newurl))
							continue;
						
						/**Parse url into an objecy to extract data*/
						URL theUrl = new URL(newurl);
						//String path = theUrl.getPath();
						//String[] pathlelements = path.split("/");
						/*
						if(true|| !theUrl.getHost().equals(hosturl.getHost()))
							continue;
							*/
						
						
						String host = theUrl.getHost();
						/**if url*/
						
						//System.out.println(url);
						
						

						uniqueHosts.add(newurl);
						allUrls.add(newurl);
						
						if(!checkedUrls.contains(newurl)) {
							if(true ) {//|| pathlelements.length <= 3 && (!pathlelements[1].equals("rss"))& (!pathlelements[1].equals("login"))) {
								
								List randomList = array[rand.nextInt(array.length)];
								randomList.add(newurl);
								writer.println(newurl);
							}
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			/**random sleep*/
		}
		
		
		System.out.println("Program Terminated");
		writer.close();
	}
}
