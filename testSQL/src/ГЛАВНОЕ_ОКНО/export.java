/*
 * Created by JFormDesigner on Mon Dec 15 03:29:26 EET 2014
 */

package ГЛАВНОЕ_ОКНО;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Igor
 */
public class export extends JFrame {
    public export() {
        initComponents();
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        fileChooser1 = new JFileChooser();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "172dlu:grow",
            "fill:121dlu:grow"));

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.setLayout(new FormLayout(
                "default:grow, $lcgap, default, $lcgap, default:grow",
                "2*(default, $lgap), default"));

            //---- label1 ----
            label1.setText("\u0423 \u044f\u043a\u0438\u0439 \u0444\u043e\u0440\u043c\u0430\u0442 \u0431\u0443\u0434\u0435\u0442\u0435 \u0435\u043a\u0441\u043f\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438?");
            panel1.add(label1, CC.xywh(1, 1, 5, 1));

            //---- radioButton1 ----
            radioButton1.setText("PDF");
            radioButton1.setSelected(true);
            panel1.add(radioButton1, CC.xy(1, 3));

            //---- radioButton2 ----
            radioButton2.setText("Excel");
            panel1.add(radioButton2, CC.xy(5, 3));

            //---- fileChooser1 ----
            fileChooser1.setAcceptAllFileFilterUsed(false);
            fileChooser1.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(fileChooser1, CC.xywh(1, 5, 5, 1));
        }
        contentPane.add(panel1, CC.xy(1, 1));
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButton1);
        buttonGroup1.add(radioButton2);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JFileChooser fileChooser1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
