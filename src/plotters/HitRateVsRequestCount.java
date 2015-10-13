package plotters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import cache_contents_mngt.LRU;

public class HitRateVsRequestCount {
	static int CACHE_SIZE = 100;
	static String FILE_PATH = "/Users/Greg/Desktop/trace_tiny.txt";
			
	public static void main(String[] args) throws NumberFormatException, IOException {
		XYPlot chart = new XYPlot("Hit rate vs Requests Count", "Hit rate vs Requests Count", "Requests count", "Hit rate", createDataset());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	static XYDataset createDataset() throws NumberFormatException, IOException {	   
		final XYSeries serie = new XYSeries("Hit Rate");
		LRU LRUCache = new LRU(0, false, CACHE_SIZE);

		BufferedReader stdin = new BufferedReader(new FileReader(FILE_PATH));
		String request;
		int requestsCount = 0;
		while((request = stdin.readLine()) != null) {
			String url  = request.split(" ")[0];
			int size = Integer.parseInt(request.split(" ")[1]);
			requestsCount++;
			LRUCache.get(url, size);
			System.out.println("Hit rate:"+LRUCache.getHitRate());
			serie.add(requestsCount, LRUCache.getHitRate());
		}

		stdin.close();
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);
		return dataset;
   }
}
