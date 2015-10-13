package plotters;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;

public class XYPlot extends ApplicationFrame {
	private static final long serialVersionUID = 1L;

	public XYPlot(String applicationTitle, String chartTitle, String xAxisLabel, String yAxisLabel, XYDataset dataSet) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataSet, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560,400));
		setContentPane(chartPanel);
	}
}