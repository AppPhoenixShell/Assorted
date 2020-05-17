package defrunn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomNextUrl implements NextUrlAlgorithm 
{
	private Set<String> allUrls = new HashSet<>();
	
	private List<String> pending = new ArrayList<>();
	private Random random = new Random();
	
	public RandomNextUrl(String...urls) {
		for(String s : urls)
			addNewUrl(s);
	}
	
	
	@Override
	public String nextUrl() {
		int nextindex = random.nextInt(pending.size());
		String nexturl = pending.get(nextindex);
		pending.remove(nextindex);
		
		return nexturl;
	}

	@Override
	public void addNewUrl(String url) {
		if(!allUrls.contains(url)) {
			allUrls.add(url);
			pending.add(url);
		}
		
	}

	@Override
	public boolean hasNextUrl() {
		return !allUrls.isEmpty();
	}


	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String addUrlToSet(String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
