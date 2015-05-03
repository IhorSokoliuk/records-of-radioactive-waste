/*
 * Created by JFormDesigner on Mon Dec 15 03:29:26 EET 2014
 */

package ГЛАВНОЕ_ОКНО;

import java.awt.*;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.TableModel;
import javax.swing.text.TableView;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jxl.Workbook;
import jxl.write.*;

/**
 * @author Igor
 */
public class export extends JFrame {

    String path;
    ArrayList<JTable> tblv;

    public export(ArrayList<JTable> tblv) {
        initComponents();
        this.tblv = tblv;
        fileChooser1.setFileFilter(new myFileFilter(".pdf", "PDF-files"));
        fileChooser1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser1.setApproveButtonText("Сохранить");
        fileChooser1.setDialogType(JFileChooser.SAVE_DIALOG);
    }

    private void intoPDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            document.addTitle("Таблиці радіоактивних відходів");
            document.addSubject("Звіт");
            document.addKeywords("завод, ТРВ, БРВ, РРВ, ДІВ, радіоактивність, радіація");
            document.addAuthor("Соколюк Ігор");
            document.addCreator("Студент групи ТІ-21, 3 курс");

            addTable(document, tblv.get(0).getModel(), "Завод");
            addTable(document, tblv.get(1).getModel(), "ТРВ");
            addTable(document, tblv.get(2).getModel(), "РРВ");
            addTable(document, tblv.get(3).getModel(), "БРВ");
            addTable(document, tblv.get(4).getModel(), "ДІВ");
            addTable(document, tblv.get(5).getModel(), "Радіонукліди");

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

    private void intoExcel() {
        try {

            WritableWorkbook workbook = Workbook.createWorkbook(new File(path));

            writeToExcel(workbook, tblv.get(0).getModel(), "Завод", 0);
            writeToExcel(workbook, tblv.get(1).getModel(), "ТРВ", 1);
            writeToExcel(workbook, tblv.get(2).getModel(), "РРВ", 2);
            writeToExcel(workbook, tblv.get(3).getModel(), "БРВ", 3);
            writeToExcel(workbook, tblv.get(4).getModel(), "ДІВ", 4);
            writeToExcel(workbook, tblv.get(5).getModel(), "Раіоактивні нукліди", 5);

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
            jxl.write.Label column = new jxl.write.Label(i, 0, model.getColumnName(i));
            sheet.addCell(column);
        }
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                jxl.write.Label row = new jxl.write.Label(j, i + 1,
                        model.getValueAt(i, j).toString());
                sheet.addCell(row);
            }
        }
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void fileChooser1ActionPerformed(ActionEvent e) {
        path = fileChooser1.getSelectedFile().getAbsolutePath();
        if(radioButton1.isSelected()) {
            if (!path.endsWith(".pdf"))
                path += ".pdf";
            intoPDF();
        }
        else{
            if (!path.endsWith(".xls"))
                path += ".xls";
            intoExcel();
        }
    }

    private void radioButton1ActionPerformed(ActionEvent e) {
        fileChooser1.setFileFilter(new myFileFilter(".pdf", "PDF-files"));
    }

    private void radioButton2ActionPerformed(ActionEvent e) {
        fileChooser1.setFileFilter(new myFileFilter(".xls", "XLS-files"));
    }

    public class myFileFilter extends javax.swing.filechooser.FileFilter {
        String ext,description;

        public String getDescription() {
            return description;
        }

        myFileFilter(String ext, String description) {
            this.ext = ext;
            this.description = description;
        }
        //В этом методе может быть любая проверка файла
        public boolean accept(File f) {
            if(f != null) {
                if(f.isDirectory()) {
                    return true;
                }

                return f.toString().endsWith(ext);
            }
            return false;
        }



    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        fileChooser1 = new JFileChooser();

        //======== this ========
        setTitle("\u0415\u043a\u0441\u043f\u043e\u0440\u0442 \u0434\u0430\u043d\u0438\u0445 \u0443 \u0444\u0430\u0439\u043b");
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "[333dlu,default]:grow",
            "fill:[237dlu,default]:grow"));

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.setLayout(new FormLayout(
                "[158dlu,default]:grow, $lcgap, default, $lcgap, 145dlu:grow",
                "2*(default, $lgap), fill:[201dlu,default]:grow"));

            //---- label1 ----
            label1.setText("\u0423 \u044f\u043a\u0438\u0439 \u0444\u043e\u0440\u043c\u0430\u0442 \u0431\u0443\u0434\u0435\u0442\u0435 \u0435\u043a\u0441\u043f\u043e\u0440\u0442\u0443\u0432\u0430\u0442\u0438?");
            panel1.add(label1, CC.xywh(1, 1, 5, 1));

            //---- radioButton1 ----
            radioButton1.setText("PDF");
            radioButton1.setSelected(true);
            radioButton1.addActionListener(e -> radioButton1ActionPerformed(e));
            panel1.add(radioButton1, CC.xy(1, 3));

            //---- radioButton2 ----
            radioButton2.setText("Excel");
            radioButton2.addActionListener(e -> radioButton2ActionPerformed(e));
            panel1.add(radioButton2, CC.xy(5, 3));

            //---- fileChooser1 ----
            fileChooser1.setAcceptAllFileFilterUsed(false);
            fileChooser1.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser1.setFont(new Font("Verdana", Font.PLAIN, 12));
            fileChooser1.addActionListener(e -> fileChooser1ActionPerformed(e));
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
