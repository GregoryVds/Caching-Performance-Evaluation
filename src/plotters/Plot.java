package plotters;

import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

public class Plot {
	
	public Plot(String title, String x_axis_label, String y_axis_label, XYDataset data_set) {
		XYPlot chart = new XYPlot(title, title, x_axis_label, y_axis_label, data_set);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}