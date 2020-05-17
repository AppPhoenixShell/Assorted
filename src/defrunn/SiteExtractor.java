package defrunn;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SiteExtractor 
{
	private File findex;
	private String selectCss;
	
	public SiteExtractor(File index, String select) {
		this.findex = index;
		this.selectCss = select;
	}
	
	public void run(SiteExtractor.Listener callback) {
		File[] files = findex.listFiles();
		
		for(int i=0; i < files.length; i++) {
			File html = files[i];
			try {
				Document doc = Jsoup.parse(html, "UTF-8");
				Elements els = doc.select(selectCss);
				
				for(Element element : els) {
					callback.onText(element.text(), i, files.length);
				}
					
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static interface Listener{
		public void onText(String text, int fileNum, int fileCount);
	}
}
