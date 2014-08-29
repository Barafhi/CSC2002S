//EVNMIC009
//Micahel Evans
//Chart.java

package assignment_1;

import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class Chart extends JFrame {
	public Chart() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void chartSignals(XYSeriesCollection dataset) {
		JFreeChart chart = createChart("Signals", "Time", "Amplitude", dataset);

		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);

		this.pack();
		this.setVisible(true);
		RefineryUtilities.centerFrameOnScreen(this);
	}

	// Creates a XYSeriesCollection from an array of floats.
	public XYSeriesCollection createDataset(float[][] data) {
		XYSeriesCollection series = new XYSeriesCollection();
		for (int i = 0; i < data.length; i++) {
			series.addSeries(createSeries("Series " + i, data[i]));
		}
		return series;
	}

	// Creates a XYSeriesCollection from an XYSeries array
	public XYSeriesCollection createDataset(XYSeries[] data) {
		XYSeriesCollection series = new XYSeriesCollection();
		for (int i = 0; i < data.length; i++) {
			series.addSeries(data[i]);
		}
		return series;
	}

	// Creates an XYSeries with a specified name and a float array.
	public XYSeries createSeries(String name, float[] data) {
		XYSeries series = new XYSeries(name);
		for (int i = 0; i < data.length; i++) {
			series.add(i, data[i]);
		}
		return series;
	}

	// Creates an XYSeries with a specified name and a float array, offset by an
	// amount.
	public XYSeries createSeries(String name, float[] data, int offset) {
		XYSeries series = new XYSeries(name);
		for (int i = 0; i < data.length; i++) {
			series.add(i + offset, data[i]);
		}
		return series;
	}

	// Creates a chart with the given dataset.
	private JFreeChart createChart(String title, String xLabel, String yLabel,
			final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createXYLineChart(title, xLabel,
				yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(Color.white);

		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		for (int i = 0; i < dataset.getSeriesCount(); i++) {
			renderer.setSeriesLinesVisible(i, true);
			renderer.setSeriesShapesVisible(i, false);
		}
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}
}