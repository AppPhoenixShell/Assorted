package pyapps;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import py.PyContext;
import py.PyDoc;
import py.let;

public class PyFlights extends PyContext {

	@Override
	public void main() {
		
		let url = str("https://www.webjet.com.au/flights/tokyo/melbourne/");
		
		let file = open("flights/melbournetotokyo.html", 'w');
		
		PyDoc doc = scrap(url);
		
		Elements els = doc.select("table");
		
		Elements prices = doc.select("span.price-centered");
		
		for(Element e : prices)
			write(file, e);
		close(file);
	}

}
