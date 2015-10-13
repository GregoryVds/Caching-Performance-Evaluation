package plotters;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class HitRateVsRequestCount {
	
	public static void main(String[] args) {
		XYPlot chart = new XYPlot("Hit rate vs Requests Count", "Hit rate vs Requests Count", "Requests count", "Hit rate", createDataset());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	static XYDataset createDataset() {	   
	   final XYSeries series1 = new XYSeries("First");
       series1.add(1.0, 1.0);
       series1.add(2.0, 4.0);
       series1.add(3.0, 3.0);
       series1.add(4.0, 5.0);
       series1.add(5.0, 5.0);
       series1.add(6.0, 7.0);
       series1.add(7.0, 7.0);
       series1.add(8.0, 8.0);
       
       final XYSeriesCollection dataset = new XYSeriesCollection();
       dataset.addSeries(series1);
       return dataset;
   }
}
