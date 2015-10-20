package plotters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import caches.*;

public class HitRateVsRequestCount {
	static String PLOT_TITLE 	= "Hit Rate vs Requests Count";
	static String X_AXIS_LABEL 	= "Requests count";
	static String Y_AXIS_LABEL 	= "Hit Rate";
				
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Plot(PLOT_TITLE, X_AXIS_LABEL, Y_AXIS_LABEL, createDataset());
	}
	
	static XYDataset createDataset() throws NumberFormatException, IOException {
		final String FILE_PATH 	= "/Users/Greg/Desktop/trace_100K.txt";
		final int CACHE_SIZE 	= 1280;

		final XYSeries serie = new XYSeries("Hit Rate");
		LRU LRUCache = new LRU(CACHE_SIZE, false, 0);
		
		BufferedReader stdin = new BufferedReader(new FileReader(FILE_PATH));
		String request;
		int requestsCount = 0;
		while((request = stdin.readLine()) != null) {
			String url  = request.split(" ")[0];
			int size = Integer.parseInt(request.split(" ")[1]);
			requestsCount++;
			LRUCache.get(url, size);
			serie.add(requestsCount, LRUCache.getHitRate());
		}
		stdin.close();
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);
		return dataset;	
   }
}