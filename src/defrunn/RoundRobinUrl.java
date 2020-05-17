package defrunn;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RoundRobinUrl implements NextUrlAlgorithm {

	
	private Set<String> allUrls = new HashSet<>();
	private List<String> pending = new ArrayList<>();
	private List<String> awaiting = new ArrayList<>();
	
	private Map<String, Integer> urlHostCount = new HashMap<String, Integer>();
	
	private Random random = new Random();
	private int currentIndex = 0;
	
	private int counter;
	
	public RoundRobinUrl(String...urls) {
		for(String s : urls)
			addNewUrl(s);
	}
	
	
	@Override
	public String nextUrl() {
		if(counter >= 10) {
			Collections.shuffle(pending);
			counter = 0;
		}
		++counter;
		
		int nextindex = (currentIndex + 1 )%pending.size();
		
		String nexturl = pending.get(nextindex);
		pending.remove(nextindex);
		
		return nexturl;
	}

	@Override
	public void addNewUrl(String url) {
		if(allUrls.contains(url) || !url.startsWith("http"))
			return;
		
		if(url.endsWith("tumblr.com/"))
			return;
		
		try {
			/*find host url counter*/
			URL urlob = new URL(url);
			String host = urlob.getHost();
			if(!urlHostCount.containsKey(host))
				urlHostCount.put(host, 0);
			/*only add to pending if counter < 10*/
			int urlCount = urlHostCount.get(host);
			urlHostCount.put(host, urlCount + 1);
			if(urlCount <= 10) {
				pending.add(url);
				
			}
			else {
				awaiting.add(url);
			}
			allUrls.add(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasNextUrl() {
		return !allUrls.isEmpty();
	}


	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return pending.size();
	}


	@Override
	public String addUrlToSet(String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
