package defrunn;

import java.net.URL;

public interface NextUrlAlgorithm {
	
	public String nextUrl();
	public void addNewUrl(String url);
	public boolean hasNextUrl();
	public int getSize();
	public String addUrlToSet(String url);
}
