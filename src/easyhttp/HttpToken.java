package easyhttp;

import java.util.HashSet;
import java.util.Set;

import rss.Sha256;


public class HttpToken {
	
	private static final String NULL_VALUE = "null";
	private static final String NULL_HASH = Sha256.hash(NULL_VALUE);
	
	private long created;
	private String urlHash;
	private final String uuid;
	private HttpRequest<?> request;
	
	private Set<ChangeListener> changeListeners;


	public HttpToken(HttpRequest<?> request) {
		this.created = System.currentTimeMillis();
		this.urlHash = getHash(request);
		this.uuid = request.getUUID();
		this.request = request;
		this.changeListeners = new HashSet<>();
	}
	
	private static final String getHash(HttpRequest<?> request) {
		return Sha256.hash(request.getWhat() + request.getUrl());	
	}
	
	public void addChangeListener(ChangeListener listener) {
		this.changeListeners.add(listener);
	}
	public void removeChangeListener(ChangeListener listener) {
		this.changeListeners.remove(listener);
	}

	public void update() {
		/*
		if(!request.getUUID().equals(uuid))
			return false;
		 */
		//uuid have come from same request object
		String newhash = getHash(request);
		String oldhash = this.urlHash;
		
		if(!newhash.contentEquals(oldhash)) {
			//url requests atre different update it
			this.urlHash = newhash;
			
			for(ChangeListener list : changeListeners)
				list.onChange(this, oldhash, newhash);
		}
	}
	
	public boolean isNull() {
		return urlHash.equals(NULL_HASH);
	}
	
	@Override
	public String toString() {
		return urlHash;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof HttpToken) {
			HttpToken other = (HttpToken)obj;
			other.urlHash.equals(urlHash);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return urlHash.hashCode();
	}
	
	public void release() {
		request = null;
	}
	
	
	public static interface ChangeListener{
		public void onChange(HttpToken token, String oldhash, String newhash);
	}


	public HttpRequest<?> getRequest() {
		return request;
	}
	
}
