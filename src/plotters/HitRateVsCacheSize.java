package plotters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import caches.LFU;
import caches.LRU;

public class HitRateVsCacheSize {
	static String PLOT_TITLE 	= "Hit Rate vs Cache Size";
	static String X_AXIS_LABEL 	= "Cache Size";
	static String Y_AXIS_LABEL 	= "Hit rate";
				
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Plot(PLOT_TITLE, X_AXIS_LABEL, Y_AXIS_LABEL, createDataset());
	}
	
	static XYDataset createDataset() throws NumberFormatException, IOException {
		final String FILE_PATH 		= "/Users/Greg/Desktop/trace_10K.txt";
		final int MAX_CACHE_SIZE 	= 300;
		final int WARMUP     		= 2*MAX_CACHE_SIZE;
		
		final XYSeries LFU_serie = new XYSeries("LFU");
		final XYSeries LRU_serie = new XYSeries("LRU");
		
		int cache_size = 0;
		while (cache_size <= MAX_CACHE_SIZE) {
			LRU LRUCache = new LRU(cache_size, false, WARMUP);
			LFU LFUCache = new LFU(cache_size, false, WARMUP);
			
			BufferedReader stdin = new BufferedReader(new FileReader(FILE_PATH));
			String request;
			while((request = stdin.readLine()) != null) {
				String url = request.split(" ")[0];
				int size = Integer.parseInt(request.split(" ")[1]);
				LRUCache.get(url, size);
				LFUCache.get(url, size);
			}
			stdin.close();
			
			LFU_serie.add(cache_size, LFUCache.getByteHitRate());
			LRU_serie.add(cache_size, LRUCache.getByteHitRate());
			
			if (cache_size < 50) 
				cache_size+=1;
			else
				cache_size+=5;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(LFU_serie);
		dataset.addSeries(LRU_serie);
		return dataset;
   }
}