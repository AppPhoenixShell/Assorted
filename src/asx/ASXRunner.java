package asx;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rss.Sha256;



public class ASXRunner
{
	public static void main(String[] args) {
		
		File root = new File("asx2/pdf");
		
		File[] asxPdfs = root.listFiles();
		
		for(File f : asxPdfs) {
			
			String code = f.getName().substring(0, 3);
			
			try {
				savePDF(f,code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		
	}
	
	
	public static void main2(String[] args) throws Exception {
		
		
		Set<String> asxCodes = new HashSet<>();
		
		String baseurl = "https://www.asx.com.au";
		String scrapurl = "https://www.asx.com.au/asx/statistics/announcements.do?by=asxCode&asxCode=%s&timeframe=Y&year=2019";
	
		File file = new File("");
		
		String[] keywords = {"appendix 4c", "quarterly"};
		Set<String> checkedUrls = new HashSet<>();
		
		/**load asx code file*/
		try {
			Scanner scanner = new Scanner(file);
			Random random = new Random();
			while(scanner.hasNext()) {
				String code = scanner.nextLine().toLowerCase();
				File f = new File("asx2/html", code+".html");
				
				System.out.println("recieving " + code);
				
				String codeUrl = String.format(scrapurl, code);
				
				File anon = new File("asx2/html", code+ ".html");
				
				Document doc = null;
				
				
				
				if(f.exists()) {
					System.out.println("loading from cache " + code);
					doc = Jsoup.parse(f, "UTF-8");
					continue;
				}
				else 
				{
					doc = Jsoup.connect(codeUrl).get();
					long sleeptime = random.nextInt(1000 * 10) + (1000 * 5);
					Thread.sleep(sleeptime);
				}
				PrintWriter printer = new PrintWriter(anon);
				String html = doc.toString();
				
				/**iterate keywords and load all pdfs from list*/
				for(String keyword : keywords) {
					Elements els = doc.getElementsContainingOwnText(keyword);
					
					System.out.printf("%d results for %s\n", els.size(), keyword);
					
					for(int i=0; i < els.size(); i++) {
						Element el = els.get(i);
						String link = el.attr("href");
						
						String newUrl = baseurl + link;
						
						
						Document doc2= Jsoup.connect(newUrl).get();
						
						Elements pdfs = doc2.getElementsByAttributeValue("name", "pdfURL");
						
						for(int j=0; j < pdfs.size(); j++) {
							Element pdf = pdfs.get(j);
							
							String pdfLink = baseurl + pdf.attr("value");
							
							if(checkedUrls.contains(pdfLink)) {
								System.out.printf("skipping %s\n", pdfLink);
								continue;
							}
							checkedUrls.add(pdfLink);
							
							
							try {
								
								savePDFUrl(pdfLink, code);
							}catch(Exception e) {
								
							}
							
							//System.out.println("PDFURL: " +  pdfLink);
						}
					}
					
				}
				processAnoncePage(html.toLowerCase(), code);
				printer.print(html);
				
				
				printer.close();
			}
			
			scanner.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void processAnoncePage(String html, String asxCode) {
		if(html.contains("appendix 4c")) {
			System.out.printf("%s contains an appendix 4c\n", asxCode);
		}
			
	}
	public static void processLocalPDF() {
		
		File file = new File("asx2/localpdf");
		
		File[] filespdf = file.listFiles();
		
		int count = 0;
		for(File pdf : filespdf) {
			try {
				System.out.printf("saving %s\n", pdf.getName());
				savePDF(pdf, pdf.getName());
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				++count;
			}
		}
		System.out.printf("%d failed pdf saves\n", count);
		
		
	}
	public static void savePDFUrl(String surl, String code) throws Exception {
		Random random = new Random();
		String hash = Sha256.hash(surl);
		
		File file = new File("asx2/pdf", code+"-"+hash+".pdf");
		if(file.exists()) {
			System.out.printf("pdf already exists skipping %s\n", surl);
			return;
		}
		System.out.printf("requesting pdf %s\n", surl);
		long pdfsleep = (random.nextInt(1000) * 5);
		Thread.sleep(pdfsleep + 1000) ;
		URL url = new URL(surl);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(file);

		//System.out.println("reading from resource and writing to file...");
		int length = -1;
		byte[] buffer = new byte[1024];// buffer for portion of data from connection
		while ((length = in.read(buffer)) > -1) {
		    fos.write(buffer, 0, length);
		}
		fos.close();
		in.close();
		
		savePDF(file, code);
		
	}
	
	public static void savePDF(File file, String code)throws Exception {
		
		PDFParser pdfParser = new PDFParser(new RandomAccessFile(file, "r"));
        pdfParser.parse();
        PDDocument asxDoc = new PDDocument(pdfParser.getDocument());
		
		//PDDocument asxDoc =  PDDocument.load(file);
		
		if (true) {
			
			asxDoc.getClass();
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFLayoutTextStripper();

            String pdfFileInText = tStripper.getText(asxDoc);
            //System.out.println("Text:" + st);

			// split by whitespace
            File raw = new File("asx2/raw_compact", code+".txt");
            PrintWriter printer = new PrintWriter(raw);
            
            Scanner scanner = new Scanner(pdfFileInText);
           
          //  printer.print(pdfFileInText);
            
            Pattern whitespace = Pattern.compile("\\s+");
            
            while(scanner.hasNextLine()) {
            	String theLine = scanner.nextLine();
            	
            	boolean empty = whitespace.matcher(theLine).matches();
            	if(empty)
            		continue;
            	
            	Scanner theScanner = new Scanner(theLine);
            	StringBuilder builder = new StringBuilder();
            	
            	while(theScanner.hasNext()) {
            		builder.append(theScanner.next());
            		builder.append(" ");
            	}
            	builder.deleteCharAt(builder.length()-1);
            	//builder.append("\n");
            	
            	printer.println(builder.toString());
            	//System.out.println(builder.toString());
            	theScanner.close();
            }
           
            scanner.close();
            /*
            String lines[] = pdfFileInText.split("\\r?\\n");
            for (String line : lines) {
                printer.println(line);
            }
            */
            printer.close();

        }
		
		asxDoc.close();
		//System.out.println("File downloaded");
		
	}
}
