/*
 * Created by JFormDesigner on Sun Nov 30 17:12:18 EET 2014
 */

package ГЛАВНОЕ_ОКНО.РРВ;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;

public class insertIntoRRV extends JFrame {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public insertIntoRRV() {
        initComponents();
        getNuklid();
        formattedTextField1.addKeyListener(new KeyAdapterNumbersOnly());
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

    private void getNuklid() {
        try {
            open();

            resultSet = statement
                    .executeQuery("SELECT * FROM радіонуклід");
            while (resultSet.next()) {
                String str = resultSet.getString(2) + " [ " + resultSet.getString(3) + ", " + resultSet.getString(4) + " ]";
                comboBox2.addItem(str);
            }
            close();
        } catch (Exception exeption) {
        }
    }

    private void button1ActionPerformed(ActionEvent e) {
        try {

            Date date;

            if (dateChooser1.getDate().compareTo(Calendar.getInstance().getTime()) <= 0)
                date = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser1.getDate()));
            else
                throw new DateTimeException("\nНельзя путишествовать в будущее!\nДата добавления рад. отхода должна быть объявленна в прошлом!");

            open();

            // comboBox1.getSelectedIndex()
            // comboBox2.getSelectedIndex()
            // textField1.getText()
            // date
            // textPane1.getText()

            resultSet = statement
                    .executeQuery("SELECT * FROM радіонуклід WHERE `Номер` = " + (1 + comboBox2.getSelectedIndex()));
            resultSet.next();
            int act = resultSet.getInt(5);
            int nukl = (1 + comboBox2.getSelectedIndex());
            int zagAct = act * Integer.parseInt(formattedTextField1.getText());
            int cat = 3;
            if (zagAct < 1000000) cat = 2;
            if (zagAct < 10000) cat = 1;

            String query = " INSERT INTO РРВ (`Категорія активності`, `Кількість`, `Загальна активність`, `Нуклід`, `Активність нукліда`, `Дата виготовлення`, `Примітки`)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(2, Integer.parseInt(formattedTextField1.getText()));
            preparedStmt.setInt(1, cat);
            preparedStmt.setInt(3, zagAct);
            preparedStmt.setInt(4, nukl);
            preparedStmt.setInt(5, act);
            preparedStmt.setDate(6, date);
            preparedStmt.setString(7, textPane1.getText());
            preparedStmt.execute();
            preparedStmt.close();
            this.setVisible(false);
            //close();
        } catch (Exception exeption) {
            exeption.printStackTrace();
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "ОШИБКА!", JOptionPane.ERROR_MESSAGE);
            //close();
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label2;
    private JComboBox comboBox2;
    private JLabel label3;
    private JFormattedTextField formattedTextField1;
    private JLabel label4;
    private JDateChooser dateChooser1;
    private JLabel label5;
    private JScrollPane scrollPane1;
    private JTextPane textPane1;
    private JButton button1;
// JFormDesigner - End of variables declaration  //GEN-END:variables

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label2 = new JLabel();
        comboBox2 = new JComboBox();
        label3 = new JLabel();
        formattedTextField1 = new JFormattedTextField();
        label4 = new JLabel();
        dateChooser1 = new JDateChooser();
        label5 = new JLabel();
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();
        button1 = new JButton();

        //======== this ========
        setVisible(true);
        setTitle("\u0414\u043e\u0434\u0430\u0432\u0430\u043d\u043d\u044f \u043f\u043e\u043b\u044f \u0434\u043e \u0442\u0430\u0431\u043b\u0438\u0446\u0456 \u0420\u0420\u0412");
        setAlwaysOnTop(true);
        setFont(new Font("Verdana", Font.PLAIN, 12));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
                "min:grow",
                "default:grow"));

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.setLayout(new FormLayout(
                    "default:grow, $lcgap, default:grow",
                    "5*(fill:15dlu:grow, $lgap), fill:20dlu:grow"));

            //---- label2 ----
            label2.setText("\u0420\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u043d\u0443\u043a\u043b\u0456\u0434");
            label2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label2, CC.xy(1, 1));

            //---- comboBox2 ----
            comboBox2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(comboBox2, CC.xy(3, 1));

            //---- label3 ----
            label3.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c \u0432\u0456\u0434\u0445\u043e\u0434\u0456\u0432");
            label3.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label3, CC.xy(1, 3));

            //---- formattedTextField1 ----
            formattedTextField1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(formattedTextField1, CC.xy(3, 3));

            //---- label4 ----
            label4.setText("\u0414\u0430\u0442\u0430 \u0432\u0438\u0433\u043e\u0442\u043e\u0432\u043b\u0435\u043d\u043d\u044f");
            label4.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label4, CC.xy(1, 5));

            //---- dateChooser1 ----
            dateChooser1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(dateChooser1, CC.xy(3, 5));

            //---- label5 ----
            label5.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
            label5.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label5, CC.xy(1, 7));

            //======== scrollPane1 ========
            {
                scrollPane1.setFont(new Font("Verdana", Font.PLAIN, 12));

                //---- textPane1 ----
                textPane1.setFont(new Font("Verdana", Font.PLAIN, 12));
                scrollPane1.setViewportView(textPane1);
            }
            panel1.add(scrollPane1, CC.xywh(3, 7, 1, 3));

            //---- button1 ----
            button1.setText("\u0414\u043e\u0434\u0430\u0442\u0438");
            button1.setFont(new Font("Verdana", Font.PLAIN, 12));
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1ActionPerformed(e);
                }
            });
            panel1.add(button1, CC.xywh(1, 11, 3, 1));
        }
        contentPane.add(panel1, CC.xy(1, 1));
        setSize(405, 225);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public class KeyAdapterNumbersOnly extends KeyAdapter {

        private String allowedRegex = "[^0-9]";

        public void keyReleased(KeyEvent e) {
            String curText = ((JTextComponent) e.getSource()).getText();
            curText = curText.replaceAll(allowedRegex, "");

            ((JTextComponent) e.getSource()).setText(curText);
        }
    }

}
