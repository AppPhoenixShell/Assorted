import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import com.anthonynsimon.url.PyUrl;
import com.anthonynsimon.url.exceptions.MalformedURLException;

import airport.JMap;
import base.Api;
import collection.ClockArray;
import easyhttp.StringRequest;
import os.MyMessage;
import py.Py;
import py.PyDoc;
import py.PyList;
import py.PyMap;
import py.PyObjects;
import py.PyReg;
import py.PySet;
import py.PyStrings;
import py.PyTime;
import py.SAXFP;
import rss.PyRSS;
import rss.PyRSSItem;



public class Andrew 
{
	static String[] RSS = {"https://fanfox.net/rss/kaguya_sama_wa_kokurasetai_tensai_tachi_no_renai_zunousen.xml",
			"https://fanfox.net/rss/dagashi_kashi.xml",
			"https://fanfox.net/rss/high_score_girl.xml",
			"https://fanfox.net/rss/komi_san_wa_komyushou_desu.xml"};
	
	
	
	final static String drops =  
			"<?xml version=\"1.0\" encoding=\"utf-8\"?><rss version=\"2.0\"><channel><title>Nanatsuiro Drops</title><description>Latest manga chapters for Nanatsuiro Drops</description><link>https://fanfox.net/</link><lastBuildDate>Mon, 11 Mar 2013 13:34:28 GMT</lastBuildDate><generator>FeedCreator 1.7.2</generator><image><url>//c.mfcdn.net/media/logo.gif</url><title>Manga Fox</title><link>https://fanfox.net/</link><description>Feed provided by MangaFoxClick to visit.</description></image>"
			+ "<item><title>Nanatsuiro Drops Vol.01 Ch.005</title>"
			+ "<link>https://fanfox.net/manga/nanatsuiro_drops/v01/c005/1.html</link>"
			+ "<description>Yuki-Chan&amp;apos;s Secret!? Part Two</description><pubDate>Thu, 01 Jan 1970 08:00:00 GMT</pubDate></item><item><title>Nanatsuiro Drops Vol.01 Ch.004</title><link>https://fanfox.net/manga/nanatsuiro_drops/v01/c004/1.html</link><description>Yuki-chan&amp;apos;s Secret!? Part One</description><pubDate>Thu, 01 Jan 1970 08:00:00 GMT</pubDate></item><item><title>Nanatsuiro Drops Vol.01 Ch.003</title><link>https://fanfox.net/manga/nanatsuiro_drops/v01/c003/1.html</link><description>Destiny Is Seven-Colored ★ Part Two</description><pubDate>Thu, 01 Jan 1970 08:00:00 GMT</pubDate></item><item><title>Nanatsuiro Drops Vol.01 Ch.002</title><link>https://fanfox.net/manga/nanatsuiro_drops/v01/c002/1.html</link><description>Destiny is Seven-Coloured Part One</description><pubDate>Tue, 02 Jun 2009 03:56:07 GMT</pubDate></item><item><title>Nanatsuiro Drops Vol.01 Ch.001</title><link>https://fanfox.net/manga/nanatsuiro_drops/v01/c001/1.html</link><description>Sumomo&amp;apos;s First Love</description><pubDate>Thu, 01 Jan 1970 08:00:00 GMT</pubDate></item>"
			+"<item><title>Some Title</title><description>A lazy desc</description></item>" 
			+ "</channel>"
			+ "</rss>";
	
	public static void main(String[] args)
	{
		
		PyRSS rss = PyRSS.url("https://fanfox.net/rss/koi_to_uso.xml");
		
		for(PyRSSItem item: rss) {
			Py.print(item.);
		}
		

		JMap map = new JMap();
		
		map.put("names/fname", "Nick");
		map.put("names/lname", "Rosberg");
		map.put("names/nickname", "Nicky");
		map.put("age", 30);
		
		
		Py.print(map);
		
			
		
		
		
		
		
		
		
		
	}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
