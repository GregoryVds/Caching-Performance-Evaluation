package cache_contents_mngt;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Lib {
	
	public static void dumpCacheContentToFile(List<String> cacheContent, String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			for (String line : cacheContent) {
				writer.println(line);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void print(String text, double pc) {
		System.out.printf("%s %.1f%c\n", text, pc, '%');
	}
}
