package plotters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import caches.*;

public class HitRateVsCacheSize {
	static String PLOT_TITLE 	= "Hit Rate vs Cache Size";
	static String X_AXIS_LABEL 	= "Cache Size";
	static String Y_AXIS_LABEL 	= "Hit rate";
				
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Plot(PLOT_TITLE, X_AXIS_LABEL, Y_AXIS_LABEL, createDataset());
	}
	
	static XYDataset createDataset() throws NumberFormatException, IOException {
		final String FILE_PATH 		= "/Users/Greg/Desktop/trace_100K.txt";
		final int MAX_CACHE_SIZE 	= 1280;
		
		XYSeries LFUSerie = new XYSeries("LFU");
		XYSeries LRUSerie = new XYSeries("LRU");
		
		int cacheSize = 0;
		while (cacheSize <= MAX_CACHE_SIZE) {
			LRU LRUCache = new LRU(cacheSize, false, 10*MAX_CACHE_SIZE);
			LFU LFUCache = new LFU(cacheSize, false, 10*MAX_CACHE_SIZE);
			
			BufferedReader stdin = new BufferedReader(new FileReader(FILE_PATH));
			String request;
			while((request = stdin.readLine()) != null) {
				String url = request.split(" ")[0];
				int size = Integer.parseInt(request.split(" ")[1]);
				LRUCache.get(url, size);
				LFUCache.get(url, size);
			}
			stdin.close();
			
			LFUSerie.add(cacheSize, LFUCache.getByteHitRate());
			LRUSerie.add(cacheSize, LRUCache.getByteHitRate());
			
			System.out.printf("Cache size: %d\n", cacheSize);
			if (cacheSize < 50) 
				cacheSize+=1;
			else if (cacheSize < 100)
				cacheSize+=5;
			else 
				cacheSize+=10;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(LFUSerie);
		dataset.addSeries(LRUSerie);

		return dataset;
   }
}