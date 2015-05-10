package ГЛАВНОЕ_ОКНО;

/**
 * Created by Antony on 10.05.2015.
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

import javax.swing.*;

public class diagram extends JDialog {
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public diagram(final String title,Double a ,Double b ,Double c,Double d) {

        this.setTitle(title);

        // create a dataset...
        final PieDataset dataset = createSampleDataset(a ,b ,c, d);

        // create the chart...
        final JFreeChart chart = createChart(dataset);

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Creates a sample dataset for the demo.
     *
     * @return A sample dataset.
     */
    private PieDataset createSampleDataset(Double a ,Double b ,Double c,Double d) {
        Double sto = 100.0/(a+b+c+d);
        final DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Тверді РАВ - " + String.format("%.0f",a) + " (" + String.format("%.1f",a * sto) + "%)", a);
        result.setValue("Рідкі РАВ - " + String.format("%.0f",b) + " (" + String.format("%.1f", b * sto) + "%)", b);
        result.setValue("Біологічні РАВ - " + String.format("%.0f",c) + " (" + String.format("%.1f",c * sto) + "%)", c);
        result.setValue("ДІВ - " + String.format("%.0f",d) + " (" + String.format("%.1f",d * sto) + "%)", d);

        return result;

    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    *
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(final PieDataset dataset) {

        final JFreeChart chart = ChartFactory.createPieChart3D(
                "Відсоток загальної активності на складах",  // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false
        );

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        return chart;

    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
   /* public static void main(final String[] args) {
        final diagram demo = new diagram("Pie Chart 3D Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }*/


}
