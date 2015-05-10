/*
 * Created by JFormDesigner on Sun Nov 30 17:12:18 EET 2014
 */

package √À¿¬ÕŒ≈_Œ ÕŒ.–¿ƒ≤Œ¿ “»¬Õ≤_Õ” À≤ƒ»;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class insertIntoRadio extends JFrame {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public insertIntoRadio() {
        initComponents();
    }

    private int open() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "oblik_rao";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "123456";
        try {
            Class.forName(driver).newInstance();
            connect = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("Connection opened!");
        } catch (Exception e) {
            System.out.println(e);
        }
        statement = connect.createStatement();
        return 0;
    }

    private int close() {
        try {
            if (resultSet != null) {
                resultSet.close();
                System.out.println("result closed!");
            }
            if (statement != null) {
                statement.close();
                System.out.println("statement closed!");
            }
            if (connect != null) {
                connect.close();
                System.out.println("connection closed!");
            }
            System.out.println("all closed!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return JFrame.EXIT_ON_CLOSE;
    }

    private void button1ActionPerformed(ActionEvent e) {
        try {
            String query = " INSERT INTO –‡‰≥ÓÌÛÍÎ≥‰ (`Õ‡Á‚‡`, `—ÍÓÓ˜ÂÌÓ`, `ÕÓÏÂ ÔÓˇ‰ÍÛ`, `¿ÍÚË‚Ì≥ÒÚ¸ ÌÛÍÎ≥‰‡`)"
                    + " VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setString(1, textField1.getText());
            preparedStmt.setString(2, textField2.getText());
            preparedStmt.setInt(3, Integer.parseInt(textField3.getText()));
            preparedStmt.setInt(4, Integer.parseInt(textField4.getText()));
            preparedStmt.execute();
            preparedStmt.close();
            this.setVisible(false);
            //close();
        } catch (Exception exeption) {
            exeption.printStackTrace();
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Œÿ»¡ ¿!", JOptionPane.ERROR_MESSAGE);
            //close();
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JTextField textField4;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        button1 = new JButton();

        //======== this ========
        setVisible(true);
        setTitle("\u0414\u043e\u0434\u0430\u0432\u0430\u043d\u043d\u044f \u043f\u043e\u043b\u044f \u0434\u043e \u0442\u0430\u0431\u043b\u0438\u0446\u0456 \u0420\u0430\u0434\u0456\u043e\u043d\u0443\u043a\u043b\u0456\u0434");
        setAlwaysOnTop(true);
        setFont(new Font("Verdana", Font.PLAIN, 12));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "min:grow",
            "fill:default:grow"));

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.setLayout(new FormLayout(
                "default, $lcgap, default:grow",
                "4*(fill:15dlu:grow, $lgap), fill:20dlu:grow"));

            //---- label1 ----
            label1.setText("\u041d\u0430\u0437\u0432\u0430");
            label1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label1, CC.xy(1, 1));

            //---- textField1 ----
            textField1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(textField1, CC.xy(3, 1));

            //---- label2 ----
            label2.setText("\u0421\u043a\u043e\u0440\u043e\u0447\u0435\u043d\u043e");
            label2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label2, CC.xy(1, 3));

            //---- textField2 ----
            textField2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(textField2, CC.xy(3, 3));

            //---- label3 ----
            label3.setText("\u041d\u043e\u043c\u0435\u0440 \u043f\u043e\u0440\u044f\u0434\u043a\u0443");
            label3.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label3, CC.xy(1, 5));

            //---- textField3 ----
            textField3.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(textField3, CC.xy(3, 5));

            //---- label4 ----
            label4.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
            label4.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label4, CC.xy(1, 7));

            //---- textField4 ----
            textField4.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(textField4, CC.xy(3, 7));

            //---- button1 ----
            button1.setText("\u0414\u043e\u0434\u0430\u0442\u0438");
            button1.setFont(new Font("Verdana", Font.PLAIN, 12));
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1ActionPerformed(e);
                }
            });
            panel1.add(button1, CC.xywh(1, 9, 3, 1));
        }
        contentPane.add(panel1, CC.xy(1, 1));
        setSize(405, 200);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
