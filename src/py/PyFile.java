package py;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;

public class PyFile implements let{
	public static final char READ = 'r';
	public static final char WRITE = 'w';
	private static final int DEFAULT_BUFFER = 128;
	
	private File file;
	private char mode;
	
	private PrintWriter writer;
	private Reader reader;
	
	private boolean closed = false;
	
	public PyFile(File file, char mode) {
		this.file = file;
		this.mode = mode;
		init();
	}
	
	private void init(){
		
		try {
			File parent = file.getParentFile();
			if(parent == null)
				return;
			if(!parent.exists())
				parent.mkdirs();
			file.createNewFile();
			switch(mode) {
				case'r':{
					reader = new BufferedReader(new FileReader(file),DEFAULT_BUFFER);
				}break; 
				case 'w':{
					writer = new PrintWriter(new BufferedWriter(new FileWriter(file),DEFAULT_BUFFER));
				}break;
				case 'a':{
					writer = new PrintWriter(new BufferedWriter(new FileWriter(file,true),DEFAULT_BUFFER));
				}break;
			}
		}catch(IOException e) {
			
		}	
	}
	public void write(String data) {
		writer.println(data);
	}
	
	@Override
	public String toString() {
		return file.getPath();
	}
	
	
	public void close() {
		try {
			if(writer != null) {
				writer.close();
				writer = null;
			}
			if(reader != null) {
				reader.close();
				reader = null;	
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		closed = true;
	}
	
	public void mode(char mode){
		if(!closed)
			throw new RuntimeException("cannot switch mode if stream not closed first");
		this.mode = mode;
		init();
	}
	
	
	public String read() throws IOException {
		
		int length = 0;
		
		StringBuilder builder = new StringBuilder(DEFAULT_BUFFER);
		char[] buffer = new char[DEFAULT_BUFFER];
		
		
		while((length = reader.read(buffer)) != -1) {
			builder.append(buffer, 0, length);
		}
		return builder.toString();
	}
	
	
	public static PyFile open(String string, char mode) {
		File newfile = new File(string);
		
		return new PyFile(newfile, mode);
	}

	public void write(Object ob) {
		write(ob.toString());
	}

	@Override
	public Iterator<let> iterator() {
		list filelist = new PyLetSet();
		
		File[] files= file.listFiles();
	
		if(files == null) {
			Iterator<let> nullIt = new Iterator<let>() {

				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public let next() {
					return null;
				}
			};
			return nullIt;
		}
		
		
		
		for(File f : files)
			filelist.add(new PyFile(f, 'r'));
		
		return filelist.iterator();
	}
}
