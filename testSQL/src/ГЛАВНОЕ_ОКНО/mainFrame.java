package ГЛАВНОЕ_ОКНО;

import java.awt.event.*;
import javax.swing.border.*;
import com.ibatis.common.jdbc.ScriptRunner;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.proteanit.sql.DbUtils;
import ГЛАВНОЕ_ОКНО.БРВ.insertIntoBRV;
import ГЛАВНОЕ_ОКНО.БРВ.updateInBRV;
import ГЛАВНОЕ_ОКНО.ДІВ.insertIntoDIV;
import ГЛАВНОЕ_ОКНО.ДІВ.updateInDIV;
import ГЛАВНОЕ_ОКНО.ЗАВОД.insertIntoZavod;
import ГЛАВНОЕ_ОКНО.ЗАВОД.updateInZavod;
import ГЛАВНОЕ_ОКНО.РАДІОАКТИВНІ_НУКЛІДИ.insertIntoRadio;
import ГЛАВНОЕ_ОКНО.РАДІОАКТИВНІ_НУКЛІДИ.updateInRadio;
import ГЛАВНОЕ_ОКНО.РРВ.insertIntoRRV;
import ГЛАВНОЕ_ОКНО.РРВ.updateInRRV;
import ГЛАВНОЕ_ОКНО.ТРВ.insertIntoTRV;
import ГЛАВНОЕ_ОКНО.ТРВ.updateInTRV;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class mainFrame extends JFrame {

    /*String IP = "";
     try {
     IP = InetAddress.getLocalHost().getHostAddress();
     }
     catch (Exception e) {
     IP = "Error finding IP";
     }*/

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    ArrayList<JButton> buttons = new ArrayList<>();
    public int userGroup;
    public String userName;
    private login log;

    public mainFrame() throws Exception {
        initComponents();
        open();
        updateComboBox(new ActionEvent(comboBox1, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableTRV(new ActionEvent(button5, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableRRV(new ActionEvent(button14, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableDIV(new ActionEvent(button18, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableZAVOD(new ActionEvent(button23, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableBRV(new ActionEvent(button28, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableRADIO(new ActionEvent(button33, ActionEvent.ACTION_PERFORMED, ""));
        refreshTableUSER(new ActionEvent(button9, ActionEvent.ACTION_PERFORMED, ""));
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button4);
        buttons.add(button10);
        buttons.add(button11);
        buttons.add(button12);
        buttons.add(button15);
        buttons.add(button16);
        buttons.add(button17);
        buttons.add(button20);
        buttons.add(button21);
        buttons.add(button22);
        buttons.add(button25);
        buttons.add(button26);
        buttons.add(button27);
        buttons.add(button30);
        buttons.add(button31);
        buttons.add(button32);
        log = new login(this);
        this.enable(false);
    }

    public void setProperties(){
        if(userGroup == 1){
            menuItem2.setVisible(true);
            for (JButton but : buttons)
                but.setVisible(true);
            if(tabbedPane1.getTabCount() == 6)
                tabbedPane1.addTab("Користувачі", panel43);
            panel43.setVisible(true);
        }
        if(userGroup == 2)
        {
            menuItem2.setVisible(false);
            for (JButton but : buttons)
                but.setVisible(true);
            if(tabbedPane1.getTabCount() == 7)
                tabbedPane1.remove(6);
            panel43.setVisible(false);
        }
        if(userGroup == 3)
        {
            menuItem2.setVisible(false);
            for (JButton but : buttons)
                but.setVisible(false);
            if(tabbedPane1.getTabCount() == 7)
                tabbedPane1.remove(6);
        }
        tabbedPane1.revalidate();
        tabbedPane1.repaint();
        JOptionPane.showMessageDialog(null, userName, "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    private void truncateTable(String string) {
        try {
            String query = "truncate " + string;
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.execute();
            preparedStmt.close();
            JOptionPane.showMessageDialog(null, "Дані з таблиці " + string + " успішно видалені!", "УСПІХ!", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, e1.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        try {
            ScriptRunner runner = new ScriptRunner(connect, false, false);
            runner.runScript(new BufferedReader(new FileReader("script/insertЗАВОД.sql")));
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, e1.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void menuItem1ActionPerformed(ActionEvent e) {
        try {
            String FILE = "Таблиці радіоактивних відходів.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));

            document.open();

            document.addTitle("Таблиці радіоактивних відходів");
            document.addSubject("Звіт");
            document.addKeywords("завод, ТРВ, БРВ, РРВ, ДІВ, радіоактивність, радіація");
            document.addAuthor("Соколюк Ігор");
            document.addCreator("Студент групи ТІ-21, 3 курс");

            addTable(document, табл0.getModel(), "Завод");
            addTable(document, табл1.getModel(), "ТРВ");
            addTable(document, табл2.getModel(), "РРВ");
            addTable(document, табл3.getModel(), "БРВ");
            addTable(document, табл4.getModel(), "ДІВ");
            addTable(document, табл5.getModel(), "Радіонукліди");

            document.close();
            JOptionPane.showMessageDialog(null, "Успішно експортовано в PDF-файл", "Успіх!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addTable(Document document, TableModel table, String name) throws Exception {
        Paragraph enter1 = new Paragraph();
        Paragraph enter2 = new Paragraph();
        addEmptyLine(enter1, 2);
        addEmptyLine(enter2, 1);
        BaseFont bf = BaseFont.createFont("fonts\\verdanab.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Paragraph title = new Paragraph(new Phrase(name, new com.lowagie.text.Font(bf, 10)));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(enter2);
        document.add(createTable(table));
        document.add(enter1);

    }

    private Paragraph createTable(TableModel table1) throws Exception {
        PdfPTable table = new PdfPTable(table1.getColumnCount());
        Paragraph par = new Paragraph();

        PdfPCell c1;
        BaseFont bf1 = BaseFont.createFont("fonts\\verdana.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bf2 = BaseFont.createFont("fonts\\verdanab.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            c1 = new PdfPCell(new Phrase(table1.getColumnName(i), new com.lowagie.text.Font(bf2, 7)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(java.awt.Color.lightGray);
            table.addCell(c1);
        }
        table.setHeaderRows(1);
        for (int j = 0; j < table1.getRowCount(); j++) {
            for (int i = 0; i < table1.getColumnCount(); i++) {
                table.addCell(new Phrase(String.valueOf(table1.getValueAt(j, i)), new com.lowagie.text.Font(bf1, 7)));
            }
        }

        par.add(table);
        return par;
    }

    private void updateInZAVOD(ActionEvent e) {
        if (табл0.getSelectedRowCount() == 1)
            new updateInZavod(Integer.parseInt(табл0.getValueAt(табл0.getSelectedRow(), 0).toString()));
        else
            JOptionPane.showMessageDialog(null, "Потрібно обрати один запис з таблиці", "Помилка!", JOptionPane.ERROR_MESSAGE);
    }

    private void searchCleanerZAVOD(ActionEvent e) {
        checkBox1.setSelected(true);
        checkBox2.setSelected(true);
        checkBox3.setSelected(true);
        checkBox4.setSelected(true);
        checkBox5.setSelected(true);
        checkBox6.setSelected(true);
        checkBox16.setSelected(false);
        firstNumber1.setText("");
        firstNumber2.setText("");
        lastNumber1.setText("");
        lastNumber2.setText("");
        comboBox1.setSelectedIndex(0);
        comboBox2.setSelectedIndex(0);
        comboBox3.setSelectedIndex(0);
        dateChooser1.setDate(null);
        dateChooser2.setDate(null);
    }

    private void deleteFromZAVOD(ActionEvent e) {
        try {
            int[] i = табл0.getSelectedRows();
            String que;
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM Завод WHERE Номер = " + Integer.parseInt(табл0.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            refreshTableZAVOD(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTableZAVOD(ActionEvent e) {
        try {
            String que = "SELECT ";
            if (checkBox1.isSelected())
                que += "`Завод`.`Номер`, ";
            if (checkBox2.isSelected())
                que += "`Категорія відходу`.`Категорія відходу`, ";
            if (checkBox3.isSelected())
                que += "`Радіонуклід`.`Назва`, ";
            if (checkBox4.isSelected())
                que += "`Завод`.`Кількість`, ";
            if (checkBox5.isSelected())
                que += "`Завод`.`Дата виготовлення`, ";
            if (checkBox6.isSelected())
                que += "`Завод`.`Примітки`, ";
            que = que.substring(0, que.length() - 2);
            que += " FROM `Завод`\n";

            que += "INNER JOIN `Категорія відходу`\n" +
                    "ON `Завод`.`Категорія відходу` = `Категорія відходу`.`Номер`\n" +
                    "INNER JOIN `Радіонуклід`\n" +
                    "ON `Завод`.`Нуклід` = `Радіонуклід`.`Номер`\n";

            boolean where = false;

            if (!firstNumber1.getText().isEmpty()) {
                if (!lastNumber1.getText().isEmpty()) {
                    que += "WHERE `Завод`.`Номер` >= \"" + firstNumber1.getText() + "\"\n";
                    que += "AND ";
                    que += "`Завод`.`Номер` <= \"" + lastNumber1.getText() + "\"\n";
                } else
                    que += "WHERE `Завод`.`Номер` = \"" + firstNumber1.getText() + "\"\n";
                where = true;
            }

            if (comboBox1.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Категорія відходу`.`Категорія відходу` = \"" + comboBox1.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (comboBox2.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Завод`.`Нуклід` = \"" + comboBox2.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (!firstNumber2.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber2.getText().isEmpty()) {
                    que += "`Завод`.`Кількість` >= \"" + firstNumber2.getText() + "\"\n";
                    que += "AND ";
                    que += "`Завод`.`Кількість` <= \"" + lastNumber2.getText() + "\"\n";
                } else
                    que += "`Завод`.`Кількість` = \"" + firstNumber2.getText() + "\"\n";
                where = true;
            }

            Date date1;
            Date date2;

            if (dateChooser1.getDate() != null) {
                date1 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser1.getDate()));
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (dateChooser2.getDate() != null) {
                    date2 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser2.getDate()));
                    que += "`Завод`.`Дата виготовлення` >= \"" + date1 + "\"\n";
                    que += "AND ";
                    que += "`Завод`.`Дата виготовлення` <= \"" + date2 + "\"\n";
                } else
                    que += "`Завод`.`Дата виготовлення` = \"" + date1 + "\"\n";
            }

            que += "ORDER BY ";
            switch (comboBox3.getSelectedIndex()) {
                case 0:
                    que += " `Завод`.`Номер`";
                    break;
                case 1:
                    que += " `Категорія відходу`.`Категорія відходу`";
                    break;
                case 2:
                    que += " `Радіонуклід`.`Назва`";
                    break;
                case 3:
                    que += " `Завод`.`Кількість`";
                    break;
                case 4:
                    que += " `Завод`.`Дата виготовлення`";
                    break;
                case 5:
                    que += " `Завод`.`Примітки`";
                    break;
            }

            if (checkBox16.isSelected()) {
                que += " desc;";
            } else
                que += ";";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.табл0.setModel(model);
            refreshTableBRV(e);
            refreshTableRRV(e);
            refreshTableDIV(e);
            refreshTableTRV(e);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateComboBox(ActionEvent e) {
        try {
            resultSet = statement
                    .executeQuery("SELECT * FROM радіонуклід");
            comboBox2.addItem("-");
            comboBox5.addItem("-");
            comboBox8.addItem("-");
            comboBox11.addItem("-");
            comboBox14.addItem("-");
            while (resultSet.next()) {
                String str = resultSet.getString(2);
                comboBox2.addItem(str);
                comboBox5.addItem(str);
                comboBox8.addItem(str);
                comboBox11.addItem(str);
                comboBox14.addItem(str);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void menuItem4ActionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Ви впевенні, що бажаєте видалити всі дані з таблиці " + menuItem4.getText() + "?", "Увага!", JOptionPane.YES_NO_OPTION) == 0)
            truncateTable(menuItem4.getText());
    }

    private void menuItem5ActionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Ви впевенні, що бажаєте видалити всі дані з таблиці " + menuItem5.getText() + "?", "Увага!", JOptionPane.YES_NO_OPTION) == 0)
            truncateTable(menuItem5.getText());
    }

    private void menuItem6ActionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Ви впевенні, що бажаєте видалити всі дані з таблиці " + menuItem6.getText() + "?", "Увага!", JOptionPane.YES_NO_OPTION) == 0)
            truncateTable(menuItem6.getText());
    }

    private void menuItem7ActionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Ви впевенні, що бажаєте видалити всі дані з таблиці " + menuItem7.getText() + "?", "Увага!", JOptionPane.YES_NO_OPTION) == 0)
            truncateTable(menuItem7.getText());
    }

    private void menuItem8ActionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Ви впевенні, що бажаєте видалити всі дані з таблиці " + menuItem8.getText() + "?", "Увага!", JOptionPane.YES_NO_OPTION) == 0)
            truncateTable(menuItem8.getText());
    }

    private void insertIntoTRV(ActionEvent e) {
        new insertIntoTRV();
    }

    private void refreshTableTRV(ActionEvent e) {
        try {
            //open();
            String que = "SELECT ";
            if (checkBox7.isSelected())
                que += "`ТРВ`.`Номер`, ";
            if (checkBox8.isSelected())
                que += "`Категорія активності`.`Категорія активності`, ";
            if (checkBox9.isSelected())
                que += "`Радіонуклід`.`Назва`, ";
            if (checkBox10.isSelected())
                que += "`ТРВ`.`Кількість`, ";
            if (checkBox11.isSelected())
                que += " `Радіонуклід`.`Активність нукліда`, ";
            if (checkBox12.isSelected())
                que += "`ТРВ`.`Загальна активність`, ";
            if (checkBox13.isSelected())
                que += "`ТРВ`.`Дата виготовлення`, ";
            if (checkBox14.isSelected())
                que += "`ТРВ`.`Примітки`, ";
            que = que.substring(0, que.length() - 2);
            que += " FROM `ТРВ`\n";

            que += "inner join `Категорія активності`\n" +
                    "on `ТРВ`.`Категорія активності` = `Категорія активності`.`Номер`\n" +
                    "inner join `Радіонуклід`\n" +
                    "on `ТРВ`.`Нуклід` = `Радіонуклід`.`Номер`\n";

            boolean where = false;
            if (!firstNumber3.getText().isEmpty()) {
                if (!lastNumber3.getText().isEmpty()) {
                    que += "WHERE `ТРВ`.`Номер` >= \"" + firstNumber3.getText() + "\"\n";
                    que += "AND ";
                    que += "`ТРВ`.`Номер` <= \"" + lastNumber3.getText() + "\"\n";
                } else
                    que += "ТРВ `Завод`.`Номер` = \"" + firstNumber3.getText() + "\"\n";
                where = true;
            }

            if (comboBox4.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Категорія активності`.`Категорія активності` = \"" + comboBox4.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (comboBox5.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Радіонуклід`.`Назва` = \"" + comboBox5.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (!firstNumber4.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber4.getText().isEmpty()) {
                    que += "`ТРВ`.`Кількість` >= \"" + firstNumber4.getText() + "\"\n";
                    que += "AND ";
                    que += "`ТРВ`.`Кількість` <= \"" + lastNumber4.getText() + "\"\n";
                } else
                    que += "`ТРВ`.`Кількість` = \"" + firstNumber4.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber5.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber5.getText().isEmpty()) {
                    que += "`Радіонуклід`.`Активність нукліда` >= \"" + firstNumber5.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Активність нукліда` <= \"" + lastNumber5.getText() + "\"\n";
                } else
                    que += "`Радіонуклід`.`Активність нукліда` = \"" + firstNumber5.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber6.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber6.getText().isEmpty()) {
                    que += "`ТРВ`.`Загальна активність` >= \"" + firstNumber6.getText() + "\"\n";
                    que += "AND ";
                    que += "`ТРВ`.`Загальна активність` <= \"" + lastNumber6.getText() + "\"\n";
                } else
                    que += "`ТРВ`.`Загальна активність` = \"" + firstNumber6.getText() + "\"\n";
                where = true;
            }


            Date date1;
            Date date2;

            if (dateChooser3.getDate() != null) {
                date1 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser3.getDate()));
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (dateChooser4.getDate() != null) {
                    date2 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser4.getDate()));
                    que += "`ТРВ`.`Дата виготовлення` >= \"" + date1 + "\"\n";
                    que += "AND ";
                    que += "`ТРВ`.`Дата виготовлення` <= \"" + date2 + "\"\n";
                } else
                    que += "`ТРВ`.`Дата виготовлення` = \"" + date1 + "\"\n";
            }

            que += "ORDER BY ";
            switch (comboBox6.getSelectedIndex()) {
                case 0:
                    que += " `ТРВ`.`Номер`";
                    break;
                case 1:
                    que += " `Категорія активності`.`Категорія активності`";
                    break;
                case 2:
                    que += " `Радіонуклід`.`Назва`";
                    break;
                case 3:
                    que += " `ТРВ`.`Кількість`";
                    break;
                case 4:
                    que += " `Радіонуклід`.`Активність нукліда`";
                    break;
                case 5:
                    que += " `ТРВ`.`Загальна активність`";
                    break;
                case 6:
                    que += " `ТРВ`.`Дата виготовлення`";
                    break;
                case 7:
                    que += " `ТРВ`.`Примітки`";
                    break;
            }

            if (checkBox15.isSelected()) {
                que += " desc;";
            } else
                que += ";";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.табл1.setModel(model);
            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            //close();
        } catch (Exception exeption) {
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFromTRV(ActionEvent e) {
        try {
            int[] i = табл1.getSelectedRows();
            String que;
            //open();
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM ТРВ WHERE Номер = " + Integer.parseInt(табл1.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            //close();
            refreshTableTRV(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void updateInTRV(ActionEvent e) {
        if (табл1.getSelectedRowCount() == 1)
            new updateInTRV(Integer.parseInt(табл1.getValueAt(табл1.getSelectedRow(), 0).toString()));
        else
            JOptionPane.showMessageDialog(null, "Потрібно обрати один запис з таблиці", "Помилка!", JOptionPane.ERROR_MESSAGE);
    }

    private void searchCleanerTRV(ActionEvent e) {
        checkBox7.setSelected(true);
        checkBox8.setSelected(true);
        checkBox9.setSelected(true);
        checkBox10.setSelected(true);
        checkBox11.setSelected(true);
        checkBox12.setSelected(true);
        checkBox13.setSelected(true);
        checkBox14.setSelected(true);
        checkBox15.setSelected(false);
        firstNumber3.setText("");
        firstNumber4.setText("");
        firstNumber5.setText("");
        firstNumber6.setText("");
        lastNumber3.setText("");
        lastNumber4.setText("");
        lastNumber5.setText("");
        lastNumber6.setText("");
        comboBox4.setSelectedIndex(0);
        comboBox5.setSelectedIndex(0);
        comboBox6.setSelectedIndex(0);
        dateChooser3.setDate(null);
        dateChooser4.setDate(null);
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
            JOptionPane.showMessageDialog(null, e.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);

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
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
        return JFrame.EXIT_ON_CLOSE;
    }

    private void menuItem3ActionPerformed(ActionEvent e) {
        try {

            WritableWorkbook workbook = Workbook.createWorkbook(new File("result.xls"));

            writeToExcel(workbook, табл0.getModel(), "Завод", 0);
            writeToExcel(workbook, табл1.getModel(), "ТРВ", 1);
            writeToExcel(workbook, табл2.getModel(), "РРВ", 2);
            writeToExcel(workbook, табл3.getModel(), "БРВ", 3);
            writeToExcel(workbook, табл4.getModel(), "ДІВ", 4);
            writeToExcel(workbook, табл5.getModel(), "Раіоактивні нукліди", 5);

            workbook.write();
            workbook.close();
            JOptionPane.showMessageDialog(null, "Успішно експортовано в Excel-файл", "Успіх!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void writeToExcel(WritableWorkbook workbook, TableModel model, String name, int num) throws Exception {
        WritableSheet sheet = workbook.createSheet(name, num);
        for (int i = 0; i < model.getColumnCount(); i++) {
            Label column = new Label(i, 0, model.getColumnName(i));
            sheet.addCell(column);
        }
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                Label row = new Label(j, i + 1,
                        model.getValueAt(i, j).toString());
                sheet.addCell(row);
            }
        }
    }

    private void deleteFromRRV(ActionEvent e) {
        try {
            int[] i = табл2.getSelectedRows();
            String que;
            //open();
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM РРВ WHERE Номер = " + Integer.parseInt(табл2.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            //close();
            refreshTableRRV(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void insertIntoRRV(ActionEvent e) {
        new insertIntoRRV();
    }

    private void updateInRRV(ActionEvent e) {
        if (табл2.getSelectedRowCount() == 1)
            new updateInRRV(Integer.parseInt(табл2.getValueAt(табл2.getSelectedRow(), 0).toString()));
        else
            JOptionPane.showMessageDialog(null, "Потрібно обрати один запис з таблиці", "Помилка!", JOptionPane.ERROR_MESSAGE);
    }

    private void refreshTableRRV(ActionEvent e) {
        try {
            //open();
            String que = "SELECT ";
            if (checkBox17.isSelected())
                que += "`РРВ`.`Номер`, ";
            if (checkBox18.isSelected())
                que += "`Категорія активності`.`Категорія активності`, ";
            if (checkBox19.isSelected())
                que += "`Радіонуклід`.`Назва`, ";
            if (checkBox20.isSelected())
                que += "`РРВ`.`Кількість`, ";
            if (checkBox21.isSelected())
                que += " `Радіонуклід`.`Активність нукліда`, ";
            if (checkBox22.isSelected())
                que += "`РРВ`.`Загальна активність`, ";
            if (checkBox23.isSelected())
                que += "`РРВ`.`Дата виготовлення`, ";
            if (checkBox24.isSelected())
                que += "`РРВ`.`Примітки`, ";
            que = que.substring(0, que.length() - 2);
            que += " FROM `РРВ`\n";

            que += "inner join `Категорія активності`\n" +
                    "on `РРВ`.`Категорія активності` = `Категорія активності`.`Номер`\n" +
                    "inner join `Радіонуклід`\n" +
                    "on `РРВ`.`Нуклід` = `Радіонуклід`.`Номер`\n";

            boolean where = false;
            if (!firstNumber7.getText().isEmpty()) {
                if (!lastNumber7.getText().isEmpty()) {
                    que += "WHERE `РРВ`.`Номер` >= \"" + firstNumber7.getText() + "\"\n";
                    que += "AND ";
                    que += "`РРВ`.`Номер` <= \"" + lastNumber7.getText() + "\"\n";
                } else
                    que += "РРВ `Завод`.`Номер` = \"" + firstNumber7.getText() + "\"\n";
                where = true;
            }

            if (comboBox7.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Категорія активності`.`Категорія активності` = \"" + comboBox7.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (comboBox8.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Радіонуклід`.`Назва` = \"" + comboBox8.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (!firstNumber8.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber8.getText().isEmpty()) {
                    que += "`РРВ`.`Кількість` >= \"" + firstNumber8.getText() + "\"\n";
                    que += "AND ";
                    que += "`РРВ`.`Кількість` <= \"" + lastNumber8.getText() + "\"\n";
                } else
                    que += "`РРВ`.`Кількість` = \"" + firstNumber8.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber9.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber9.getText().isEmpty()) {
                    que += "`Радіонуклід`.`Активність нукліда` >= \"" + firstNumber9.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Активність нукліда` <= \"" + lastNumber9.getText() + "\"\n";
                } else
                    que += "`Радіонуклід`.`Активність нукліда` = \"" + firstNumber9.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber10.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber10.getText().isEmpty()) {
                    que += "`РРВ`.`Загальна активність` >= \"" + firstNumber10.getText() + "\"\n";
                    que += "AND ";
                    que += "`РРВ`.`Загальна активність` <= \"" + lastNumber10.getText() + "\"\n";
                } else
                    que += "`РРВ`.`Загальна активність` = \"" + firstNumber10.getText() + "\"\n";
                where = true;
            }


            Date date1;
            Date date2;

            if (dateChooser5.getDate() != null) {
                date1 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser5.getDate()));
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (dateChooser6.getDate() != null) {
                    date2 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser6.getDate()));
                    que += "`РРВ`.`Дата виготовлення` >= \"" + date1 + "\"\n";
                    que += "AND ";
                    que += "`РРВ`.`Дата виготовлення` <= \"" + date2 + "\"\n";
                } else
                    que += "`РРВ`.`Дата виготовлення` = \"" + date1 + "\"\n";
            }

            que += "ORDER BY ";
            switch (comboBox9.getSelectedIndex()) {
                case 0:
                    que += " `РРВ`.`Номер`";
                    break;
                case 1:
                    que += " `Категорія активності`.`Категорія активності`";
                    break;
                case 2:
                    que += " `Радіонуклід`.`Назва`";
                    break;
                case 3:
                    que += " `РРВ`.`Кількість`";
                    break;
                case 4:
                    que += " `Радіонуклід`.`Активність нукліда`";
                    break;
                case 5:
                    que += " `РРВ`.`Загальна активність`";
                    break;
                case 6:
                    que += " `РРВ`.`Дата виготовлення`";
                    break;
                case 7:
                    que += " `РРВ`.`Примітки`";
                    break;
            }

            if (checkBox25.isSelected()) {
                que += " desc;";
            } else
                que += ";";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.табл2.setModel(model);
            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            //close();
        } catch (Exception exeption) {
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTableBRV(ActionEvent e) {
        try {
            //open();
            String que = "SELECT ";
            if (checkBox26.isSelected())
                que += "`БРВ`.`Номер`, ";
            if (checkBox27.isSelected())
                que += "`Категорія активності`.`Категорія активності`, ";
            if (checkBox28.isSelected())
                que += "`Радіонуклід`.`Назва`, ";
            if (checkBox29.isSelected())
                que += "`БРВ`.`Кількість`, ";
            if (checkBox30.isSelected())
                que += " `Радіонуклід`.`Активність нукліда`, ";
            if (checkBox31.isSelected())
                que += "`БРВ`.`Загальна активність`, ";
            if (checkBox32.isSelected())
                que += "`БРВ`.`Дата виготовлення`, ";
            if (checkBox33.isSelected())
                que += "`БРВ`.`Примітки`, ";
            que = que.substring(0, que.length() - 2);
            que += " FROM `БРВ`\n";

            que += "inner join `Категорія активності`\n" +
                    "on `БРВ`.`Категорія активності` = `Категорія активності`.`Номер`\n" +
                    "inner join `Радіонуклід`\n" +
                    "on `БРВ`.`Нуклід` = `Радіонуклід`.`Номер`\n";

            boolean where = false;
            if (!firstNumber11.getText().isEmpty()) {
                if (!lastNumber11.getText().isEmpty()) {
                    que += "WHERE `БРВ`.`Номер` >= \"" + firstNumber11.getText() + "\"\n";
                    que += "AND ";
                    que += "`БРВ`.`Номер` <= \"" + lastNumber11.getText() + "\"\n";
                } else
                    que += "БРВ `Завод`.`Номер` = \"" + firstNumber11.getText() + "\"\n";
                where = true;
            }

            if (comboBox10.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Категорія активності`.`Категорія активності` = \"" + comboBox10.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (comboBox11.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Радіонуклід`.`Назва` = \"" + comboBox11.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (!firstNumber12.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber12.getText().isEmpty()) {
                    que += "`БРВ`.`Кількість` >= \"" + firstNumber12.getText() + "\"\n";
                    que += "AND ";
                    que += "`БРВ`.`Кількість` <= \"" + lastNumber12.getText() + "\"\n";
                } else
                    que += "`БРВ`.`Кількість` = \"" + firstNumber12.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber13.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber13.getText().isEmpty()) {
                    que += "`Радіонуклід`.`Активність нукліда` >= \"" + firstNumber13.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Активність нукліда` <= \"" + lastNumber13.getText() + "\"\n";
                } else
                    que += "`Радіонуклід`.`Активність нукліда` = \"" + firstNumber13.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber14.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber14.getText().isEmpty()) {
                    que += "`БРВ`.`Загальна активність` >= \"" + firstNumber14.getText() + "\"\n";
                    que += "AND ";
                    que += "`БРВ`.`Загальна активність` <= \"" + lastNumber14.getText() + "\"\n";
                } else
                    que += "`БРВ`.`Загальна активність` = \"" + firstNumber14.getText() + "\"\n";
                where = true;
            }


            Date date1;
            Date date2;

            if (dateChooser7.getDate() != null) {
                date1 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser7.getDate()));
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (dateChooser8.getDate() != null) {
                    date2 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser8.getDate()));
                    que += "`БРВ`.`Дата виготовлення` >= \"" + date1 + "\"\n";
                    que += "AND ";
                    que += "`БРВ`.`Дата виготовлення` <= \"" + date2 + "\"\n";
                } else
                    que += "`БРВ`.`Дата виготовлення` = \"" + date1 + "\"\n";
            }

            que += "ORDER BY ";
            switch (comboBox12.getSelectedIndex()) {
                case 0:
                    que += " `БРВ`.`Номер`";
                    break;
                case 1:
                    que += " `Категорія активності`.`Категорія активності`";
                    break;
                case 2:
                    que += " `Радіонуклід`.`Назва`";
                    break;
                case 3:
                    que += " `БРВ`.`Кількість`";
                    break;
                case 4:
                    que += " `Радіонуклід`.`Активність нукліда`";
                    break;
                case 5:
                    que += " `БРВ`.`Загальна активність`";
                    break;
                case 6:
                    que += " `БРВ`.`Дата виготовлення`";
                    break;
                case 7:
                    que += " `БРВ`.`Примітки`";
                    break;
            }

            if (checkBox34.isSelected()) {
                que += " desc;";
            } else
                que += ";";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.табл3.setModel(model);
            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            //close();
        } catch (Exception exeption) {
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateInBRV(ActionEvent e) {
        if (табл3.getSelectedRowCount() == 1)
            new updateInBRV(Integer.parseInt(табл3.getValueAt(табл3.getSelectedRow(), 0).toString()));
        else
            JOptionPane.showMessageDialog(null, "Потрібно обрати один запис з таблиці", "Помилка!", JOptionPane.ERROR_MESSAGE);

    }

    private void deleteFromBRV(ActionEvent e) {
        try {
            int[] i = табл3.getSelectedRows();
            String que;
            //open();
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM БРВ WHERE Номер = " + Integer.parseInt(табл3.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            //close();
            refreshTableBRV(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void insertIntoBRV(ActionEvent e) {
        new insertIntoBRV();
    }

    private void insertIntoDIV(ActionEvent e) {
        new insertIntoDIV();
    }

    private void deleteFromDIV(ActionEvent e) {
        try {
            int[] i = табл4.getSelectedRows();
            String que;
            //open();
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM ТРВ WHERE Номер = " + Integer.parseInt(табл4.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            //close();
            refreshTableDIV(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void updateInDIV(ActionEvent e) {
        if (табл4.getSelectedRowCount() == 1)
            new updateInDIV(Integer.parseInt(табл4.getValueAt(табл4.getSelectedRow(), 0).toString()));
        else
            JOptionPane.showMessageDialog(null, "Потрібно обрати один запис з таблиці", "Помилка!", JOptionPane.ERROR_MESSAGE);

    }

    private void refreshTableDIV(ActionEvent e) {
        try {
            //open();
            String que = "SELECT ";
            if (checkBox35.isSelected())
                que += "`ДІВ`.`Номер`, ";
            if (checkBox36.isSelected())
                que += "`Категорія активності`.`Категорія активності`, ";
            if (checkBox37.isSelected())
                que += "`Радіонуклід`.`Назва`, ";
            if (checkBox38.isSelected())
                que += "`ДІВ`.`Кількість`, ";
            if (checkBox39.isSelected())
                que += " `Радіонуклід`.`Активність нукліда`, ";
            if (checkBox40.isSelected())
                que += "`ДІВ`.`Загальна активність`, ";
            if (checkBox41.isSelected())
                que += "`ДІВ`.`Дата виготовлення`, ";
            if (checkBox42.isSelected())
                que += "`ДІВ`.`Примітки`, ";
            que = que.substring(0, que.length() - 2);
            que += " FROM `ДІВ`\n";

            que += "inner join `Категорія активності`\n" +
                    "on `ДІВ`.`Категорія активності` = `Категорія активності`.`Номер`\n" +
                    "inner join `Радіонуклід`\n" +
                    "on `ДІВ`.`Нуклід` = `Радіонуклід`.`Номер`\n";

            boolean where = false;
            if (!firstNumber15.getText().isEmpty()) {
                if (!lastNumber15.getText().isEmpty()) {
                    que += "WHERE `ДІВ`.`Номер` >= \"" + firstNumber15.getText() + "\"\n";
                    que += "AND ";
                    que += "`ДІВ`.`Номер` <= \"" + lastNumber15.getText() + "\"\n";
                } else
                    que += "ДІВ `Завод`.`Номер` = \"" + firstNumber15.getText() + "\"\n";
                where = true;
            }

            if (comboBox13.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Категорія активності`.`Категорія активності` = \"" + comboBox13.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (comboBox14.getSelectedIndex() != 0) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Радіонуклід`.`Назва` = \"" + comboBox14.getSelectedItem().toString() + "\"\n";
                where = true;
            }

            if (!firstNumber16.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber16.getText().isEmpty()) {
                    que += "`ДІВ`.`Кількість` >= \"" + firstNumber16.getText() + "\"\n";
                    que += "AND ";
                    que += "`ДІВ`.`Кількість` <= \"" + lastNumber16.getText() + "\"\n";
                } else
                    que += "`ДІВ`.`Кількість` = \"" + firstNumber16.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber17.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber17.getText().isEmpty()) {
                    que += "`Радіонуклід`.`Активність нукліда` >= \"" + firstNumber17.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Активність нукліда` <= \"" + lastNumber17.getText() + "\"\n";
                } else
                    que += "`Радіонуклід`.`Активність нукліда` = \"" + firstNumber17.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber18.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber18.getText().isEmpty()) {
                    que += "`ДІВ`.`Загальна активність` >= \"" + firstNumber18.getText() + "\"\n";
                    que += "AND ";
                    que += "`ДІВ`.`Загальна активність` <= \"" + lastNumber18.getText() + "\"\n";
                } else
                    que += "`ДІВ`.`Загальна активність` = \"" + firstNumber18.getText() + "\"\n";
                where = true;
            }


            Date date1;
            Date date2;

            if (dateChooser9.getDate() != null) {
                date1 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser9.getDate()));
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (dateChooser10.getDate() != null) {
                    date2 = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser10.getDate()));
                    que += "`ДІВ`.`Дата виготовлення` >= \"" + date1 + "\"\n";
                    que += "AND ";
                    que += "`ДІВ`.`Дата виготовлення` <= \"" + date2 + "\"\n";
                } else
                    que += "`ДІВ`.`Дата виготовлення` = \"" + date1 + "\"\n";
            }

            que += "ORDER BY ";
            switch (comboBox15.getSelectedIndex()) {
                case 0:
                    que += " `ДІВ`.`Номер`";
                    break;
                case 1:
                    que += " `Категорія активності`.`Категорія активності`";
                    break;
                case 2:
                    que += " `Радіонуклід`.`Назва`";
                    break;
                case 3:
                    que += " `ДІВ`.`Кількість`";
                    break;
                case 4:
                    que += " `Радіонуклід`.`Активність нукліда`";
                    break;
                case 5:
                    que += " `ДІВ`.`Загальна активність`";
                    break;
                case 6:
                    que += " `ДІВ`.`Дата виготовлення`";
                    break;
                case 7:
                    que += " `ДІВ`.`Примітки`";
                    break;
            }

            if (checkBox43.isSelected()) {
                que += " desc;";
            } else
                que += ";";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.табл4.setModel(model);
            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            //close();
        } catch (Exception exeption) {
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTableRADIO(ActionEvent e) {
        try {
            //open();
            String que = "SELECT ";
            if (checkBox44.isSelected())
                que += "`Радіонуклід`.`Номер`, ";
            if (checkBox45.isSelected())
                que += "`Радіонуклід`.`Назва`, ";
            if (checkBox46.isSelected())
                que += "`Радіонуклід`.`Скорочено`, ";
            if (checkBox47.isSelected())
                que += "`Радіонуклід`.`Номер порядку`, ";
            if (checkBox48.isSelected())
                que += "`Радіонуклід`.`Активність нукліда`, ";
            que = que.substring(0, que.length() - 2);
            que += " FROM `Радіонуклід`\n";

            boolean where = false;
            if (!firstNumber19.getText().isEmpty()) {
                if (!lastNumber19.getText().isEmpty()) {
                    que += "WHERE `Радіонуклід`.`Номер` >= \"" + firstNumber19.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Номер` <= \"" + lastNumber19.getText() + "\"\n";
                } else
                    que += "WHERE `Радіонуклід`.`Номер` = \"" + firstNumber19.getText() + "\"\n";
                where = true;
            }


            if (!textField1.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Радіонуклід`.`Назва` = \"" + textField1.getText() + "\"\n";
                where = true;
            }

            if (!textField2.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                que += "`Радіонуклід`.`Скорочено` = \"" + textField2.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber20.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber20.getText().isEmpty()) {
                    que += "`Радіонуклід`.`Номер порядку` >= \"" + firstNumber20.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Номер порядку` <= \"" + lastNumber20.getText() + "\"\n";
                } else
                    que += "`Радіонуклід`.`Номер порядку` = \"" + firstNumber20.getText() + "\"\n";
                where = true;
            }

            if (!firstNumber21.getText().isEmpty()) {
                if (!where) que += "WHERE ";
                else que += "AND ";
                if (!lastNumber21.getText().isEmpty()) {
                    que += "`Радіонуклід`.`Активність нукліда` >= \"" + firstNumber21.getText() + "\"\n";
                    que += "AND ";
                    que += "`Радіонуклід`.`Активність нукліда` <= \"" + lastNumber21.getText() + "\"\n";
                } else
                    que += "`Радіонуклід`.`Активність нукліда` = \"" + firstNumber21.getText() + "\"\n";
            }

            que += "ORDER BY ";
            switch (comboBox18.getSelectedIndex()) {
                case 0:
                    que += " `Радіонуклід`.`Номер`";
                    break;
                case 1:
                    que += " `Радіонуклід`.`Назва`";
                    break;
                case 2:
                    que += " `Радіонуклід`.`Скорочено`";
                    break;
                case 3:
                    que += " `Радіонуклід`.`Номер порядку`";
                    break;
                case 4:
                    que += " `Радіонуклід`.`Активність нукліда`";
                    break;
            }

            if (checkBox52.isSelected()) {
                que += " desc;";
            } else
                que += ";";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.табл5.setModel(model);
            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            //close();
        } catch (Exception exeption) {
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFromRADIO(ActionEvent e) {
        try {
            int[] i = табл5.getSelectedRows();
            String que;
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM `Радіонуклід` WHERE Номер = " + Integer.parseInt(табл5.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            refreshTableRADIO(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void insertIntoRADIO(ActionEvent e) {
        new insertIntoRadio();
    }

    private void updateInRADIO(ActionEvent e) {
        if (табл5.getSelectedRowCount() == 1)
            new updateInRadio(Integer.parseInt(табл5.getValueAt(табл5.getSelectedRow(), 0).toString()));
        else
            JOptionPane.showMessageDialog(null, "Потрібно обрати один запис з таблиці", "Помилка!", JOptionPane.ERROR_MESSAGE);
    }

    private void searchCleanerRADIO(ActionEvent e) {
        checkBox44.setSelected(true);
        checkBox45.setSelected(true);
        checkBox46.setSelected(true);
        checkBox47.setSelected(true);
        checkBox48.setSelected(true);
        checkBox52.setSelected(false);
        firstNumber19.setText("");
        firstNumber20.setText("");
        firstNumber21.setText("");
        lastNumber19.setText("");
        lastNumber20.setText("");
        lastNumber21.setText("");
        textField1.setText("");
        textField2.setText("");
        comboBox18.setSelectedIndex(0);
    }

    private void searchCleanerRRV(ActionEvent e) {
        checkBox17.setSelected(true);
        checkBox18.setSelected(true);
        checkBox19.setSelected(true);
        checkBox20.setSelected(true);
        checkBox21.setSelected(true);
        checkBox22.setSelected(true);
        checkBox23.setSelected(true);
        checkBox24.setSelected(true);
        checkBox25.setSelected(false);
        firstNumber7.setText("");
        firstNumber8.setText("");
        firstNumber9.setText("");
        firstNumber10.setText("");
        lastNumber7.setText("");
        lastNumber8.setText("");
        lastNumber9.setText("");
        lastNumber10.setText("");
        comboBox7.setSelectedIndex(0);
        comboBox8.setSelectedIndex(0);
        comboBox9.setSelectedIndex(0);
        dateChooser5.setDate(null);
        dateChooser6.setDate(null);
    }

    private void searchCleanerBRV(ActionEvent e) {
        checkBox26.setSelected(true);
        checkBox27.setSelected(true);
        checkBox28.setSelected(true);
        checkBox29.setSelected(true);
        checkBox30.setSelected(true);
        checkBox31.setSelected(true);
        checkBox32.setSelected(true);
        checkBox33.setSelected(true);
        checkBox34.setSelected(true);
        firstNumber11.setText("");
        firstNumber12.setText("");
        firstNumber13.setText("");
        firstNumber14.setText("");
        lastNumber11.setText("");
        lastNumber12.setText("");
        lastNumber13.setText("");
        lastNumber14.setText("");
        comboBox10.setSelectedIndex(0);
        comboBox11.setSelectedIndex(0);
        comboBox12.setSelectedIndex(0);
        dateChooser7.setDate(null);
        dateChooser8.setDate(null);
    }

    private void searchCleanerDIV(ActionEvent e) {
        checkBox35.setSelected(true);
        checkBox36.setSelected(true);
        checkBox37.setSelected(true);
        checkBox38.setSelected(true);
        checkBox39.setSelected(true);
        checkBox40.setSelected(true);
        checkBox41.setSelected(true);
        checkBox42.setSelected(true);
        checkBox43.setSelected(false);
        firstNumber15.setText("");
        firstNumber16.setText("");
        firstNumber17.setText("");
        firstNumber18.setText("");
        lastNumber15.setText("");
        lastNumber16.setText("");
        lastNumber17.setText("");
        lastNumber18.setText("");
        comboBox13.setSelectedIndex(0);
        comboBox14.setSelectedIndex(0);
        comboBox15.setSelectedIndex(0);
        dateChooser9.setDate(null);
        dateChooser10.setDate(null);
    }

    private void textField1KeyTyped(KeyEvent e) {
        // TODO add your code here
    }

    private void textField1ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void menuItem9ActionPerformed(ActionEvent e) {
        log.setVisible(true);
        this.enable(false);
    }

    public static void main(String[] args) throws Exception {
        new mainFrame();
    }

    private void insertIntoZAVOD(ActionEvent e) {
        new insertIntoZavod();
    }

    private void addUSER(ActionEvent e) {
        try {
            String query = " INSERT INTO користувач (`Ім'я`, `Пароль`, `Група`)"
                    + " VALUES (?, ?, ?)";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setString(1, textField3.getText());
            preparedStmt.setString(2, textField4.getText());
            preparedStmt.setInt(3, (comboBox16.getSelectedIndex()+1));
            preparedStmt.execute();
            preparedStmt.close();
        } catch (Exception exeption) {
            exeption.printStackTrace();
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "ОШИБКА!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUSER(ActionEvent e) {
        try {
            int[] i = table1.getSelectedRows();
            String que;
            PreparedStatement prp;
            System.out.println(i.length);
            for (int I : i) {
                que = "delete FROM `користувач` WHERE Номер = " + Integer.parseInt(table1.getValueAt(I, 0).toString()) + ";\n";
                prp = connect.prepareStatement(que);
                prp.execute();
            }
            refreshTableUSER(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void updateUSER(ActionEvent e) {
        try {
            String query = " UPDATE користувач SET `Ім'я` = ?, `Пароль` = ?, `Група` = ? WHERE `Номер` = ?";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setString(1, textField3.getText());
            preparedStmt.setString(2, textField4.getText());
            preparedStmt.setInt(3, (comboBox16.getSelectedIndex() + 1));
            preparedStmt.setInt(4, Integer.parseInt(label75.getText()));
            preparedStmt.execute();
            preparedStmt.close();
            //close();
        } catch (Exception exeption) {
            exeption.printStackTrace();
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "ОШИБКА!", JOptionPane.ERROR_MESSAGE);
            //close();
        }
    }

    private void refreshTableUSER(ActionEvent e) {
        try {
            open();
            String que = "select * from oblik_rao.користувач;";

            System.out.println(que);
            resultSet = statement
                    .executeQuery(que);

            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            TableModel model = DbUtils.resultSetToTableModel(resultSet);
            this.table1.setModel(model);
            /*----------------------------------------------------------------------
            ----------------------- ОЧЕНЬ БЛЕАТЬ ВАЖНО!!!!! ------------------------
            /*--------------------------------------------------------------------*/
            //close();
        } catch (Exception exeption) {
            JOptionPane.showMessageDialog(null, exeption.fillInStackTrace(), "Помилка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void table1MouseClicked(MouseEvent e) {
        label75.setText(table1.getValueAt(table1.getSelectedRow(), 0).toString());
        textField3.setText(table1.getValueAt(table1.getSelectedRow(), 1).toString());
        textField4.setText(table1.getValueAt(table1.getSelectedRow(), 2).toString());
        comboBox16.setSelectedIndex(Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 3).toString())-1);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenuItem menuItem2;
    private JMenu menu4;
    private JMenuItem menuItem1;
    private JMenuItem menuItem3;
    private JMenu menu3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JMenuItem menuItem7;
    private JMenuItem menuItem8;
    private JMenuItem menuItem9;
    private JTabbedPane tabbedPane1;
    private JPanel ЗАВОД;
    private JScrollPane scrollPane1;
    private JTable табл0;
    private JPanel panel6;
    private JPanel panel8;
    private JButton button4;
    private JPanel panel9;
    private JButton button1;
    private JPanel panel7;
    private JButton button2;
    private JPanel panel2;
    private JButton button5;
    private JPanel panel3;
    private JLabel label2;
    private JPanel panel4;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JLabel label1;
    private JTextField firstNumber1;
    private JLabel label3;
    private JTextField lastNumber1;
    private JLabel label4;
    private JComboBox<String> comboBox1;
    private JLabel label5;
    private JComboBox comboBox2;
    private JLabel label6;
    private JTextField firstNumber2;
    private JLabel label7;
    private JTextField lastNumber2;
    private JLabel label8;
    private JDateChooser dateChooser1;
    private JDateChooser dateChooser2;
    private JLabel label9;
    private JComboBox<String> comboBox3;
    private JCheckBox checkBox16;
    private JButton button3;
    private JPanel ЗАВОД2;
    private JScrollPane scrollPane3;
    private JTable табл1;
    private JPanel panel10;
    private JPanel panel11;
    private JButton button10;
    private JPanel panel12;
    private JButton button11;
    private JPanel panel13;
    private JButton button12;
    private JPanel panel1;
    private JButton button14;
    private JPanel panel14;
    private JLabel label10;
    private JPanel panel15;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;
    private JCheckBox checkBox9;
    private JCheckBox checkBox10;
    private JCheckBox checkBox11;
    private JCheckBox checkBox12;
    private JCheckBox checkBox13;
    private JCheckBox checkBox14;
    private JLabel label11;
    private JTextField firstNumber3;
    private JLabel label12;
    private JTextField lastNumber3;
    private JLabel label13;
    private JComboBox<String> comboBox4;
    private JLabel label14;
    private JComboBox comboBox5;
    private JLabel label15;
    private JTextField firstNumber4;
    private JLabel label16;
    private JTextField lastNumber4;
    private JLabel label19;
    private JTextField firstNumber5;
    private JLabel label20;
    private JTextField lastNumber5;
    private JLabel label21;
    private JTextField firstNumber6;
    private JLabel label22;
    private JTextField lastNumber6;
    private JLabel label17;
    private JDateChooser dateChooser3;
    private JDateChooser dateChooser4;
    private JLabel label18;
    private JComboBox<String> comboBox6;
    private JCheckBox checkBox15;
    private JButton button13;
    private JPanel ЗАВОД3;
    private JScrollPane scrollPane4;
    private JTable табл2;
    private JPanel panel16;
    private JPanel panel17;
    private JButton button15;
    private JPanel panel18;
    private JButton button16;
    private JPanel panel19;
    private JButton button17;
    private JPanel panel5;
    private JButton button18;
    private JPanel panel20;
    private JLabel label23;
    private JPanel panel21;
    private JCheckBox checkBox17;
    private JCheckBox checkBox18;
    private JCheckBox checkBox19;
    private JCheckBox checkBox20;
    private JCheckBox checkBox21;
    private JCheckBox checkBox22;
    private JCheckBox checkBox23;
    private JCheckBox checkBox24;
    private JLabel label24;
    private JTextField firstNumber7;
    private JLabel label25;
    private JTextField lastNumber7;
    private JLabel label26;
    private JComboBox<String> comboBox7;
    private JLabel label27;
    private JComboBox comboBox8;
    private JLabel label28;
    private JTextField firstNumber8;
    private JLabel label29;
    private JTextField lastNumber8;
    private JLabel label30;
    private JTextField firstNumber9;
    private JLabel label31;
    private JTextField lastNumber9;
    private JLabel label32;
    private JTextField firstNumber10;
    private JLabel label33;
    private JTextField lastNumber10;
    private JLabel label34;
    private JDateChooser dateChooser5;
    private JDateChooser dateChooser6;
    private JLabel label35;
    private JComboBox<String> comboBox9;
    private JCheckBox checkBox25;
    private JButton button19;
    private JPanel ЗАВОД4;
    private JScrollPane scrollPane5;
    private JTable табл3;
    private JPanel panel22;
    private JPanel panel23;
    private JButton button20;
    private JPanel panel24;
    private JButton button21;
    private JPanel panel25;
    private JButton button22;
    private JPanel panel26;
    private JButton button23;
    private JPanel panel27;
    private JLabel label36;
    private JPanel panel28;
    private JCheckBox checkBox26;
    private JCheckBox checkBox27;
    private JCheckBox checkBox28;
    private JCheckBox checkBox29;
    private JCheckBox checkBox30;
    private JCheckBox checkBox31;
    private JCheckBox checkBox32;
    private JCheckBox checkBox33;
    private JLabel label37;
    private JTextField firstNumber11;
    private JLabel label38;
    private JTextField lastNumber11;
    private JLabel label39;
    private JComboBox<String> comboBox10;
    private JLabel label40;
    private JComboBox comboBox11;
    private JLabel label41;
    private JTextField firstNumber12;
    private JLabel label42;
    private JTextField lastNumber12;
    private JLabel label43;
    private JTextField firstNumber13;
    private JLabel label44;
    private JTextField lastNumber13;
    private JLabel label45;
    private JTextField firstNumber14;
    private JLabel label46;
    private JTextField lastNumber14;
    private JLabel label47;
    private JDateChooser dateChooser7;
    private JDateChooser dateChooser8;
    private JLabel label48;
    private JComboBox<String> comboBox12;
    private JCheckBox checkBox34;
    private JButton button24;
    private JPanel ЗАВОД5;
    private JScrollPane scrollPane6;
    private JTable табл4;
    private JPanel panel29;
    private JPanel panel30;
    private JButton button25;
    private JPanel panel31;
    private JButton button26;
    private JPanel panel32;
    private JButton button27;
    private JPanel panel33;
    private JButton button28;
    private JPanel panel34;
    private JLabel label49;
    private JPanel panel35;
    private JCheckBox checkBox35;
    private JCheckBox checkBox36;
    private JCheckBox checkBox37;
    private JCheckBox checkBox38;
    private JCheckBox checkBox39;
    private JCheckBox checkBox40;
    private JCheckBox checkBox41;
    private JCheckBox checkBox42;
    private JLabel label50;
    private JTextField firstNumber15;
    private JLabel label51;
    private JTextField lastNumber15;
    private JLabel label52;
    private JComboBox<String> comboBox13;
    private JLabel label53;
    private JComboBox comboBox14;
    private JLabel label54;
    private JTextField firstNumber16;
    private JLabel label55;
    private JTextField lastNumber16;
    private JLabel label56;
    private JTextField firstNumber17;
    private JLabel label57;
    private JTextField lastNumber17;
    private JLabel label58;
    private JTextField firstNumber18;
    private JLabel label59;
    private JTextField lastNumber18;
    private JLabel label60;
    private JDateChooser dateChooser9;
    private JDateChooser dateChooser10;
    private JLabel label61;
    private JComboBox<String> comboBox15;
    private JCheckBox checkBox43;
    private JButton button29;
    private JPanel ЗАВОД6;
    private JScrollPane scrollPane7;
    private JTable табл5;
    private JPanel panel36;
    private JPanel panel37;
    private JButton button30;
    private JPanel panel38;
    private JButton button31;
    private JPanel panel39;
    private JButton button32;
    private JPanel panel40;
    private JButton button33;
    private JPanel panel41;
    private JLabel label62;
    private JPanel panel42;
    private JCheckBox checkBox44;
    private JCheckBox checkBox45;
    private JCheckBox checkBox46;
    private JCheckBox checkBox47;
    private JCheckBox checkBox48;
    private JLabel label63;
    private JTextField firstNumber19;
    private JLabel label64;
    private JTextField lastNumber19;
    private JLabel label65;
    private JTextField textField1;
    private JLabel label66;
    private JTextField textField2;
    private JLabel label67;
    private JTextField firstNumber20;
    private JLabel label68;
    private JTextField lastNumber20;
    private JLabel label69;
    private JTextField firstNumber21;
    private JLabel label70;
    private JTextField lastNumber21;
    private JLabel label74;
    private JComboBox<String> comboBox18;
    private JCheckBox checkBox52;
    private JButton button34;
    private JPanel panel43;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JButton button6;
    private JPanel panel44;
    private JLabel label71;
    private JTextField textField3;
    private JLabel label72;
    private JTextField textField4;
    private JLabel label73;
    private JComboBox<String> comboBox16;
    private JLabel label75;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem2 = new JMenuItem();
        menu4 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menu3 = new JMenu();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menuItem7 = new JMenuItem();
        menuItem8 = new JMenuItem();
        menuItem9 = new JMenuItem();
        tabbedPane1 = new JTabbedPane();
        ЗАВОД = new JPanel();
        scrollPane1 = new JScrollPane();
        табл0 = new JTable();
        panel6 = new JPanel();
        panel8 = new JPanel();
        button4 = new JButton();
        panel9 = new JPanel();
        button1 = new JButton();
        panel7 = new JPanel();
        button2 = new JButton();
        panel2 = new JPanel();
        button5 = new JButton();
        panel3 = new JPanel();
        label2 = new JLabel();
        panel4 = new JPanel();
        checkBox1 = new JCheckBox();
        checkBox2 = new JCheckBox();
        checkBox3 = new JCheckBox();
        checkBox4 = new JCheckBox();
        checkBox5 = new JCheckBox();
        checkBox6 = new JCheckBox();
        label1 = new JLabel();
        firstNumber1 = new JTextField();
        label3 = new JLabel();
        lastNumber1 = new JTextField();
        label4 = new JLabel();
        comboBox1 = new JComboBox<>();
        label5 = new JLabel();
        comboBox2 = new JComboBox();
        label6 = new JLabel();
        firstNumber2 = new JTextField();
        label7 = new JLabel();
        lastNumber2 = new JTextField();
        label8 = new JLabel();
        dateChooser1 = new JDateChooser();
        dateChooser2 = new JDateChooser();
        label9 = new JLabel();
        comboBox3 = new JComboBox<>();
        checkBox16 = new JCheckBox();
        button3 = new JButton();
        ЗАВОД2 = new JPanel();
        scrollPane3 = new JScrollPane();
        табл1 = new JTable();
        panel10 = new JPanel();
        panel11 = new JPanel();
        button10 = new JButton();
        panel12 = new JPanel();
        button11 = new JButton();
        panel13 = new JPanel();
        button12 = new JButton();
        panel1 = new JPanel();
        button14 = new JButton();
        panel14 = new JPanel();
        label10 = new JLabel();
        panel15 = new JPanel();
        checkBox7 = new JCheckBox();
        checkBox8 = new JCheckBox();
        checkBox9 = new JCheckBox();
        checkBox10 = new JCheckBox();
        checkBox11 = new JCheckBox();
        checkBox12 = new JCheckBox();
        checkBox13 = new JCheckBox();
        checkBox14 = new JCheckBox();
        label11 = new JLabel();
        firstNumber3 = new JTextField();
        label12 = new JLabel();
        lastNumber3 = new JTextField();
        label13 = new JLabel();
        comboBox4 = new JComboBox<>();
        label14 = new JLabel();
        comboBox5 = new JComboBox();
        label15 = new JLabel();
        firstNumber4 = new JTextField();
        label16 = new JLabel();
        lastNumber4 = new JTextField();
        label19 = new JLabel();
        firstNumber5 = new JTextField();
        label20 = new JLabel();
        lastNumber5 = new JTextField();
        label21 = new JLabel();
        firstNumber6 = new JTextField();
        label22 = new JLabel();
        lastNumber6 = new JTextField();
        label17 = new JLabel();
        dateChooser3 = new JDateChooser();
        dateChooser4 = new JDateChooser();
        label18 = new JLabel();
        comboBox6 = new JComboBox<>();
        checkBox15 = new JCheckBox();
        button13 = new JButton();
        ЗАВОД3 = new JPanel();
        scrollPane4 = new JScrollPane();
        табл2 = new JTable();
        panel16 = new JPanel();
        panel17 = new JPanel();
        button15 = new JButton();
        panel18 = new JPanel();
        button16 = new JButton();
        panel19 = new JPanel();
        button17 = new JButton();
        panel5 = new JPanel();
        button18 = new JButton();
        panel20 = new JPanel();
        label23 = new JLabel();
        panel21 = new JPanel();
        checkBox17 = new JCheckBox();
        checkBox18 = new JCheckBox();
        checkBox19 = new JCheckBox();
        checkBox20 = new JCheckBox();
        checkBox21 = new JCheckBox();
        checkBox22 = new JCheckBox();
        checkBox23 = new JCheckBox();
        checkBox24 = new JCheckBox();
        label24 = new JLabel();
        firstNumber7 = new JTextField();
        label25 = new JLabel();
        lastNumber7 = new JTextField();
        label26 = new JLabel();
        comboBox7 = new JComboBox<>();
        label27 = new JLabel();
        comboBox8 = new JComboBox();
        label28 = new JLabel();
        firstNumber8 = new JTextField();
        label29 = new JLabel();
        lastNumber8 = new JTextField();
        label30 = new JLabel();
        firstNumber9 = new JTextField();
        label31 = new JLabel();
        lastNumber9 = new JTextField();
        label32 = new JLabel();
        firstNumber10 = new JTextField();
        label33 = new JLabel();
        lastNumber10 = new JTextField();
        label34 = new JLabel();
        dateChooser5 = new JDateChooser();
        dateChooser6 = new JDateChooser();
        label35 = new JLabel();
        comboBox9 = new JComboBox<>();
        checkBox25 = new JCheckBox();
        button19 = new JButton();
        ЗАВОД4 = new JPanel();
        scrollPane5 = new JScrollPane();
        табл3 = new JTable();
        panel22 = new JPanel();
        panel23 = new JPanel();
        button20 = new JButton();
        panel24 = new JPanel();
        button21 = new JButton();
        panel25 = new JPanel();
        button22 = new JButton();
        panel26 = new JPanel();
        button23 = new JButton();
        panel27 = new JPanel();
        label36 = new JLabel();
        panel28 = new JPanel();
        checkBox26 = new JCheckBox();
        checkBox27 = new JCheckBox();
        checkBox28 = new JCheckBox();
        checkBox29 = new JCheckBox();
        checkBox30 = new JCheckBox();
        checkBox31 = new JCheckBox();
        checkBox32 = new JCheckBox();
        checkBox33 = new JCheckBox();
        label37 = new JLabel();
        firstNumber11 = new JTextField();
        label38 = new JLabel();
        lastNumber11 = new JTextField();
        label39 = new JLabel();
        comboBox10 = new JComboBox<>();
        label40 = new JLabel();
        comboBox11 = new JComboBox();
        label41 = new JLabel();
        firstNumber12 = new JTextField();
        label42 = new JLabel();
        lastNumber12 = new JTextField();
        label43 = new JLabel();
        firstNumber13 = new JTextField();
        label44 = new JLabel();
        lastNumber13 = new JTextField();
        label45 = new JLabel();
        firstNumber14 = new JTextField();
        label46 = new JLabel();
        lastNumber14 = new JTextField();
        label47 = new JLabel();
        dateChooser7 = new JDateChooser();
        dateChooser8 = new JDateChooser();
        label48 = new JLabel();
        comboBox12 = new JComboBox<>();
        checkBox34 = new JCheckBox();
        button24 = new JButton();
        ЗАВОД5 = new JPanel();
        scrollPane6 = new JScrollPane();
        табл4 = new JTable();
        panel29 = new JPanel();
        panel30 = new JPanel();
        button25 = new JButton();
        panel31 = new JPanel();
        button26 = new JButton();
        panel32 = new JPanel();
        button27 = new JButton();
        panel33 = new JPanel();
        button28 = new JButton();
        panel34 = new JPanel();
        label49 = new JLabel();
        panel35 = new JPanel();
        checkBox35 = new JCheckBox();
        checkBox36 = new JCheckBox();
        checkBox37 = new JCheckBox();
        checkBox38 = new JCheckBox();
        checkBox39 = new JCheckBox();
        checkBox40 = new JCheckBox();
        checkBox41 = new JCheckBox();
        checkBox42 = new JCheckBox();
        label50 = new JLabel();
        firstNumber15 = new JTextField();
        label51 = new JLabel();
        lastNumber15 = new JTextField();
        label52 = new JLabel();
        comboBox13 = new JComboBox<>();
        label53 = new JLabel();
        comboBox14 = new JComboBox();
        label54 = new JLabel();
        firstNumber16 = new JTextField();
        label55 = new JLabel();
        lastNumber16 = new JTextField();
        label56 = new JLabel();
        firstNumber17 = new JTextField();
        label57 = new JLabel();
        lastNumber17 = new JTextField();
        label58 = new JLabel();
        firstNumber18 = new JTextField();
        label59 = new JLabel();
        lastNumber18 = new JTextField();
        label60 = new JLabel();
        dateChooser9 = new JDateChooser();
        dateChooser10 = new JDateChooser();
        label61 = new JLabel();
        comboBox15 = new JComboBox<>();
        checkBox43 = new JCheckBox();
        button29 = new JButton();
        ЗАВОД6 = new JPanel();
        scrollPane7 = new JScrollPane();
        табл5 = new JTable();
        panel36 = new JPanel();
        panel37 = new JPanel();
        button30 = new JButton();
        panel38 = new JPanel();
        button31 = new JButton();
        panel39 = new JPanel();
        button32 = new JButton();
        panel40 = new JPanel();
        button33 = new JButton();
        panel41 = new JPanel();
        label62 = new JLabel();
        panel42 = new JPanel();
        checkBox44 = new JCheckBox();
        checkBox45 = new JCheckBox();
        checkBox46 = new JCheckBox();
        checkBox47 = new JCheckBox();
        checkBox48 = new JCheckBox();
        label63 = new JLabel();
        firstNumber19 = new JTextField();
        label64 = new JLabel();
        lastNumber19 = new JTextField();
        label65 = new JLabel();
        textField1 = new JTextField();
        label66 = new JLabel();
        textField2 = new JTextField();
        label67 = new JLabel();
        firstNumber20 = new JTextField();
        label68 = new JLabel();
        lastNumber20 = new JTextField();
        label69 = new JLabel();
        firstNumber21 = new JTextField();
        label70 = new JLabel();
        lastNumber21 = new JTextField();
        label74 = new JLabel();
        comboBox18 = new JComboBox<>();
        checkBox52 = new JCheckBox();
        button34 = new JButton();
        panel43 = new JPanel();
        scrollPane2 = new JScrollPane();
        table1 = new JTable();
        button6 = new JButton();
        panel44 = new JPanel();
        label71 = new JLabel();
        textField3 = new JTextField();
        label72 = new JLabel();
        textField4 = new JTextField();
        label73 = new JLabel();
        comboBox16 = new JComboBox<>();
        label75 = new JLabel();
        button7 = new JButton();
        button8 = new JButton();
        button9 = new JButton();

        //======== this ========
        setResizable(false);
        setMinimumSize(new Dimension(502, 200));
        setVisible(true);
        setFont(new Font("Verdana", Font.PLAIN, 12));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("\u0421\u043a\u043b\u0430\u0434 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0445 \u0432\u0456\u0434\u0445\u043e\u0434\u0456\u0432");
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "[449dlu,pref]:grow",
            "250dlu:grow"));

        //======== menuBar1 ========
        {
            menuBar1.setPreferredSize(new Dimension(502, 24));
            menuBar1.setFont(new Font("Verdana", Font.PLAIN, 12));
            menuBar1.setMinimumSize(new Dimension(502, 300));
            menuBar1.setMaximumSize(new Dimension(502, 32769));

            //======== menu2 ========
            {
                menu2.setText("\u0424\u0430\u0439\u043b");
                menu2.setFont(new Font("Verdana", Font.PLAIN, 12));

                //---- menuItem2 ----
                menuItem2.setText("\u0417\u0430\u043f\u0438\u0441\u0430\u0442\u0438 \u0434\u043e \u0442\u0430\u0431\u043b\u0438\u0446\u0456 \u043f\u043e\u0447\u0430\u0442\u043a\u043e\u0432\u0456 \u0434\u0430\u043d\u0456");
                menuItem2.setFont(new Font("Verdana", Font.PLAIN, 12));
                menuItem2.addActionListener(e -> menuItem2ActionPerformed(e));
                menu2.add(menuItem2);

                //======== menu4 ========
                {
                    menu4.setText("\u0415\u043a\u0441\u043f\u043e\u0440\u0442");
                    menu4.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- menuItem1 ----
                    menuItem1.setText("PDF");
                    menuItem1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
                    menu4.add(menuItem1);

                    //---- menuItem3 ----
                    menuItem3.setText("Excel");
                    menuItem3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    menuItem3.addActionListener(e -> menuItem3ActionPerformed(e));
                    menu4.add(menuItem3);
                }
                menu2.add(menu4);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0432\u0441\u0456 \u0434\u0430\u043d\u0456 \u0437 \u0442\u0430\u0431\u043b\u0438\u0446\u0456");
                menu3.setFont(new Font("Verdana", Font.PLAIN, 12));

                //---- menuItem4 ----
                menuItem4.setText("\u0417\u0410\u0412\u041e\u0414");
                menuItem4.setFont(new Font("Verdana", Font.PLAIN, 12));
                menuItem4.addActionListener(e -> menuItem4ActionPerformed(e));
                menu3.add(menuItem4);

                //---- menuItem5 ----
                menuItem5.setText("\u0422\u0420\u0412");
                menuItem5.setFont(new Font("Verdana", Font.PLAIN, 12));
                menuItem5.addActionListener(e -> menuItem5ActionPerformed(e));
                menu3.add(menuItem5);

                //---- menuItem6 ----
                menuItem6.setText("\u0420\u0420\u0412");
                menuItem6.setFont(new Font("Verdana", Font.PLAIN, 12));
                menuItem6.addActionListener(e -> menuItem6ActionPerformed(e));
                menu3.add(menuItem6);

                //---- menuItem7 ----
                menuItem7.setText("\u0411\u0420\u0412");
                menuItem7.setFont(new Font("Verdana", Font.PLAIN, 12));
                menuItem7.addActionListener(e -> menuItem7ActionPerformed(e));
                menu3.add(menuItem7);

                //---- menuItem8 ----
                menuItem8.setText("\u0414\u0406\u0412");
                menuItem8.setFont(new Font("Verdana", Font.PLAIN, 12));
                menuItem8.addActionListener(e -> menuItem8ActionPerformed(e));
                menu3.add(menuItem8);
            }
            menuBar1.add(menu3);

            //---- menuItem9 ----
            menuItem9.setText("\u0417\u043c\u0456\u043d\u0438\u0442\u0438 \u043a\u043e\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0430");
            menuItem9.setFont(new Font("Verdana", Font.PLAIN, 12));
            menuItem9.addActionListener(e -> menuItem9ActionPerformed(e));
            menuBar1.add(menuItem9);
        }
        setJMenuBar(menuBar1);

        //======== tabbedPane1 ========
        {
            tabbedPane1.setPreferredSize(new Dimension(472, 302));
            tabbedPane1.setMinimumSize(new Dimension(502, 300));
            tabbedPane1.setFont(new Font("Verdana", Font.PLAIN, 12));

            //======== ЗАВОД ========
            {
                ЗАВОД.setMinimumSize(new Dimension(500, 200));
                ЗАВОД.setPreferredSize(new Dimension(470, 200));
                ЗАВОД.setFont(new Font("Verdana", Font.PLAIN, 12));
                ЗАВОД.setMaximumSize(new Dimension(500, 200));
                ЗАВОД.setLayout(new FormLayout(
                    "[320dlu,pref], $lcgap, default, $lcgap, 125dlu:grow",
                    "fill:225dlu:grow"));

                //======== scrollPane1 ========
                {
                    scrollPane1.setMinimumSize(new Dimension(700, 400));
                    scrollPane1.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- табл0 ----
                    табл0.setMinimumSize(new Dimension(30, 204));
                    табл0.setFont(new Font("Verdana", Font.PLAIN, 12));
                    scrollPane1.setViewportView(табл0);
                }
                ЗАВОД.add(scrollPane1, CC.xy(1, 1));

                //======== panel6 ========
                {
                    panel6.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel6.setLayout(new FormLayout(
                        "center:55dlu",
                        "fill:50dlu:grow, 2*($lgap, 50dlu:grow), $lgap, fill:50dlu:grow"));

                    //======== panel8 ========
                    {
                        panel8.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel8.setLayout(new FormLayout(
                            "default:grow",
                            "default:grow"));

                        //---- button4 ----
                        button4.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button4.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f2.gif")));
                        button4.setToolTipText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button4.setMinimumSize(new Dimension(50, 50));
                        button4.setMaximumSize(new Dimension(50, 50));
                        button4.addActionListener(e -> deleteFromZAVOD(e));
                        panel8.add(button4, CC.xy(1, 1));
                    }
                    panel6.add(panel8, CC.xy(1, 3));

                    //======== panel9 ========
                    {
                        panel9.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel9.setLayout(new FormLayout(
                            "default:grow",
                            "default:grow"));

                        //---- button1 ----
                        button1.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button1.setMaximumSize(new Dimension(50, 50));
                        button1.setMinimumSize(new Dimension(50, 50));
                        button1.setMargin(new Insets(0, 0, 0, 0));
                        button1.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f3.gif")));
                        button1.setToolTipText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0434\u0430\u043d\u0456 \u043f\u0440\u043e \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button1.addActionListener(e -> updateInZAVOD(e));
                        panel9.add(button1, CC.xy(1, 1));
                    }
                    panel6.add(panel9, CC.xy(1, 5));

                    //======== panel7 ========
                    {
                        panel7.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel7.setLayout(new FormLayout(
                            "default:grow",
                            "default:grow"));

                        //---- button2 ----
                        button2.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button2.setMaximumSize(new Dimension(50, 50));
                        button2.setMinimumSize(new Dimension(50, 50));
                        button2.setMargin(new Insets(0, 0, 0, 0));
                        button2.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f1.gif")));
                        button2.setToolTipText("\u0414\u043e\u0434\u0430\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button2.addActionListener(e -> insertIntoZAVOD(e));
                        panel7.add(button2, CC.xy(1, 1));
                    }
                    panel6.add(panel7, CC.xy(1, 1));

                    //======== panel2 ========
                    {
                        panel2.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel2.setLayout(new FormLayout(
                            "default:grow",
                            "fill:default:grow"));

                        //---- button5 ----
                        button5.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button5.setMaximumSize(new Dimension(50, 50));
                        button5.setMinimumSize(new Dimension(50, 50));
                        button5.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f4.gif")));
                        button5.addActionListener(e -> refreshTableZAVOD(e));
                        panel2.add(button5, CC.xy(1, 1));
                    }
                    panel6.add(panel2, CC.xy(1, 7));
                }
                ЗАВОД.add(panel6, CC.xy(3, 1));

                //======== panel3 ========
                {
                    panel3.setBorder(new EmptyBorder(5, 5, 5, 5));
                    panel3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.setLayout(new FormLayout(
                        "43dlu, $lcgap, 40dlu:grow, $lcgap, default, $lcgap, 37dlu:grow",
                        "2*(default), $lgap, default, $lgap, min, 7*($lgap, default), $lgap, fill:default:grow"));

                    //---- label2 ----
                    label2.setText("\u041f\u0430\u043d\u0435\u043b\u044c \u043f\u043e\u0448\u0443\u043a\u0443");
                    label2.setIcon(UIManager.getIcon("TextField.darcula.search.icon"));
                    label2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label2, CC.xywh(1, 1, 7, 1));

                    //======== panel4 ========
                    {
                        panel4.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.setLayout(new FormLayout(
                            "${growing-button}, $lcgap, ${growing-button}",
                            "2*(default, $lgap), default"));

                        //---- checkBox1 ----
                        checkBox1.setText("\u041d\u043e\u043c\u0435\u0440");
                        checkBox1.setSelected(true);
                        checkBox1.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.add(checkBox1, CC.xy(1, 1));

                        //---- checkBox2 ----
                        checkBox2.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0432\u0456\u0434\u0445\u043e\u0434\u0443");
                        checkBox2.setSelected(true);
                        checkBox2.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.add(checkBox2, CC.xy(3, 1));

                        //---- checkBox3 ----
                        checkBox3.setText("\u041d\u0443\u043a\u043b\u0456\u0434");
                        checkBox3.setSelected(true);
                        checkBox3.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.add(checkBox3, CC.xy(1, 3));

                        //---- checkBox4 ----
                        checkBox4.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c");
                        checkBox4.setSelected(true);
                        checkBox4.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.add(checkBox4, CC.xy(3, 3));

                        //---- checkBox5 ----
                        checkBox5.setText("\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f");
                        checkBox5.setSelected(true);
                        checkBox5.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.add(checkBox5, CC.xy(1, 5));

                        //---- checkBox6 ----
                        checkBox6.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
                        checkBox6.setSelected(true);
                        checkBox6.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel4.add(checkBox6, CC.xy(3, 5));
                    }
                    panel3.add(panel4, CC.xywh(1, 4, 7, 1));

                    //---- label1 ----
                    label1.setText("\u041d\u043e\u043c\u0435\u0440:");
                    label1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label1, CC.xy(1, 8));

                    //---- firstNumber1 ----
                    firstNumber1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(firstNumber1, CC.xy(3, 8));

                    //---- label3 ----
                    label3.setText("-");
                    label3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label3, CC.xy(5, 8));

                    //---- lastNumber1 ----
                    lastNumber1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(lastNumber1, CC.xy(7, 8));

                    //---- label4 ----
                    label4.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f: ");
                    label4.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label4, CC.xy(1, 10));

                    //---- comboBox1 ----
                    comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                        "-",
                        "\u0422\u0420\u0412",
                        "\u0420\u0420\u0412",
                        "\u0411\u0420\u0412",
                        "\u0414\u0406\u0412"
                    }));
                    comboBox1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(comboBox1, CC.xywh(3, 10, 5, 1));

                    //---- label5 ----
                    label5.setText("\u041d\u0443\u043a\u043b\u0456\u0434:");
                    label5.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label5, CC.xy(1, 12));

                    //---- comboBox2 ----
                    comboBox2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(comboBox2, CC.xywh(3, 12, 5, 1));

                    //---- label6 ----
                    label6.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c:");
                    label6.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label6, CC.xy(1, 14));

                    //---- firstNumber2 ----
                    firstNumber2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber2.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber2.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel3.add(firstNumber2, CC.xy(3, 14));

                    //---- label7 ----
                    label7.setText("-");
                    label7.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label7, CC.xy(5, 14));

                    //---- lastNumber2 ----
                    lastNumber2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(lastNumber2, CC.xy(7, 14));

                    //---- label8 ----
                    label8.setText("\u0414\u0430\u0442\u0430:");
                    label8.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label8, CC.xy(1, 16));

                    //---- dateChooser1 ----
                    dateChooser1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(dateChooser1, CC.xy(3, 16));

                    //---- dateChooser2 ----
                    dateChooser2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(dateChooser2, CC.xy(7, 16));

                    //---- label9 ----
                    label9.setText("\u0421\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438:");
                    label9.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(label9, CC.xy(1, 18));

                    //---- comboBox3 ----
                    comboBox3.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u041d\u043e\u043c\u0435\u0440",
                        "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0432\u0456\u0434\u0445\u043e\u0434\u0443",
                        "\u041d\u0443\u043a\u043b\u0456\u0434",
                        "\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c",
                        "\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f",
                        "\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438"
                    }));
                    comboBox3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(comboBox3, CC.xy(3, 18));

                    //---- checkBox16 ----
                    checkBox16.setText("\u041f\u043e \u0441\u043f\u0430\u0434\u0430\u043d\u043d\u044e");
                    checkBox16.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel3.add(checkBox16, CC.xy(7, 18));

                    //---- button3 ----
                    button3.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043f\u043e\u043b\u044f \u043f\u043e\u0448\u0443\u043a\u0443");
                    button3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    button3.addActionListener(e -> searchCleanerZAVOD(e));
                    panel3.add(button3, CC.xywh(1, 20, 7, 1));
                }
                ЗАВОД.add(panel3, CC.xy(5, 1));
            }
            tabbedPane1.addTab("\u0417\u0430\u0432\u043e\u0434/\u043f\u043e\u0441\u0442\u0430\u0447\u0430\u043b\u044c\u043d\u0438\u043a \u0420\u0410\u0412", ЗАВОД);

            //======== ЗАВОД2 ========
            {
                ЗАВОД2.setMinimumSize(new Dimension(500, 200));
                ЗАВОД2.setPreferredSize(new Dimension(470, 200));
                ЗАВОД2.setFont(new Font("Verdana", Font.PLAIN, 12));
                ЗАВОД2.setMaximumSize(new Dimension(500, 200));
                ЗАВОД2.setLayout(new FormLayout(
                    "[320dlu,pref], $lcgap, default, $lcgap, 125dlu:grow",
                    "fill:225dlu:grow"));

                //======== scrollPane3 ========
                {
                    scrollPane3.setMinimumSize(new Dimension(700, 400));
                    scrollPane3.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- табл1 ----
                    табл1.setMinimumSize(new Dimension(30, 204));
                    табл1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    scrollPane3.setViewportView(табл1);
                }
                ЗАВОД2.add(scrollPane3, CC.xy(1, 1));

                //======== panel10 ========
                {
                    panel10.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel10.setLayout(new FormLayout(
                        "center:55dlu",
                        "fill:50dlu:grow, 2*($lgap, 50dlu:grow), $lgap, fill:50dlu:grow"));

                    //======== panel11 ========
                    {
                        panel11.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel11.setMinimumSize(new Dimension(50, 98));
                        panel11.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button10 ----
                        button10.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button10.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f2.gif")));
                        button10.setToolTipText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button10.setMinimumSize(new Dimension(50, 50));
                        button10.addActionListener(e -> deleteFromTRV(e));
                        panel11.add(button10, CC.xy(1, 1));
                    }
                    panel10.add(panel11, CC.xy(1, 3));

                    //======== panel12 ========
                    {
                        panel12.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel12.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button11 ----
                        button11.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button11.setMinimumSize(new Dimension(50, 50));
                        button11.setMargin(new Insets(0, 0, 0, 0));
                        button11.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f3.gif")));
                        button11.setToolTipText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0434\u0430\u043d\u0456 \u043f\u0440\u043e \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button11.addActionListener(e -> updateInTRV(e));
                        panel12.add(button11, CC.xy(1, 1));
                    }
                    panel10.add(panel12, CC.xy(1, 5));

                    //======== panel13 ========
                    {
                        panel13.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel13.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu"));

                        //---- button12 ----
                        button12.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button12.setMinimumSize(new Dimension(50, 50));
                        button12.setMargin(new Insets(0, 0, 0, 0));
                        button12.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f1.gif")));
                        button12.setToolTipText("\u0414\u043e\u0434\u0430\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button12.addActionListener(e -> insertIntoTRV(e));
                        panel13.add(button12, CC.xy(1, 1));
                    }
                    panel10.add(panel13, CC.xy(1, 1));

                    //======== panel1 ========
                    {
                        panel1.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel1.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu:grow"));

                        //---- button14 ----
                        button14.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button14.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f4.gif")));
                        button14.addActionListener(e -> refreshTableTRV(e));
                        panel1.add(button14, CC.xy(1, 1));
                    }
                    panel10.add(panel1, CC.xy(1, 7));
                }
                ЗАВОД2.add(panel10, CC.xy(3, 1));

                //======== panel14 ========
                {
                    panel14.setBorder(new EmptyBorder(5, 5, 5, 5));
                    panel14.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.setLayout(new FormLayout(
                        "55dlu, $lcgap, 70dlu:grow, $lcgap, default, $lcgap, 50dlu:grow",
                        "default, min, $lgap, default, $lgap, min, 8*($lgap, default), 10dlu, default, $lgap, fill:default:grow, $lgap, default"));

                    //---- label10 ----
                    label10.setText("\u041f\u0430\u043d\u0435\u043b\u044c \u043f\u043e\u0448\u0443\u043a\u0443");
                    label10.setIcon(UIManager.getIcon("TextField.darcula.search.icon"));
                    label10.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label10, CC.xywh(1, 1, 7, 1));

                    //======== panel15 ========
                    {
                        panel15.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.setLayout(new FormLayout(
                            "${growing-button}, $lcgap, ${growing-button}",
                            "3*(default, $lgap), default"));

                        //---- checkBox7 ----
                        checkBox7.setText("\u041d\u043e\u043c\u0435\u0440");
                        checkBox7.setSelected(true);
                        checkBox7.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox7, CC.xy(1, 1));

                        //---- checkBox8 ----
                        checkBox8.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456");
                        checkBox8.setSelected(true);
                        checkBox8.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox8, CC.xy(3, 1));

                        //---- checkBox9 ----
                        checkBox9.setText("\u041d\u0443\u043a\u043b\u0456\u0434");
                        checkBox9.setSelected(true);
                        checkBox9.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox9, CC.xy(1, 3));

                        //---- checkBox10 ----
                        checkBox10.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c");
                        checkBox10.setSelected(true);
                        checkBox10.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox10, CC.xy(3, 3));

                        //---- checkBox11 ----
                        checkBox11.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
                        checkBox11.setSelected(true);
                        checkBox11.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox11, CC.xy(1, 5));

                        //---- checkBox12 ----
                        checkBox12.setText("\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c");
                        checkBox12.setSelected(true);
                        checkBox12.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox12, CC.xy(3, 5));

                        //---- checkBox13 ----
                        checkBox13.setText("\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f");
                        checkBox13.setSelected(true);
                        checkBox13.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox13, CC.xy(1, 7));

                        //---- checkBox14 ----
                        checkBox14.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
                        checkBox14.setSelected(true);
                        checkBox14.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel15.add(checkBox14, CC.xy(3, 7));
                    }
                    panel14.add(panel15, CC.xywh(1, 4, 7, 1));

                    //---- label11 ----
                    label11.setText("\u041d\u043e\u043c\u0435\u0440:");
                    label11.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label11, CC.xy(1, 8));

                    //---- firstNumber3 ----
                    firstNumber3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber3.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber3.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel14.add(firstNumber3, CC.xy(3, 8));

                    //---- label12 ----
                    label12.setText("-");
                    label12.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label12, CC.xy(5, 8));

                    //---- lastNumber3 ----
                    lastNumber3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(lastNumber3, CC.xy(7, 8));

                    //---- label13 ----
                    label13.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f: ");
                    label13.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label13, CC.xy(1, 10));

                    //---- comboBox4 ----
                    comboBox4.setModel(new DefaultComboBoxModel<>(new String[] {
                        "-",
                        "\u0421\u043b\u0430\u0431\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0435\u0440\u0435\u0434\u043d\u044c\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0438\u043b\u044c\u043d\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439"
                    }));
                    comboBox4.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(comboBox4, CC.xywh(3, 10, 5, 1));

                    //---- label14 ----
                    label14.setText("\u041d\u0443\u043a\u043b\u0456\u0434:");
                    label14.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label14, CC.xy(1, 12));

                    //---- comboBox5 ----
                    comboBox5.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(comboBox5, CC.xywh(3, 12, 5, 1));

                    //---- label15 ----
                    label15.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c:");
                    label15.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label15, CC.xy(1, 14));

                    //---- firstNumber4 ----
                    firstNumber4.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber4.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber4.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel14.add(firstNumber4, CC.xy(3, 14));

                    //---- label16 ----
                    label16.setText("-");
                    label16.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label16, CC.xy(5, 14));

                    //---- lastNumber4 ----
                    lastNumber4.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(lastNumber4, CC.xy(7, 14));

                    //---- label19 ----
                    label19.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430:");
                    label19.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label19, CC.xy(1, 16));

                    //---- firstNumber5 ----
                    firstNumber5.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber5.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber5.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel14.add(firstNumber5, CC.xy(3, 16));

                    //---- label20 ----
                    label20.setText("-");
                    label20.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label20, CC.xy(5, 16));

                    //---- lastNumber5 ----
                    lastNumber5.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(lastNumber5, CC.xy(7, 16));

                    //---- label21 ----
                    label21.setText("\u0417\u0430\u0433. \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c:");
                    label21.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label21, CC.xy(1, 18));

                    //---- firstNumber6 ----
                    firstNumber6.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber6.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber6.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel14.add(firstNumber6, CC.xy(3, 18));

                    //---- label22 ----
                    label22.setText("-");
                    label22.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label22, CC.xy(5, 18));

                    //---- lastNumber6 ----
                    lastNumber6.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(lastNumber6, CC.xy(7, 18));

                    //---- label17 ----
                    label17.setText("\u0414\u0430\u0442\u0430:");
                    label17.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label17, CC.xy(1, 20));

                    //---- dateChooser3 ----
                    dateChooser3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(dateChooser3, CC.xy(3, 20));

                    //---- dateChooser4 ----
                    dateChooser4.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(dateChooser4, CC.xy(7, 20));

                    //---- label18 ----
                    label18.setText("\u0421\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438:");
                    label18.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(label18, CC.xy(1, 22));

                    //---- comboBox6 ----
                    comboBox6.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u041d\u043e\u043c\u0435\u0440",
                        "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456",
                        "\u041d\u0443\u043a\u043b\u0456\u0434",
                        "\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c",
                        "\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430",
                        "\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c",
                        "\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f",
                        "\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438"
                    }));
                    comboBox6.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(comboBox6, CC.xy(3, 22));

                    //---- checkBox15 ----
                    checkBox15.setText("\u041f\u043e \u0441\u043f\u0430\u0434\u0430\u043d\u043d\u044e");
                    checkBox15.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel14.add(checkBox15, CC.xy(7, 22));

                    //---- button13 ----
                    button13.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043f\u043e\u043b\u044f \u043f\u043e\u0448\u0443\u043a\u0443");
                    button13.setFont(new Font("Verdana", Font.PLAIN, 12));
                    button13.addActionListener(e -> searchCleanerTRV(e));
                    panel14.add(button13, CC.xywh(1, 24, 7, 1));
                }
                ЗАВОД2.add(panel14, CC.xy(5, 1));
            }
            tabbedPane1.addTab("\u0422\u0432\u0435\u0440\u0434\u0456 \u0420\u0410\u0412", ЗАВОД2);

            //======== ЗАВОД3 ========
            {
                ЗАВОД3.setMinimumSize(new Dimension(500, 200));
                ЗАВОД3.setPreferredSize(new Dimension(470, 200));
                ЗАВОД3.setFont(new Font("Verdana", Font.PLAIN, 12));
                ЗАВОД3.setMaximumSize(new Dimension(500, 200));
                ЗАВОД3.setLayout(new FormLayout(
                    "[320dlu,pref], $lcgap, default, $lcgap, 125dlu:grow",
                    "fill:225dlu:grow"));

                //======== scrollPane4 ========
                {
                    scrollPane4.setMinimumSize(new Dimension(700, 400));
                    scrollPane4.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- табл2 ----
                    табл2.setMinimumSize(new Dimension(30, 204));
                    табл2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    scrollPane4.setViewportView(табл2);
                }
                ЗАВОД3.add(scrollPane4, CC.xy(1, 1));

                //======== panel16 ========
                {
                    panel16.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel16.setLayout(new FormLayout(
                        "center:55dlu",
                        "fill:50dlu:grow, 2*($lgap, 50dlu:grow), $lgap, fill:50dlu:grow"));

                    //======== panel17 ========
                    {
                        panel17.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel17.setMinimumSize(new Dimension(50, 98));
                        panel17.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button15 ----
                        button15.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button15.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f2.gif")));
                        button15.setToolTipText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button15.setMinimumSize(new Dimension(50, 50));
                        button15.addActionListener(e -> deleteFromRRV(e));
                        panel17.add(button15, CC.xy(1, 1));
                    }
                    panel16.add(panel17, CC.xy(1, 3));

                    //======== panel18 ========
                    {
                        panel18.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel18.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button16 ----
                        button16.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button16.setMinimumSize(new Dimension(50, 50));
                        button16.setMargin(new Insets(0, 0, 0, 0));
                        button16.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f3.gif")));
                        button16.setToolTipText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0434\u0430\u043d\u0456 \u043f\u0440\u043e \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button16.addActionListener(e -> updateInRRV(e));
                        panel18.add(button16, CC.xy(1, 1));
                    }
                    panel16.add(panel18, CC.xy(1, 5));

                    //======== panel19 ========
                    {
                        panel19.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel19.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu"));

                        //---- button17 ----
                        button17.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button17.setMinimumSize(new Dimension(50, 50));
                        button17.setMargin(new Insets(0, 0, 0, 0));
                        button17.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f1.gif")));
                        button17.setToolTipText("\u0414\u043e\u0434\u0430\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button17.addActionListener(e -> insertIntoRRV(e));
                        panel19.add(button17, CC.xy(1, 1));
                    }
                    panel16.add(panel19, CC.xy(1, 1));

                    //======== panel5 ========
                    {
                        panel5.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel5.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu:grow"));

                        //---- button18 ----
                        button18.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button18.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f4.gif")));
                        button18.addActionListener(e -> refreshTableRRV(e));
                        panel5.add(button18, CC.xy(1, 1));
                    }
                    panel16.add(panel5, CC.xy(1, 7));
                }
                ЗАВОД3.add(panel16, CC.xy(3, 1));

                //======== panel20 ========
                {
                    panel20.setBorder(new EmptyBorder(5, 5, 5, 5));
                    panel20.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.setLayout(new FormLayout(
                        "55dlu, $lcgap, 70dlu:grow, $lcgap, default, $lcgap, 50dlu:grow",
                        "2*(default), $lgap, default, $lgap, min, 8*($lgap, default), 10dlu, default, $lgap, fill:default:grow, $lgap, default"));

                    //---- label23 ----
                    label23.setText("\u041f\u0430\u043d\u0435\u043b\u044c \u043f\u043e\u0448\u0443\u043a\u0443");
                    label23.setIcon(UIManager.getIcon("TextField.darcula.search.icon"));
                    label23.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label23, CC.xywh(1, 1, 7, 1));

                    //======== panel21 ========
                    {
                        panel21.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.setLayout(new FormLayout(
                            "${growing-button}, $lcgap, ${growing-button}",
                            "3*(default, $lgap), default"));

                        //---- checkBox17 ----
                        checkBox17.setText("\u041d\u043e\u043c\u0435\u0440");
                        checkBox17.setSelected(true);
                        checkBox17.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox17, CC.xy(1, 1));

                        //---- checkBox18 ----
                        checkBox18.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456");
                        checkBox18.setSelected(true);
                        checkBox18.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox18, CC.xy(3, 1));

                        //---- checkBox19 ----
                        checkBox19.setText("\u041d\u0443\u043a\u043b\u0456\u0434");
                        checkBox19.setSelected(true);
                        checkBox19.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox19, CC.xy(1, 3));

                        //---- checkBox20 ----
                        checkBox20.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c");
                        checkBox20.setSelected(true);
                        checkBox20.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox20, CC.xy(3, 3));

                        //---- checkBox21 ----
                        checkBox21.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
                        checkBox21.setSelected(true);
                        checkBox21.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox21, CC.xy(1, 5));

                        //---- checkBox22 ----
                        checkBox22.setText("\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c");
                        checkBox22.setSelected(true);
                        checkBox22.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox22, CC.xy(3, 5));

                        //---- checkBox23 ----
                        checkBox23.setText("\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f");
                        checkBox23.setSelected(true);
                        checkBox23.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox23, CC.xy(1, 7));

                        //---- checkBox24 ----
                        checkBox24.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
                        checkBox24.setSelected(true);
                        checkBox24.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel21.add(checkBox24, CC.xy(3, 7));
                    }
                    panel20.add(panel21, CC.xywh(1, 4, 7, 1));

                    //---- label24 ----
                    label24.setText("\u041d\u043e\u043c\u0435\u0440:");
                    label24.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label24, CC.xy(1, 8));

                    //---- firstNumber7 ----
                    firstNumber7.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber7.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber7.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel20.add(firstNumber7, CC.xy(3, 8));

                    //---- label25 ----
                    label25.setText("-");
                    label25.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label25, CC.xy(5, 8));

                    //---- lastNumber7 ----
                    lastNumber7.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(lastNumber7, CC.xy(7, 8));

                    //---- label26 ----
                    label26.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f: ");
                    label26.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label26, CC.xy(1, 10));

                    //---- comboBox7 ----
                    comboBox7.setModel(new DefaultComboBoxModel<>(new String[] {
                        "-",
                        "\u0421\u043b\u0430\u0431\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0435\u0440\u0435\u0434\u043d\u044c\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0438\u043b\u044c\u043d\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439"
                    }));
                    comboBox7.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(comboBox7, CC.xywh(3, 10, 5, 1));

                    //---- label27 ----
                    label27.setText("\u041d\u0443\u043a\u043b\u0456\u0434:");
                    label27.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label27, CC.xy(1, 12));

                    //---- comboBox8 ----
                    comboBox8.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(comboBox8, CC.xywh(3, 12, 5, 1));

                    //---- label28 ----
                    label28.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c:");
                    label28.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label28, CC.xy(1, 14));

                    //---- firstNumber8 ----
                    firstNumber8.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber8.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber8.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel20.add(firstNumber8, CC.xy(3, 14));

                    //---- label29 ----
                    label29.setText("-");
                    label29.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label29, CC.xy(5, 14));

                    //---- lastNumber8 ----
                    lastNumber8.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(lastNumber8, CC.xy(7, 14));

                    //---- label30 ----
                    label30.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430:");
                    label30.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label30, CC.xy(1, 16));

                    //---- firstNumber9 ----
                    firstNumber9.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber9.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber9.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel20.add(firstNumber9, CC.xy(3, 16));

                    //---- label31 ----
                    label31.setText("-");
                    label31.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label31, CC.xy(5, 16));

                    //---- lastNumber9 ----
                    lastNumber9.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(lastNumber9, CC.xy(7, 16));

                    //---- label32 ----
                    label32.setText("\u0417\u0430\u0433. \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c:");
                    label32.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label32, CC.xy(1, 18));

                    //---- firstNumber10 ----
                    firstNumber10.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber10.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber10.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel20.add(firstNumber10, CC.xy(3, 18));

                    //---- label33 ----
                    label33.setText("-");
                    label33.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label33, CC.xy(5, 18));

                    //---- lastNumber10 ----
                    lastNumber10.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(lastNumber10, CC.xy(7, 18));

                    //---- label34 ----
                    label34.setText("\u0414\u0430\u0442\u0430:");
                    label34.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label34, CC.xy(1, 20));

                    //---- dateChooser5 ----
                    dateChooser5.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(dateChooser5, CC.xy(3, 20));

                    //---- dateChooser6 ----
                    dateChooser6.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(dateChooser6, CC.xy(7, 20));

                    //---- label35 ----
                    label35.setText("\u0421\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438:");
                    label35.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(label35, CC.xy(1, 22));

                    //---- comboBox9 ----
                    comboBox9.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u041d\u043e\u043c\u0435\u0440",
                        "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456",
                        "\u041d\u0443\u043a\u043b\u0456\u0434",
                        "\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c",
                        "\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430",
                        "\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c",
                        "\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f",
                        "\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438"
                    }));
                    comboBox9.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(comboBox9, CC.xy(3, 22));

                    //---- checkBox25 ----
                    checkBox25.setText("\u041f\u043e \u0441\u043f\u0430\u0434\u0430\u043d\u043d\u044e");
                    checkBox25.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel20.add(checkBox25, CC.xy(7, 22));

                    //---- button19 ----
                    button19.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043f\u043e\u043b\u044f \u043f\u043e\u0448\u0443\u043a\u0443");
                    button19.setFont(new Font("Verdana", Font.PLAIN, 12));
                    button19.addActionListener(e -> searchCleanerRRV(e));
                    panel20.add(button19, CC.xywh(1, 24, 7, 1));
                }
                ЗАВОД3.add(panel20, CC.xy(5, 1));
            }
            tabbedPane1.addTab("\u0420\u0456\u0434\u043a\u0456 \u0420\u0410\u0412", ЗАВОД3);

            //======== ЗАВОД4 ========
            {
                ЗАВОД4.setMinimumSize(new Dimension(500, 200));
                ЗАВОД4.setPreferredSize(new Dimension(470, 200));
                ЗАВОД4.setFont(new Font("Verdana", Font.PLAIN, 12));
                ЗАВОД4.setMaximumSize(new Dimension(500, 200));
                ЗАВОД4.setLayout(new FormLayout(
                    "[320dlu,pref], $lcgap, default, $lcgap, 125dlu:grow",
                    "fill:225dlu:grow"));

                //======== scrollPane5 ========
                {
                    scrollPane5.setMinimumSize(new Dimension(700, 400));
                    scrollPane5.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- табл3 ----
                    табл3.setMinimumSize(new Dimension(30, 204));
                    табл3.setFont(new Font("Verdana", Font.PLAIN, 12));
                    scrollPane5.setViewportView(табл3);
                }
                ЗАВОД4.add(scrollPane5, CC.xy(1, 1));

                //======== panel22 ========
                {
                    panel22.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel22.setLayout(new FormLayout(
                        "center:55dlu",
                        "fill:50dlu:grow, 2*($lgap, 50dlu:grow), $lgap, fill:50dlu:grow"));

                    //======== panel23 ========
                    {
                        panel23.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel23.setMinimumSize(new Dimension(50, 98));
                        panel23.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button20 ----
                        button20.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button20.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f2.gif")));
                        button20.setToolTipText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button20.setMinimumSize(new Dimension(50, 50));
                        button20.addActionListener(e -> deleteFromBRV(e));
                        panel23.add(button20, CC.xy(1, 1));
                    }
                    panel22.add(panel23, CC.xy(1, 3));

                    //======== panel24 ========
                    {
                        panel24.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel24.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button21 ----
                        button21.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button21.setMinimumSize(new Dimension(50, 50));
                        button21.setMargin(new Insets(0, 0, 0, 0));
                        button21.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f3.gif")));
                        button21.setToolTipText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0434\u0430\u043d\u0456 \u043f\u0440\u043e \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button21.addActionListener(e -> updateInBRV(e));
                        panel24.add(button21, CC.xy(1, 1));
                    }
                    panel22.add(panel24, CC.xy(1, 5));

                    //======== panel25 ========
                    {
                        panel25.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel25.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu"));

                        //---- button22 ----
                        button22.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button22.setMinimumSize(new Dimension(50, 50));
                        button22.setMargin(new Insets(0, 0, 0, 0));
                        button22.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f1.gif")));
                        button22.setToolTipText("\u0414\u043e\u0434\u0430\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button22.addActionListener(e -> insertIntoBRV(e));
                        panel25.add(button22, CC.xy(1, 1));
                    }
                    panel22.add(panel25, CC.xy(1, 1));

                    //======== panel26 ========
                    {
                        panel26.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel26.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu:grow"));

                        //---- button23 ----
                        button23.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button23.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f4.gif")));
                        button23.addActionListener(e -> refreshTableBRV(e));
                        panel26.add(button23, CC.xy(1, 1));
                    }
                    panel22.add(panel26, CC.xy(1, 7));
                }
                ЗАВОД4.add(panel22, CC.xy(3, 1));

                //======== panel27 ========
                {
                    panel27.setBorder(new EmptyBorder(5, 5, 5, 5));
                    panel27.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.setLayout(new FormLayout(
                        "55dlu, $lcgap, 70dlu:grow, $lcgap, default, $lcgap, 50dlu:grow",
                        "2*(default), $lgap, default, $lgap, min, 8*($lgap, default), 10dlu, default, $lgap, fill:default:grow, $lgap, default"));

                    //---- label36 ----
                    label36.setText("\u041f\u0430\u043d\u0435\u043b\u044c \u043f\u043e\u0448\u0443\u043a\u0443");
                    label36.setIcon(UIManager.getIcon("TextField.darcula.search.icon"));
                    label36.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label36, CC.xywh(1, 1, 7, 1));

                    //======== panel28 ========
                    {
                        panel28.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.setLayout(new FormLayout(
                            "${growing-button}, $lcgap, ${growing-button}",
                            "3*(default, $lgap), default"));

                        //---- checkBox26 ----
                        checkBox26.setText("\u041d\u043e\u043c\u0435\u0440");
                        checkBox26.setSelected(true);
                        checkBox26.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox26, CC.xy(1, 1));

                        //---- checkBox27 ----
                        checkBox27.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456");
                        checkBox27.setSelected(true);
                        checkBox27.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox27, CC.xy(3, 1));

                        //---- checkBox28 ----
                        checkBox28.setText("\u041d\u0443\u043a\u043b\u0456\u0434");
                        checkBox28.setSelected(true);
                        checkBox28.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox28, CC.xy(1, 3));

                        //---- checkBox29 ----
                        checkBox29.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c");
                        checkBox29.setSelected(true);
                        checkBox29.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox29, CC.xy(3, 3));

                        //---- checkBox30 ----
                        checkBox30.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
                        checkBox30.setSelected(true);
                        checkBox30.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox30, CC.xy(1, 5));

                        //---- checkBox31 ----
                        checkBox31.setText("\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c");
                        checkBox31.setSelected(true);
                        checkBox31.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox31, CC.xy(3, 5));

                        //---- checkBox32 ----
                        checkBox32.setText("\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f");
                        checkBox32.setSelected(true);
                        checkBox32.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox32, CC.xy(1, 7));

                        //---- checkBox33 ----
                        checkBox33.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
                        checkBox33.setSelected(true);
                        checkBox33.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel28.add(checkBox33, CC.xy(3, 7));
                    }
                    panel27.add(panel28, CC.xywh(1, 4, 7, 1));

                    //---- label37 ----
                    label37.setText("\u041d\u043e\u043c\u0435\u0440:");
                    label37.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label37, CC.xy(1, 8));

                    //---- firstNumber11 ----
                    firstNumber11.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber11.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber11.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel27.add(firstNumber11, CC.xy(3, 8));

                    //---- label38 ----
                    label38.setText("-");
                    label38.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label38, CC.xy(5, 8));

                    //---- lastNumber11 ----
                    lastNumber11.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(lastNumber11, CC.xy(7, 8));

                    //---- label39 ----
                    label39.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f: ");
                    label39.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label39, CC.xy(1, 10));

                    //---- comboBox10 ----
                    comboBox10.setModel(new DefaultComboBoxModel<>(new String[] {
                        "-",
                        "\u0421\u043b\u0430\u0431\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0435\u0440\u0435\u0434\u043d\u044c\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0438\u043b\u044c\u043d\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439"
                    }));
                    comboBox10.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(comboBox10, CC.xywh(3, 10, 5, 1));

                    //---- label40 ----
                    label40.setText("\u041d\u0443\u043a\u043b\u0456\u0434:");
                    label40.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label40, CC.xy(1, 12));

                    //---- comboBox11 ----
                    comboBox11.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(comboBox11, CC.xywh(3, 12, 5, 1));

                    //---- label41 ----
                    label41.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c:");
                    label41.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label41, CC.xy(1, 14));

                    //---- firstNumber12 ----
                    firstNumber12.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber12.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber12.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel27.add(firstNumber12, CC.xy(3, 14));

                    //---- label42 ----
                    label42.setText("-");
                    label42.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label42, CC.xy(5, 14));

                    //---- lastNumber12 ----
                    lastNumber12.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(lastNumber12, CC.xy(7, 14));

                    //---- label43 ----
                    label43.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430:");
                    label43.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label43, CC.xy(1, 16));

                    //---- firstNumber13 ----
                    firstNumber13.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber13.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber13.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel27.add(firstNumber13, CC.xy(3, 16));

                    //---- label44 ----
                    label44.setText("-");
                    label44.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label44, CC.xy(5, 16));

                    //---- lastNumber13 ----
                    lastNumber13.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(lastNumber13, CC.xy(7, 16));

                    //---- label45 ----
                    label45.setText("\u0417\u0430\u0433. \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c:");
                    label45.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label45, CC.xy(1, 18));

                    //---- firstNumber14 ----
                    firstNumber14.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(firstNumber14, CC.xy(3, 18));

                    //---- label46 ----
                    label46.setText("-");
                    label46.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label46, CC.xy(5, 18));

                    //---- lastNumber14 ----
                    lastNumber14.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(lastNumber14, CC.xy(7, 18));

                    //---- label47 ----
                    label47.setText("\u0414\u0430\u0442\u0430:");
                    label47.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label47, CC.xy(1, 20));

                    //---- dateChooser7 ----
                    dateChooser7.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(dateChooser7, CC.xy(3, 20));

                    //---- dateChooser8 ----
                    dateChooser8.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(dateChooser8, CC.xy(7, 20));

                    //---- label48 ----
                    label48.setText("\u0421\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438:");
                    label48.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(label48, CC.xy(1, 22));

                    //---- comboBox12 ----
                    comboBox12.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u041d\u043e\u043c\u0435\u0440",
                        "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456",
                        "\u041d\u0443\u043a\u043b\u0456\u0434",
                        "\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c",
                        "\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430",
                        "\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c",
                        "\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f",
                        "\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438"
                    }));
                    comboBox12.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(comboBox12, CC.xy(3, 22));

                    //---- checkBox34 ----
                    checkBox34.setText("\u041f\u043e \u0441\u043f\u0430\u0434\u0430\u043d\u043d\u044e");
                    checkBox34.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel27.add(checkBox34, CC.xy(7, 22));

                    //---- button24 ----
                    button24.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043f\u043e\u043b\u044f \u043f\u043e\u0448\u0443\u043a\u0443");
                    button24.setFont(new Font("Verdana", Font.PLAIN, 12));
                    button24.addActionListener(e -> searchCleanerBRV(e));
                    panel27.add(button24, CC.xywh(1, 24, 7, 1));
                }
                ЗАВОД4.add(panel27, CC.xy(5, 1));
            }
            tabbedPane1.addTab("\u0411\u0456\u043e\u043b\u043e\u0433\u0456\u0447\u043d\u0456 \u0420\u0410\u0412", ЗАВОД4);

            //======== ЗАВОД5 ========
            {
                ЗАВОД5.setMinimumSize(new Dimension(500, 200));
                ЗАВОД5.setPreferredSize(new Dimension(470, 200));
                ЗАВОД5.setFont(new Font("Verdana", Font.PLAIN, 12));
                ЗАВОД5.setMaximumSize(new Dimension(500, 200));
                ЗАВОД5.setLayout(new FormLayout(
                    "[320dlu,pref], $lcgap, default, $lcgap, 125dlu:grow",
                    "fill:225dlu:grow"));

                //======== scrollPane6 ========
                {
                    scrollPane6.setMinimumSize(new Dimension(700, 400));
                    scrollPane6.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- табл4 ----
                    табл4.setMinimumSize(new Dimension(30, 204));
                    табл4.setFont(new Font("Verdana", Font.PLAIN, 12));
                    scrollPane6.setViewportView(табл4);
                }
                ЗАВОД5.add(scrollPane6, CC.xy(1, 1));

                //======== panel29 ========
                {
                    panel29.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel29.setLayout(new FormLayout(
                        "center:55dlu",
                        "fill:50dlu:grow, 2*($lgap, 50dlu:grow), $lgap, fill:50dlu:grow"));

                    //======== panel30 ========
                    {
                        panel30.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel30.setMinimumSize(new Dimension(50, 98));
                        panel30.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button25 ----
                        button25.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button25.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f2.gif")));
                        button25.setToolTipText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button25.setMinimumSize(new Dimension(50, 50));
                        button25.addActionListener(e -> deleteFromDIV(e));
                        panel30.add(button25, CC.xy(1, 1));
                    }
                    panel29.add(panel30, CC.xy(1, 3));

                    //======== panel31 ========
                    {
                        panel31.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel31.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button26 ----
                        button26.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button26.setMinimumSize(new Dimension(50, 50));
                        button26.setMargin(new Insets(0, 0, 0, 0));
                        button26.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f3.gif")));
                        button26.setToolTipText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0434\u0430\u043d\u0456 \u043f\u0440\u043e \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button26.addActionListener(e -> updateInDIV(e));
                        panel31.add(button26, CC.xy(1, 1));
                    }
                    panel29.add(panel31, CC.xy(1, 5));

                    //======== panel32 ========
                    {
                        panel32.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel32.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu"));

                        //---- button27 ----
                        button27.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button27.setMinimumSize(new Dimension(50, 50));
                        button27.setMargin(new Insets(0, 0, 0, 0));
                        button27.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f1.gif")));
                        button27.setToolTipText("\u0414\u043e\u0434\u0430\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button27.addActionListener(e -> insertIntoDIV(e));
                        panel32.add(button27, CC.xy(1, 1));
                    }
                    panel29.add(panel32, CC.xy(1, 1));

                    //======== panel33 ========
                    {
                        panel33.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel33.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu:grow"));

                        //---- button28 ----
                        button28.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button28.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f4.gif")));
                        button28.addActionListener(e -> refreshTableDIV(e));
                        panel33.add(button28, CC.xy(1, 1));
                    }
                    panel29.add(panel33, CC.xy(1, 7));
                }
                ЗАВОД5.add(panel29, CC.xy(3, 1));

                //======== panel34 ========
                {
                    panel34.setBorder(new EmptyBorder(5, 5, 5, 5));
                    panel34.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.setLayout(new FormLayout(
                        "55dlu, $lcgap, 70dlu:grow, $lcgap, default, $lcgap, 50dlu:grow",
                        "2*(default), $lgap, default, $lgap, min, 8*($lgap, default), 10dlu, default, $lgap, fill:default:grow, $lgap, default"));

                    //---- label49 ----
                    label49.setText("\u041f\u0430\u043d\u0435\u043b\u044c \u043f\u043e\u0448\u0443\u043a\u0443");
                    label49.setIcon(UIManager.getIcon("TextField.darcula.search.icon"));
                    label49.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label49, CC.xywh(1, 1, 7, 1));

                    //======== panel35 ========
                    {
                        panel35.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.setLayout(new FormLayout(
                            "${growing-button}, $lcgap, ${growing-button}",
                            "3*(default, $lgap), default"));

                        //---- checkBox35 ----
                        checkBox35.setText("\u041d\u043e\u043c\u0435\u0440");
                        checkBox35.setSelected(true);
                        checkBox35.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox35, CC.xy(1, 1));

                        //---- checkBox36 ----
                        checkBox36.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456");
                        checkBox36.setSelected(true);
                        checkBox36.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox36, CC.xy(3, 1));

                        //---- checkBox37 ----
                        checkBox37.setText("\u041d\u0443\u043a\u043b\u0456\u0434");
                        checkBox37.setSelected(true);
                        checkBox37.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox37, CC.xy(1, 3));

                        //---- checkBox38 ----
                        checkBox38.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c");
                        checkBox38.setSelected(true);
                        checkBox38.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox38, CC.xy(3, 3));

                        //---- checkBox39 ----
                        checkBox39.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
                        checkBox39.setSelected(true);
                        checkBox39.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox39, CC.xy(1, 5));

                        //---- checkBox40 ----
                        checkBox40.setText("\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c");
                        checkBox40.setSelected(true);
                        checkBox40.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox40, CC.xy(3, 5));

                        //---- checkBox41 ----
                        checkBox41.setText("\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f");
                        checkBox41.setSelected(true);
                        checkBox41.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox41, CC.xy(1, 7));

                        //---- checkBox42 ----
                        checkBox42.setText("\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438");
                        checkBox42.setSelected(true);
                        checkBox42.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel35.add(checkBox42, CC.xy(3, 7));
                    }
                    panel34.add(panel35, CC.xywh(1, 4, 7, 1));

                    //---- label50 ----
                    label50.setText("\u041d\u043e\u043c\u0435\u0440:");
                    label50.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label50, CC.xy(1, 8));

                    //---- firstNumber15 ----
                    firstNumber15.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber15.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber15.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel34.add(firstNumber15, CC.xy(3, 8));

                    //---- label51 ----
                    label51.setText("-");
                    label51.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label51, CC.xy(5, 8));

                    //---- lastNumber15 ----
                    lastNumber15.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(lastNumber15, CC.xy(7, 8));

                    //---- label52 ----
                    label52.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f: ");
                    label52.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label52, CC.xy(1, 10));

                    //---- comboBox13 ----
                    comboBox13.setModel(new DefaultComboBoxModel<>(new String[] {
                        "-",
                        "\u0421\u043b\u0430\u0431\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0435\u0440\u0435\u0434\u043d\u044c\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439",
                        "\u0421\u0438\u043b\u044c\u043d\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439"
                    }));
                    comboBox13.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(comboBox13, CC.xywh(3, 10, 5, 1));

                    //---- label53 ----
                    label53.setText("\u041d\u0443\u043a\u043b\u0456\u0434:");
                    label53.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label53, CC.xy(1, 12));

                    //---- comboBox14 ----
                    comboBox14.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(comboBox14, CC.xywh(3, 12, 5, 1));

                    //---- label54 ----
                    label54.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c:");
                    label54.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label54, CC.xy(1, 14));

                    //---- firstNumber16 ----
                    firstNumber16.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber16.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber16.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel34.add(firstNumber16, CC.xy(3, 14));

                    //---- label55 ----
                    label55.setText("-");
                    label55.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label55, CC.xy(5, 14));

                    //---- lastNumber16 ----
                    lastNumber16.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(lastNumber16, CC.xy(7, 14));

                    //---- label56 ----
                    label56.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430:");
                    label56.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label56, CC.xy(1, 16));

                    //---- firstNumber17 ----
                    firstNumber17.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber17.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber17.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel34.add(firstNumber17, CC.xy(3, 16));

                    //---- label57 ----
                    label57.setText("-");
                    label57.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label57, CC.xy(5, 16));

                    //---- lastNumber17 ----
                    lastNumber17.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(lastNumber17, CC.xy(7, 16));

                    //---- label58 ----
                    label58.setText("\u0417\u0430\u0433. \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c:");
                    label58.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label58, CC.xy(1, 18));

                    //---- firstNumber18 ----
                    firstNumber18.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber18.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber18.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel34.add(firstNumber18, CC.xy(3, 18));

                    //---- label59 ----
                    label59.setText("-");
                    label59.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label59, CC.xy(5, 18));

                    //---- lastNumber18 ----
                    lastNumber18.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(lastNumber18, CC.xy(7, 18));

                    //---- label60 ----
                    label60.setText("\u0414\u0430\u0442\u0430:");
                    label60.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label60, CC.xy(1, 20));

                    //---- dateChooser9 ----
                    dateChooser9.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(dateChooser9, CC.xy(3, 20));

                    //---- dateChooser10 ----
                    dateChooser10.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(dateChooser10, CC.xy(7, 20));

                    //---- label61 ----
                    label61.setText("\u0421\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438:");
                    label61.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(label61, CC.xy(1, 22));

                    //---- comboBox15 ----
                    comboBox15.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u041d\u043e\u043c\u0435\u0440",
                        "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0456\u044f \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u0456",
                        "\u041d\u0443\u043a\u043b\u0456\u0434",
                        "\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c",
                        "\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430",
                        "\u0417\u0430\u0433\u0430\u043b\u044c\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c",
                        "\u0414\u0430\u0442\u0430 \u0441\u0442\u0432\u043e\u0440\u0435\u043d\u043d\u044f",
                        "\u041f\u0440\u0438\u043c\u0456\u0442\u043a\u0438"
                    }));
                    comboBox15.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(comboBox15, CC.xy(3, 22));

                    //---- checkBox43 ----
                    checkBox43.setText("\u041f\u043e \u0441\u043f\u0430\u0434\u0430\u043d\u043d\u044e");
                    checkBox43.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel34.add(checkBox43, CC.xy(7, 22));

                    //---- button29 ----
                    button29.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043f\u043e\u043b\u044f \u043f\u043e\u0448\u0443\u043a\u0443");
                    button29.setFont(new Font("Verdana", Font.PLAIN, 12));
                    button29.addActionListener(e -> searchCleanerDIV(e));
                    panel34.add(button29, CC.xywh(1, 24, 7, 1));
                }
                ЗАВОД5.add(panel34, CC.xy(5, 1));
            }
            tabbedPane1.addTab("\u0414\u0436\u0435\u0440\u0435\u043b\u0430 \u0456\u043e\u043d\u0456\u0437\u0443\u044e\u0447\u043e\u0433\u043e \u0432\u0438\u043f\u0440\u043e\u043c\u0456\u043d\u044e\u0432\u0430\u043d\u043d\u044f", ЗАВОД5);

            //======== ЗАВОД6 ========
            {
                ЗАВОД6.setMinimumSize(new Dimension(500, 200));
                ЗАВОД6.setPreferredSize(new Dimension(470, 200));
                ЗАВОД6.setFont(new Font("Verdana", Font.PLAIN, 12));
                ЗАВОД6.setMaximumSize(new Dimension(500, 200));
                ЗАВОД6.setLayout(new FormLayout(
                    "[320dlu,pref], $lcgap, default, $lcgap, 125dlu:grow",
                    "fill:225dlu:grow"));

                //======== scrollPane7 ========
                {
                    scrollPane7.setMinimumSize(new Dimension(700, 400));
                    scrollPane7.setFont(new Font("Verdana", Font.PLAIN, 12));

                    //---- табл5 ----
                    табл5.setMinimumSize(new Dimension(30, 204));
                    табл5.setFont(new Font("Verdana", Font.PLAIN, 12));
                    scrollPane7.setViewportView(табл5);
                }
                ЗАВОД6.add(scrollPane7, CC.xy(1, 1));

                //======== panel36 ========
                {
                    panel36.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel36.setLayout(new FormLayout(
                        "center:55dlu",
                        "fill:50dlu:grow, 2*($lgap, 50dlu:grow), $lgap, fill:50dlu:grow"));

                    //======== panel37 ========
                    {
                        panel37.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel37.setMinimumSize(new Dimension(50, 98));
                        panel37.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button30 ----
                        button30.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button30.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f2.gif")));
                        button30.setToolTipText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button30.setMinimumSize(new Dimension(50, 50));
                        button30.addActionListener(e -> deleteFromRADIO(e));
                        panel37.add(button30, CC.xy(1, 1));
                    }
                    panel36.add(panel37, CC.xy(1, 3));

                    //======== panel38 ========
                    {
                        panel38.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel38.setLayout(new FormLayout(
                            "default:grow",
                            "fill:60dlu:grow"));

                        //---- button31 ----
                        button31.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button31.setMinimumSize(new Dimension(50, 50));
                        button31.setMargin(new Insets(0, 0, 0, 0));
                        button31.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f3.gif")));
                        button31.setToolTipText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438 \u0434\u0430\u043d\u0456 \u043f\u0440\u043e \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button31.addActionListener(e -> updateInRADIO(e));
                        panel38.add(button31, CC.xy(1, 1));
                    }
                    panel36.add(panel38, CC.xy(1, 5));

                    //======== panel39 ========
                    {
                        panel39.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel39.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu"));

                        //---- button32 ----
                        button32.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button32.setMinimumSize(new Dimension(50, 50));
                        button32.setMargin(new Insets(0, 0, 0, 0));
                        button32.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f1.gif")));
                        button32.setToolTipText("\u0414\u043e\u0434\u0430\u0442\u0438 \u0440\u0430\u0434\u0456\u043e\u0430\u043a\u0442\u0438\u0432\u043d\u0438\u0439 \u0432\u0456\u0434\u0445\u0456\u0434");
                        button32.addActionListener(e -> insertIntoRADIO(e));
                        panel39.add(button32, CC.xy(1, 1));
                    }
                    panel36.add(panel39, CC.xy(1, 1));

                    //======== panel40 ========
                    {
                        panel40.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel40.setLayout(new FormLayout(
                            "55dlu:grow",
                            "fill:60dlu:grow"));

                        //---- button33 ----
                        button33.setFont(new Font("Verdana", Font.PLAIN, 12));
                        button33.setIcon(new ImageIcon(getClass().getResource("/\u0413\u041b\u0410\u0412\u041d\u041e\u0415_\u041e\u041a\u041d\u041e/gif/\u0440\u0430\u0434\u0438\u0430\u0446\u0438\u044f4.gif")));
                        button33.addActionListener(e -> refreshTableRADIO(e));
                        panel40.add(button33, CC.xy(1, 1));
                    }
                    panel36.add(panel40, CC.xy(1, 7));
                }
                ЗАВОД6.add(panel36, CC.xy(3, 1));

                //======== panel41 ========
                {
                    panel41.setBorder(new EmptyBorder(5, 5, 5, 5));
                    panel41.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.setLayout(new FormLayout(
                        "55dlu, $lcgap, 70dlu:grow, $lcgap, default, $lcgap, 50dlu:grow",
                        "2*(default), $lgap, default, $lgap, min, 6*($lgap, default), 10dlu, default, $lgap, fill:default:grow, $lgap, default"));

                    //---- label62 ----
                    label62.setText("\u041f\u0430\u043d\u0435\u043b\u044c \u043f\u043e\u0448\u0443\u043a\u0443");
                    label62.setIcon(UIManager.getIcon("TextField.darcula.search.icon"));
                    label62.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label62, CC.xywh(1, 1, 7, 1));

                    //======== panel42 ========
                    {
                        panel42.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel42.setLayout(new FormLayout(
                            "${growing-button}, $lcgap, ${growing-button}",
                            "2*(default, $lgap), default"));

                        //---- checkBox44 ----
                        checkBox44.setText("\u041d\u043e\u043c\u0435\u0440");
                        checkBox44.setSelected(true);
                        checkBox44.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel42.add(checkBox44, CC.xy(1, 1));

                        //---- checkBox45 ----
                        checkBox45.setText("\u041d\u0430\u0437\u0432\u0430");
                        checkBox45.setSelected(true);
                        checkBox45.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel42.add(checkBox45, CC.xy(3, 1));

                        //---- checkBox46 ----
                        checkBox46.setText("\u0421\u043a\u043e\u0440\u043e\u0447\u0435\u043d\u043e");
                        checkBox46.setSelected(true);
                        checkBox46.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel42.add(checkBox46, CC.xy(1, 3));

                        //---- checkBox47 ----
                        checkBox47.setText("\u041d\u043e\u043c\u0435\u0440 \u043f\u043e\u0440\u044f\u0434\u043a\u0443");
                        checkBox47.setSelected(true);
                        checkBox47.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel42.add(checkBox47, CC.xy(3, 3));

                        //---- checkBox48 ----
                        checkBox48.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430");
                        checkBox48.setSelected(true);
                        checkBox48.setFont(new Font("Verdana", Font.PLAIN, 12));
                        panel42.add(checkBox48, CC.xy(1, 5));
                    }
                    panel41.add(panel42, CC.xywh(1, 4, 7, 1));

                    //---- label63 ----
                    label63.setText("\u041d\u043e\u043c\u0435\u0440:");
                    label63.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label63, CC.xy(1, 8));

                    //---- firstNumber19 ----
                    firstNumber19.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber19.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber19.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel41.add(firstNumber19, CC.xy(3, 8));

                    //---- label64 ----
                    label64.setText("-");
                    label64.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label64, CC.xy(5, 8));

                    //---- lastNumber19 ----
                    lastNumber19.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(lastNumber19, CC.xy(7, 8));

                    //---- label65 ----
                    label65.setText("\u041d\u0430\u0437\u0432\u0430:");
                    label65.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label65, CC.xy(1, 10));

                    //---- textField1 ----
                    textField1.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(textField1, CC.xywh(3, 10, 5, 1));

                    //---- label66 ----
                    label66.setText("\u0421\u043e\u043a\u043e\u0440\u043e\u0447\u0435\u043d\u043e:");
                    label66.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label66, CC.xy(1, 12));

                    //---- textField2 ----
                    textField2.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(textField2, CC.xywh(3, 12, 5, 1));

                    //---- label67 ----
                    label67.setText("\u041a\u0456\u043b\u044c\u043a\u0456\u0441\u0442\u044c:");
                    label67.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label67, CC.xy(1, 14));

                    //---- firstNumber20 ----
                    firstNumber20.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(firstNumber20, CC.xy(3, 14));

                    //---- label68 ----
                    label68.setText("-");
                    label68.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label68, CC.xy(5, 14));

                    //---- lastNumber20 ----
                    lastNumber20.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(lastNumber20, CC.xy(7, 14));

                    //---- label69 ----
                    label69.setText("\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430:");
                    label69.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label69, CC.xy(1, 16));

                    //---- firstNumber21 ----
                    firstNumber21.setFont(new Font("Verdana", Font.PLAIN, 12));
                    firstNumber21.addActionListener(e -> textField1ActionPerformed(e));
                    firstNumber21.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            textField1KeyTyped(e);
                        }
                    });
                    panel41.add(firstNumber21, CC.xy(3, 16));

                    //---- label70 ----
                    label70.setText("-");
                    label70.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label70, CC.xy(5, 16));

                    //---- lastNumber21 ----
                    lastNumber21.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(lastNumber21, CC.xy(7, 16));

                    //---- label74 ----
                    label74.setText("\u0421\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438:");
                    label74.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(label74, CC.xy(1, 18));

                    //---- comboBox18 ----
                    comboBox18.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u041d\u043e\u043c\u0435\u0440",
                        "\u041d\u0430\u0437\u0432\u0430",
                        "\u0421\u043a\u043e\u0440\u043e\u0447\u0435\u043d\u043e",
                        "\u041d\u043e\u043c\u0435\u0440 \u043f\u043e\u0440\u044f\u0434\u043a\u0443",
                        "\u0410\u043a\u0442\u0438\u0432\u043d\u0456\u0441\u0442\u044c \u043d\u0443\u043a\u043b\u0456\u0434\u0430"
                    }));
                    comboBox18.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(comboBox18, CC.xy(3, 18));

                    //---- checkBox52 ----
                    checkBox52.setText("\u041f\u043e \u0441\u043f\u0430\u0434\u0430\u043d\u043d\u044e");
                    checkBox52.setFont(new Font("Verdana", Font.PLAIN, 12));
                    panel41.add(checkBox52, CC.xy(7, 18));

                    //---- button34 ----
                    button34.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043f\u043e\u043b\u044f \u043f\u043e\u0448\u0443\u043a\u0443");
                    button34.setFont(new Font("Verdana", Font.PLAIN, 12));
                    button34.addActionListener(e -> searchCleanerRADIO(e));
                    panel41.add(button34, CC.xywh(1, 20, 7, 1));
                }
                ЗАВОД6.add(panel41, CC.xy(5, 1));
            }
            tabbedPane1.addTab("\u0420\u0430\u0434\u0456\u043e\u043d\u0443\u043a\u043b\u0456\u0434\u0438", ЗАВОД6);

            //======== panel43 ========
            {
                panel43.setLayout(new FormLayout(
                    "default:grow, $lcgap, default, $lcgap, default:grow",
                    "3*(fill:default:grow, $lgap), fill:default:grow"));

                //======== scrollPane2 ========
                {

                    //---- table1 ----
                    table1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            table1MouseClicked(e);
                        }
                    });
                    scrollPane2.setViewportView(table1);
                }
                panel43.add(scrollPane2, CC.xywh(1, 1, 1, 7));

                //---- button6 ----
                button6.setText("\u0414\u043e\u0434\u0430\u0442\u0438");
                button6.addActionListener(e -> addUSER(e));
                panel43.add(button6, CC.xy(3, 1));

                //======== panel44 ========
                {
                    panel44.setBorder(new TitledBorder("\u0414\u0430\u043d\u0456 \u043a\u043e\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0430"));
                    panel44.setLayout(new FormLayout(
                        "2*(default, $lcgap), default:grow, $lcgap, default",
                        "4*(default, $lgap), default"));

                    //---- label71 ----
                    label71.setText("Login");
                    panel44.add(label71, CC.xy(3, 3));
                    panel44.add(textField3, CC.xy(5, 3));

                    //---- label72 ----
                    label72.setText("Password");
                    panel44.add(label72, CC.xy(3, 5));
                    panel44.add(textField4, CC.xy(5, 5));

                    //---- label73 ----
                    label73.setText("User group");
                    panel44.add(label73, CC.xy(3, 7));

                    //---- comboBox16 ----
                    comboBox16.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u0410\u0434\u043c\u0456\u043d",
                        "\u0413\u043e\u043b\u043e\u0432\u043d\u0438\u0439 \u0456\u043d\u0436\u0435\u043d\u0435\u0440",
                        "\u041f\u0440\u0430\u0446\u0456\u0432\u043d\u0438\u043a"
                    }));
                    panel44.add(comboBox16, CC.xy(5, 7));

                    //---- label75 ----
                    label75.setText("0");
                    panel44.add(label75, CC.xy(3, 9));
                }
                panel43.add(panel44, CC.xywh(5, 1, 1, 7));

                //---- button7 ----
                button7.setText("\u0412\u0438\u0434\u0430\u043b\u0438\u0442\u0438");
                button7.addActionListener(e -> deleteUSER(e));
                panel43.add(button7, CC.xy(3, 3));

                //---- button8 ----
                button8.setText("\u0417\u043c\u0456\u043d\u0438\u0442\u0438");
                button8.addActionListener(e -> updateUSER(e));
                panel43.add(button8, CC.xy(3, 5));

                //---- button9 ----
                button9.setText("\u041e\u043d\u043e\u0432\u0438\u0442\u0438");
                button9.addActionListener(e -> refreshTableUSER(e));
                panel43.add(button9, CC.xy(3, 7));
            }
            tabbedPane1.addTab("\u041a\u043e\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0456", panel43);
        }
        contentPane.add(tabbedPane1, CC.xy(1, 1, CC.FILL, CC.FILL));
        setSize(1030, 490);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
