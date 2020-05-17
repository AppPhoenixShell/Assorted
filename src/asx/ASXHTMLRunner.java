package asx;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ASXHTMLRunner {

	public static void main(String[] args) {
		File hot = new File("asx/hotcopper.html");
		
		try {
			Document doc = Jsoup.parse(hot, "UTF-8");
			
			Elements els = doc.getElementsByClass("stock-td");
			
			for(Element el : els) {
				
				Element parent = el.parent();
				Elements search = parent.getElementsByClass("stock-pill-td");
				Element code = null;
				if(search.size() > 0)
					code = search.get(0);
				
				if(code != null)
					System.out.println(code.text() + " - " + el.text());
				else
				{
					System.out.println("CodeNULL " + el.text());
				}
				
				System.out.println("----------------------");
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	
	public static void main2(String[] args) {
		
		File root = new File("asx/html");
		
		File[] htmlFiles= root.listFiles();
		
		for(File html : htmlFiles) {
			
			System.out.println("-------------------");
			System.out.println(html.getName());
			System.out.println("-------------------");
			try {
				Document doc = Jsoup.parse(html, StandardCharsets.UTF_8.name());
				
				
				Elements els = doc.getElementsByAttributeValueContaining("href", "/asx/statistics/displayAnnouncement.do");
				
				for(int i=0; i < els.size(); i++) {
					Element el = els.get(i);
					System.out.println(el.text());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("-------------------\n\n");
		}
		

	}

}
