package asx;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ASXPDFRunner {

	private static Map<String, WordCount> wordcount = new HashMap<String, WordCount>();
	
	private static void wordCount(String file) {
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()) {
			String word = scanner.next();
			
			if(!wordcount.containsKey(word)) {
				WordCount newCount = new WordCount();
				wordcount.put(word, newCount);
			}
			WordCount theCount = wordcount.get(word);
			theCount.increment();
		}
		
		
	}
	public static void printWords(File pdfText, Pattern pattern) {
		
		StringBuilder file = new StringBuilder();
		
		
		try {
			Scanner scanner = new Scanner(pdfText);
			
			while(scanner.hasNextLine()) {
				file.append(scanner.nextLine());
				file.append("\n");
			}
			scanner.close();
			
			/**/
			Matcher fileMatcher = pattern.matcher(file.toString());
			while(fileMatcher.find()) {
				String token = file.substring(fileMatcher.start(), fileMatcher.end());
				System.out.println(token);
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void main10(String[] args) {
		
		File file = new File("asx/raw_compact");
		File pdf = file.listFiles()[0];
		
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		
		printWords(pdf, pattern);
		
	}
	
	
	public static void main(String[] args) throws IOException
	{
		File pdfroot = new File("asx2/raw_compact");
		File[] rawtextfiles= pdfroot.listFiles();
		
		
		/*
		File codes = new File("asx", "code.txt");
		Scanner asxscan = new Scanner(codes);
		
		List<String> filenames = new ArrayList<>();
		
		while(asxscan.hasNext()) {
			String asx = asxscan.nextLine();
			filenames.add(asx);
		}
		*/
		
		//String[] filenames= {"eof", "gss", "nuh", "oli", "pch", "VHT", "scl"};
		
		Map<String, String> filehash = new HashMap<>();
		
		for(File file: rawtextfiles) {
			try {
				String thefile = processPDF(file);
				if(thefile == null)
					continue;
				
				filehash.put(file.getName().substring(0, 3), thefile.toLowerCase());
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		/**iterate files*/
		//System.out.printf("%-30s%-20s%-20s\n", "code", "current", "year2");
		
		int count = 0;
		int total = filehash.size();
		
		System.out.println("Receipts from customers");
		System.out.printf("%-30s%-20s%-20s\n","File", "Current", "Year2");
		
		File asxCodes = new File("asx2/codes.txt");
		Scanner scanner = new Scanner(asxCodes);
		
		while(scanner.hasNextLine()) {
			String aCode = scanner.nextLine().toLowerCase();
			String pdf = filehash.get(aCode);
			if(pdf == null)
				continue;
			Pattern pattern = Pattern.compile("net cash from .* operating");
			
			List<NumberPair> pairs = findNumberPair(pdf, aCode, pattern);
			
			for(NumberPair p : pairs) {
				System.out.printf("%-30s%-20s%-20s\n",aCode, p.first, p.second);
			}
			
		}
		
		
		for(String key : filehash.keySet()) {
			
			
			//find(pdf, key, pattern);
			//wordCount(pdf);
			
			
			
			
			
			/*
			boolean found = findKeyword(pdf, key, "a", 50);
		
			if(found) {
				System.out.println(key);
				++count;
			}*/
			
			
				
			
		}
		
		/*
		for(String key : wordcount.keySet()) {
			WordCount counter = wordcount.get(key);
			
			if(counter.getCount() > 5)
				System.out.printf("%-10d%-40s\n", counter.getCount(), key);
		}
		*/
		
		
		
		System.out.printf("keyword found at least in %d/%d of files\n", count, total);
		
	}
	private static boolean findKeyword(String thefile, String asx, String keyword, int charsafter) {
		Pattern pattern = Pattern.compile(keyword);
		Matcher matcher = pattern.matcher(thefile);
		
		Pattern date = Pattern.compile("\\d+((st)|(nd)|(rd)|(th)|()) ((january)|(febuary)|(march)|(april)(may)|(june)|(july)|(august)|(september)|(october)|(november)|(december)) \\d{4}");
		
		boolean found = false;
		
		System.out.println("-----------------------------------");
		System.out.println("ASX: " + asx);
		System.out.println("-----------------------------------\n");
		
		//
		String firstdate = null;
		while(matcher.find()) {
			
			String keyphrase = thefile.substring(matcher.start(), matcher.end() + charsafter);
			System.out.println(keyphrase);
			Matcher datematcher = date.matcher(keyphrase);
			
			while(datematcher.find()) {
				String time= keyphrase.substring(datematcher.start(), datematcher.end());
				
				if(firstdate==null)
					firstdate= time;
			}
					
			
			
			found = true;
		}
		if(found) {
			
		}
		//System.out.println(firstdate);
		return found;
	}
	
	
	private static List<NumberPair> findNumberPair(String thefile, String asx, Pattern pattern) {
		
		//.out.println(thefile);
		List<NumberPair> pairs = new ArrayList<>();
		
		Matcher matcher = pattern.matcher(thefile);
		while(matcher.find()) {
					
			String key = thefile.substring(matcher.start(), matcher.end() + 100);
			
			//Pattern intpat = Pattern.compile("\\d+");
			//Pattern negnum = Pattern.compile("(\\d+)");
			
			
			Scanner scanner = new Scanner(key);
			
			Pattern numberPattern = Pattern.compile("\\d+(,\\d+)*");
			Pattern negPattern = Pattern.compile("\\(\\d+(,\\d+)*\\)");
			
			
			/*		
			System.out.println("\n\n---------------------------------------------------------");
			System.out.println("ASX: " + asx);
			System.out.println("---------------------------------------------------------");
			System.out.println(key);
			*/
			String[] values= new String[2];
			boolean[] positive = new boolean[2];
			int index= 0;
			
			while(scanner.hasNext()) {
				String number = scanner.next();
				if(number.equals("-") && index < values.length) {
					values[index++] = number;
					continue;
				}
				boolean isnum = numberPattern.matcher(number).matches();
				boolean isneg = negPattern.matcher(number).matches();
				
				if((isnum || isneg )&& index < values.length) {
					values[index] = number;
					positive[index] = isnum;
					++index;
				}
				
				//System.out.println("number: " + number + " pos: " + isnum + " neg: " + isneg);
				 
			}
			scanner.close();
			NumberPair pair = new NumberPair();
			pair.first = values[0];
			pair.second = values[1];
			
			pairs.add(pair);
			//if(positive[0] && !positive[1])
			//System.out.printf("%-30s%-20s%-20s\n",asx, values[0], values[1]);
		
			//System.out.println("found ");
		}	
		
			
		return pairs;			
	}
	public static String processPDFCode(String asxcode) throws Exception {
		File file = new File("asx/raw", asxcode+".txt");
		return processPDF(file);
	}
	
	public static String processPDF(File file) throws Exception {
		
		return processPDFFile(file);
	}
	
	public static String processPDFFile(File file) throws Exception {
		//
		
		if(!file.exists())
			return null;
		Pattern pattern = Pattern.compile("Net cash .* operating");
		
		
		
		
		Reader reader = new BufferedReader(new FileReader(file));
		char[] buffer = new char[100];
		
		StringBuilder builder = new StringBuilder();
		
		int length = 0;
		while((length = reader.read(buffer))!= -1) {
			builder.append(buffer, 0, length);
		}
		
		String thefile= builder.toString();
		
		return thefile;
	}
	
	

}
