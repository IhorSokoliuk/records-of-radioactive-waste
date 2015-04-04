import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Mon Mar 23 19:47:58 EET 2015
 */



/**
 * @author Ololo
 */
public class diagrama extends JFrame {
    public diagrama() {
        initComponents();
        /*final ChartPanel chartPanel = createDemoPanel();
        panel1.add(chartPanel, CC.xy(1, 1));
        add(new JButton(new AbstractAction("Add") {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < N; i++) {
                    added.add(rand.nextDouble(), rand.nextDouble());
                }
            }
        }));
        this.add(control, BorderLayout.SOUTH);*/
    }
    /*private XYSeries added = new XYSeries("Added");

    private ChartPanel createDemoPanel() {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                title, "X", "Y", createSampleData(),
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(0.00, 1.00);
        domain.setTickUnit(new NumberTickUnit(0.1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(0.0, 1.0);
        range.setTickUnit(new NumberTickUnit(0.1));
        return new ChartPanel(jfreechart);
    }

    private XYDataset createSampleData() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Random");
        for (int i = 0; i < N * N; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            series.add(x, y);
        }
        xySeriesCollection.addSeries(series);
        xySeriesCollection.addSeries(added);
        return xySeriesCollection;
    }*/

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "default, $lcgap, [201dlu,default], $lcgap, default",
            "default, $lgap, [134dlu,default], $lgap, default"));

        //======== panel1 ========
        {
            panel1.setLayout(new FormLayout(
                "default",
                "default"));
        }
        contentPane.add(panel1, CC.xy(3, 3));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
