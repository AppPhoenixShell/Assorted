package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.omg.CORBA.Environment;

public class FileIO
{
	public static PrintWriter getPrinter(String filename, int buffSize, boolean append)
	{
		File fileob = new File(filename);
		
		fileob.getParentFile().mkdirs();
		
		
		
		try {
			fileob.createNewFile();
			PrintWriter writer = new PrintWriter(
					new BufferedWriter(new FileWriter(filename,append),buffSize));
			return writer;
		} catch (IOException e) {
			throw new RuntimeException("io exception: " + e.getMessage());
		}
	}
	public static void createFile(String file) {
		File theFile = new File(file);
		theFile.mkdirs();
	}
}
