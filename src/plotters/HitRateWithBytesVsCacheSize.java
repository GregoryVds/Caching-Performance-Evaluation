package plotters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import caches.*;

public class HitRateWithBytesVsCacheSize {
	static String PLOT_TITLE 	= "(Byte) Hit Rate vs Cache Size";
	static String X_AXIS_LABEL 	= "Cache Size";
	static String Y_AXIS_LABEL 	= "Hit rate";
				
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Plot(PLOT_TITLE, X_AXIS_LABEL, Y_AXIS_LABEL, createDataset());
	}
	
	static XYDataset createDataset() throws NumberFormatException, IOException {
		final String FILE_PATH 		= "/Users/Greg/Desktop/trace_10K.txt";
		final int MIN_CACHE_SIZE 	= 10000; // 10 KB (10^3)
		final int MAX_CACHE_SIZE 	= 10000000; // 10 MB (10^6)
		final int WARMUP 			= 300;
			
		XYSeries LFUSerie = new XYSeries("LFU Hit Rate");
		XYSeries LRUSerie = new XYSeries("LRU Hit Rate");
		XYSeries RLFSerie = new XYSeries("RLF Hit Rate");
		XYSeries RSFSerie = new XYSeries("RSF Hit Rate");

		XYSeries LFUSerieBytes = new XYSeries("LFU Bytes Hit Rate");
		XYSeries LRUSerieBytes = new XYSeries("LRU Bytes Hit Rate");
		XYSeries RLFSerieBytes = new XYSeries("RLF Bytes Hit Rate");
		XYSeries RSFSerieBytes = new XYSeries("RSF Bytes Hit Rate");
		
		int cacheSize = MIN_CACHE_SIZE;
		while (cacheSize <= MAX_CACHE_SIZE) {
			LRU LRUCache = new LRU(cacheSize, true, WARMUP);
			LFU LFUCache = new LFU(cacheSize, true, WARMUP);
			RLF RLFCache = new RLF(cacheSize, true, WARMUP);
			RSF RSFCache = new RSF(cacheSize, true, WARMUP);
			
			BufferedReader stdin = new BufferedReader(new FileReader(FILE_PATH));
			String request;
			while((request = stdin.readLine()) != null) {
				String url = request.split(" ")[0];
				int size = Integer.parseInt(request.split(" ")[1]);
				
				LRUCache.get(url, size);
				LFUCache.get(url, size);
				RLFCache.get(url, size);
				RSFCache.get(url, size);
			}
			stdin.close();
			
			LFUSerie.add(cacheSize, LFUCache.getHitRate());
			LRUSerie.add(cacheSize, LRUCache.getHitRate());
			RLFSerie.add(cacheSize, RLFCache.getHitRate());
			RSFSerie.add(cacheSize, RSFCache.getHitRate());
			
			LFUSerieBytes.add(cacheSize, LFUCache.getByteHitRate());
			LRUSerieBytes.add(cacheSize, LRUCache.getByteHitRate());
			RLFSerieBytes.add(cacheSize, RLFCache.getByteHitRate());
			RSFSerieBytes.add(cacheSize, RSFCache.getByteHitRate());
			
			cacheSize+=10000;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(LFUSerie);
		dataset.addSeries(LRUSerie);
		dataset.addSeries(RLFSerie);
		dataset.addSeries(RSFSerie);
		
		dataset.addSeries(LFUSerieBytes);
		dataset.addSeries(LRUSerieBytes);
		dataset.addSeries(RLFSerieBytes);
		dataset.addSeries(RSFSerieBytes);
		
		return dataset;
   }
}