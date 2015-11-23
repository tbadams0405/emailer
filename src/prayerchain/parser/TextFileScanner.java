package prayerchain.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


/** Assumes UTF-8 encoding. JDK 7+. */
public class TextFileScanner {
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	public static void main(String... aArgs) throws IOException 
	{
		    processFile("C:/code/prayerChain/res/config.txt");
		    log("Done.");
	}
	
	public static final ArrayList<String[]> processFile(String fileToScan) throws IOException
	{
		Path fPath = Paths.get(fileToScan);
		return processLineByLine(fPath);	
	}
	
	
	/** Template method that calls {@link #processLine(String)}.  
	 * 
	 * @return values, a ArrayList of String arrays which will contain the name value pairs contained in the file
	 * @throws IOException
	 */
	public static final ArrayList<String[]> processLineByLine(Path filePath) throws IOException
	{
		ArrayList<String[]> values = new ArrayList<String[]>();
		try (Scanner scanner = new Scanner(filePath, ENCODING.name()))
		{
			while(scanner.hasNextLine())
			{
				processLine(scanner.nextLine(), values);
			}
		}
		return values;
	}
	
	/** Over-rideable method for processing lines in different ways. */
	protected static void processLine(String line, ArrayList<String[]> result)
	{
		//use a second Scanner to parse the content of each line
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter("=");
		if(scanner.hasNext())
		{
			//assumes the line has a certain structure
			String[] arr = new String[2];
			arr[0] = scanner.next();
			arr[1] = scanner.next();
			result.add(arr);
			
			//int size = result.size();
			//log("Name is : " + quote(result.get(size-1)[0].trim()) + ", and Value is : " + quote(result.get(size-1)[1].trim()));
		}
		else 
		{
			log("Empty or invalid line. Unable to process.");
		}
		scanner.close();
	}
	
	
	protected static void log(Object obj)
	{
		System.out.println(String.valueOf(obj));
	}
	
	private String quote(String text)
	{
		String QUOTE = "'";
		return QUOTE + text + QUOTE;
	}
	
}
