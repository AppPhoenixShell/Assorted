package py;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class PyRss implements Iterable<PyRssItem>
{
	protected String title;
	protected String description;
	protected String link;
	
	private List<PyRssItem> items = new ArrayList<>();
	
	public static PyRss stream(InputStream in) {
		try {
			SAXFP fp = new SAXFP();
			SAXParserFactory.newInstance().newSAXParser().parse(in, fp);
			
			PyRss rss = fp.getFeed();
			return rss;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PyRss parse(String xml) {
		InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		return stream(in);
	}
	
	public static PyRss url(String url) {
		HttpURLConnection con = Py.connect(url);
		try {
			return stream(con.getInputStream());
		}
		catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
	public void addItem(PyRssItem rssItem) {
		items.add(rssItem);
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(String.format("{[%s, %s, %s]:\n", title, link, description));
		
		for(PyRssItem item : items)
			build.append(item).append("\n");
		build.append("}");
		return build.toString();
	}


	@Override
	public Iterator<PyRssItem> iterator() {

		return items.iterator();
	}
}
