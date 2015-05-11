/*
 * Created by JFormDesigner on Sun Dec 14 00:01:55 EET 2014
 */

package ГЛАВНОЕ_ОКНО.ТРВ;

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

/**
 * @author Igor
 */
public class updateInTRV extends JFrame {

    int Nomer;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public updateInTRV(int nomer) {
        initComponents();
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectRow(nomer);
        button1.setText(button1.getText() + nomer);
        formattedTextField1.addKeyListener(new KeyAdapterNumbersOnly());
        Nomer = nomer;
    }

    private void selectRow(int nomer) {
        try {
            String query = "SELECT * FROM ТРВ WHERE Номер = " + nomer;
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            int Kat = 0;
            int Nukl = 0;
            while (resultSet.next()) {
                Kat = resultSet.getInt("Категорія активності") - 1;
                Nukl = resultSet.getInt("Нуклід") - 1;
                formattedTextField1.setText(String.valueOf(resultSet.getInt("Кількість")));
                formattedTextField2.setText(String.valueOf(resultSet.getInt("Активність нукліда")));
                formattedTextField3.setText(String.valueOf(resultSet.getInt("Загальна активність")));
                textPane1.setText(resultSet.getString("Примітки"));
                dateChooser1.setDate(resultSet.getDate("Дата виготовлення"));
            }
            getKategory(Kat);
            getNuklid(Nukl);
        } catch (Exception e) {
        }

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

    private void getKategory(int Kat) {
        try {
            //open();

            resultSet = statement
                    .executeQuery("SELECT * FROM `категорія активності`");
            while (resultSet.next()) {
                comboBox1.addItem(resultSet.getString(2));
            }
            comboBox1.setSelectedIndex(Kat);
            //close();
        } catch (Exception exeption) {
        }
    }

    private void getNuklid(int Nukl) {
        try {
            //open();

            resultSet = statement
                    .executeQuery("SELECT * FROM радіонуклід");
            while (resultSet.next()) {
                String str = resultSet.getString(2) + " [ " + resultSet.getString(3) + ", " + resultSet.getString(4) + " ]";
                comboBox2.addItem(str);
            }
            comboBox2.setSelectedIndex(Nukl);
            //close();
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

            //open();

            // comboBox1.getSelectedIndex()
            // comboBox2.getSelectedIndex()
            // textField1.getText()
            // date
            // textPane1.getText()

            String query = " UPDATE ТРВ SET `Категорія активності` = ?, `Нуклід` = ?, `Кількість` = ?, `Активність нукліда` = ?, `Загальна активність` = ?, `Дата виготовлення` = ?, `Примітки` = ? WHERE `Номер` = ?";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, (comboBox1.getSelectedIndex() + 1));
            preparedStmt.setInt(2, (1 + comboBox2.getSelectedIndex()));
            preparedStmt.setInt(3, Integer.parseInt(formattedTextField1.getText()));
            preparedStmt.setInt(4, Integer.parseInt(formattedTextField2.getText()));
            preparedStmt.setInt(5, Integer.parseInt(formattedTextField3.getText()));
            preparedStmt.setDate(6, date);
            preparedStmt.setString(7, textPane1.getText());
            preparedStmt.setInt(8, Nomer);
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
    private JLabel label1;
    private JComboBox comboBox1;
    private JLabel label2;
    private JComboBox comboBox2;
    private JLabel label3;
    private JFormattedTextField formattedTextField1;
    private JLabel label6;
    private JFormattedTextField formattedTextField2;
    private JLabel label7;
    private JFormattedTextField formattedTextField3;
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
        label1 = new JLabel();
        comboBox1 = new JComboBox();
        label2 = new JLabel();
        comboBox2 = new JComboBox();
        label3 = new JLabel();
        formattedTextField1 = new JFormattedTextField();
        label6 = new JLabel();
        formattedTextField2 = new JFormattedTextField();
        label7 = new JLabel();
        formattedTextField3 = new JFormattedTextField();
        label4 = new JLabel();
        dateChooser1 = new JDateChooser();
        label5 = new JLabel();
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();
        button1 = new JButton();

        //======== this ========
        setVisible(true);
        setTitle("\u041e\u043d\u043e\u0432\u043b\u0435\u043d\u043d\u044f \u0437\u0430\u043f\u0438\u0441\u0443 \u0432 \u0442\u0430\u0431\u043b\u0438\u0446\u0456 \u0411\u0420\u0412");
        setMinimumSize(new Dimension(440, 260));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
                "default:grow",
                "fill:default:grow"));

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.setLayout(new FormLayout(
                    "default:grow, $lcgap, default:grow",
                    "3*(fill:15dlu:grow, $lgap), 2*(default, $lgap), 3*(fill:15dlu:grow, $lgap), fill:20dlu:grow"));

            //---- label1 ----
            label1.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456");
            label1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label1, CC.xy(1, 1));

            //---- comboBox1 ----
            comboBox1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(comboBox1, CC.xy(3, 1));

            //---- label2 ----
            label2.setText("\u0420\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u043d\u0443\u043a\u043b\u0456\u0434");
            label2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label2, CC.xy(1, 3));

            //---- comboBox2 ----
            comboBox2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(comboBox2, CC.xy(3, 3));

            //---- label3 ----
            label3.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c \u0432\u0456\u0434\u0445\u043e\u0434\u0456\u0432");
            label3.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label3, CC.xy(1, 5));

            //---- formattedTextField1 ----
            formattedTextField1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(formattedTextField1, CC.xy(3, 5));

            //---- label6 ----
            label6.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
            label6.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label6, CC.xy(1, 7));

            //---- formattedTextField2 ----
            formattedTextField2.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(formattedTextField2, CC.xy(3, 7));

            //---- label7 ----
            label7.setText("\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c");
            label7.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label7, CC.xy(1, 9));

            //---- formattedTextField3 ----
            formattedTextField3.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(formattedTextField3, CC.xy(3, 9));

            //---- label4 ----
            label4.setText("\u0414\u0430\u0442\u0430 \u0432\u0438\u0433\u043e\u0442\u043e\u0432\u043b\u0435\u043d\u043d\u044f");
            label4.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label4, CC.xy(1, 11));

            //---- dateChooser1 ----
            dateChooser1.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(dateChooser1, CC.xy(3, 11));

            //---- label5 ----
            label5.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
            label5.setFont(new Font("Verdana", Font.PLAIN, 12));
            panel1.add(label5, CC.xy(1, 13));

            //======== scrollPane1 ========
            {
                scrollPane1.setFont(new Font("Verdana", Font.PLAIN, 12));

                //---- textPane1 ----
                textPane1.setFont(new Font("Verdana", Font.PLAIN, 12));
                scrollPane1.setViewportView(textPane1);
            }
            panel1.add(scrollPane1, CC.xywh(3, 13, 1, 3));

            //---- button1 ----
            button1.setText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0437\u0430\u043f\u0438\u0441 \u0437 \u041d\u043e\u043c\u0435\u0440\u043e\u043c = ");
            button1.setFont(new Font("Verdana", Font.PLAIN, 12));
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1ActionPerformed(e);
                }
            });
            panel1.add(button1, CC.xywh(1, 17, 3, 1));
        }
        contentPane.add(panel1, CC.xy(1, 1));
        pack();
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
