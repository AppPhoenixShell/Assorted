package asx;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import base.Api;
import rss.Sha256;
import util.ClockTimer;
import util.ClockUtil;



public class HomeCrawler 
{
	private static final String START = "https://www.fanfiction.net/";
	private static boolean isRunning = true;
	
	
	
	public static void main(String[] arg) throws FileNotFoundException, InterruptedException {
		
		Runnable run = new Runnable() {

			@Override
			public void run() {
				Scanner scanner = new Scanner(System.in);
				while(isRunning) {
					String command = scanner.nextLine();
					if(command.equals("stop"))
						isRunning = false;
				}
				scanner.close();
				
			}
			
		};
		Thread io = new Thread(run);
		io.start();
		
		File root = new File("asx/index");
		
		File[] files= root.listFiles();
		Random random = new Random();
		for(int i=0; i < 1000000; i++) {
			int r1 = random.nextInt(files.length);
			int r2 = random.nextInt(files.length);
			
			File f1 = files[r1];
			
			files[r1] = files[r2];
			files[r2] = f1;
		}
		
		int count = 0;
		System.out.println("parsing");
		ClockTimer timer = new ClockTimer(ClockUtil.SECOND * 20);
		
		Set<String> genres = new HashSet<>();
		
		File genreFile = new File("asx/genres.txt");
		Scanner scanner = new Scanner(genreFile);
		
		Set<String> deleteGenres = new HashSet<>();
		while(scanner.hasNextLine()) {
			String ig = scanner.nextLine();
			deleteGenres.add(ig);
		}
		
		for(File html : files) {
			if(timer.time())
				Api.log("...parsing files");
			if(!isRunning)
				break;
			
			Runnable parseRun = new Runnable() {
				@Override
				public void run() {
					try {
						
						Document doc = Jsoup.parse(html, "UTF-8");
						
						Elements els = doc.select("p.detail-info-right-tag-list > a");
						
						boolean romance = false;
						boolean school = false;
						
						for(Element theTag : els) {
							String genre = theTag.attr("title");
							if(deleteGenres.contains(genre)) {
								System.out.printf("deleting %s\n", html.getName());
								html.delete();
								continue;
							}
							/*
							if(genre.equalsIgnoreCase("romance")) {
								romance = true;
								//System.out.println(html.getName());
								//PrintWriter writer = new PrintWriter(new FileWriter(new File("asx/romance", html.getName())));
								
								//writer.print(doc.toString());
								//writer.close();
							}
							else if(genre.equalsIgnoreCase("School Life")) {
								school = true;
							}
							
							
							*/
							genres.add(genre);
						}
						if(school && romance)
							System.out.println(html.getName());
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			Thread t1 = new Thread(parseRun);
			t1.start();
			Thread.sleep(5);
			
			
		}
		for(String s : genres)
			System.out.println(s);
		
		Api.log("done");
		Api.log("%d/%d has romance", count, files.length);
		
	}
	
	public void start(String[] agrs) throws FileNotFoundException
	{
		DateTime now = new DateTime();
		String timestamp = now.toString(DateTimeFormat.mediumDateTime());
		
		Reader reader = new BufferedReader(new FileReader(new File(System.currentTimeMillis()+".txt")));
		
		Scanner scanner = new Scanner(reader);
		Set<String> seriesSet = new HashSet<>();
		
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			try {
				URL url = new URL(line);
				
				URI uri = url.toURI();
				
				
				String path = uri.getPath();
				String[] paths= path.split("/");
				if(paths.length > 3) {
					String series = paths[2];
					//System.out.println(series);
					seriesSet.add(series);
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(String s : seriesSet)
			System.out.println(s);
		
		
		
	}
	
	
	
}
