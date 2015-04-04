/*
 * Created by JFormDesigner on Mon Mar 23 09:24:15 EET 2015
 */

package ГЛАВНОЕ_ОКНО;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Ololo
 */
public class login extends JFrame {


    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    mainFrame M;

    public login(mainFrame M) {
        initComponents();
        this.M = M;
    }



    private int open() {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "oblik_rao";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "123456";
        try {
            Class.forName(driver).newInstance();
            connect = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("Connection opened!");
            statement = connect.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);

        }

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
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
        return JFrame.EXIT_ON_CLOSE;
    }

    private void button1ActionPerformed(ActionEvent e){
        open();
        int group = 0;
        try {
            String que = "SELECT `Користувач`.`Група` FROM `Користувач` WHERE `Користувач`.`Ім'я` = \"" + textField1.getText() + "\"AND `Користувач`.`Пароль` = \"" + textField2.getText() + "\";";
            resultSet = statement.executeQuery(que);
            while (resultSet.next())
                group = resultSet.getInt("Група");
        } catch (SQLException e1){
            JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        try {
            if(group == 0) {throw new RuntimeException("Нет такого пользователя");}
            else
            {
                this.dispose();
                M.userGroup = group;
                M.userName = textField1.getText();
                textField1.setText("");
                textField2.setText("");
                M.setProperties();
                M.enable(true);
            }

        } catch (Exception e1) {

            JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        close();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        button1 = new JButton();

        //======== this ========
        setTitle("\u0412\u0445\u0456\u0434 \u0432 \u0441\u0438\u0441\u0442\u0435\u043c\u0443");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "default, $lcgap, 54dlu, $lcgap, 109dlu, $lcgap, default",
            "4*(default, $lgap), default"));

        //---- label1 ----
        label1.setText("\u041b\u043e\u0433\u0456\u043d");
        contentPane.add(label1, CC.xy(3, 3));
        contentPane.add(textField1, CC.xy(5, 3));

        //---- label2 ----
        label2.setText("\u041f\u0430\u0440\u043e\u043b\u044c");
        contentPane.add(label2, CC.xy(3, 5));
        contentPane.add(textField2, CC.xy(5, 5));

        //---- button1 ----
        button1.setText("\u0412\u0445\u043e\u0434");
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1, CC.xywh(3, 7, 3, 1));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
