package himawari.server;

public class HSORssFeed {
	private String rsslink;
	
	public HSORssFeed() {
		
	}
	public HSORssFeed(String link) {
		this.rsslink= link;	
	}
	
	public String getRsslink() {
		return rsslink;
	}
	@Override
	public String toString() {
		return rsslink;
	}

}
