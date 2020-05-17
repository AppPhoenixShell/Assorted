package py;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class PyDoc extends Document implements let{

	
	
	public PyDoc(Document doc) {
		super(doc.baseUri());		
		List<Node> rootNodes = doc.root().childNodesCopy();
		
		for(Node n : rootNodes)
			addChildren(n);
	}
	
	public PyDoc() {
		super("/");
		// TODO Auto-generated constructor stub
	}

	public static PyDoc url(String url) {
		
		try {
			Document doc = Jsoup.connect(url)
				      .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				      .referrer("http://www.google.com")
				      .get();
			
			return new PyDoc(doc);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	public static PyDoc parse(String html) {
		Document doc = Jsoup.parse(html);
		return new PyDoc(doc);
	}
	public List<String> findHref(String cssQuery){
		Elements els = select(cssQuery);
		List<String> array = new ArrayList<>();
		
		for(Element el : els) {
			String value = el.absUrl("href");
			if(value == null || value.equals(""))
				continue;
			array.add(value);
		}
		return array;
	}
	
	public List<String> findAttr(String cssQuery, String key){
		Elements els = select(cssQuery);
		List<String> array = new ArrayList<>();
		
		for(Element el : els) {
			String value = el.attr(key);
			if(value == null || value.equals(""))
				continue;
			array.add(el.attr(key));
		}
		return array;
	}
	
	public List<String> findText(String cssQuery) {
		Elements els = select(cssQuery);
		
		List<String> array = new ArrayList<>();
		
		for(Element el : els)
			array.add(el.text());
		return array;
	}

	@Override
	public Iterator<let> iterator() {
		
		Iterator<Element> els = getAllElements().iterator();
		
		
		Iterator<let> it = new Iterator<let>() {
			
			
			@Override
			public boolean hasNext() {
				
				return els.hasNext();
			}

			@Override
			public let next() {
				Element elo = els.next();
				return new PyString(elo.outerHtml());
			}
		};
		
		return it;
	}

	public list pylinks() {
		list set = new PyLetSet();
		
		Elements els = getElementsByAttribute("href");
		for(Element e : els)
		{
			String link = e.absUrl("href");
			set.add(new PyString(link));
		}
		return set;
	}

}
