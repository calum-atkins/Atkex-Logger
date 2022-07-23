import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    ComboBox indexCombobox;

    @FXML
    Label lblLabel;

    @FXML
    Button btnAdd;

    @FXML
    TextField txtUnits;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexCombobox.getItems().clear();

    }

    @FXML void addClick() {
        System.out.println(indexCombobox.getSelectionModel().getSelectedItem().toString() + " "+ txtUnits.getText());


        File myFile = new File("src/main/resources/spreadsheets/AUDCAD.xlsx");

        try {

            FileInputStream fis = new FileInputStream(myFile);


            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

            //Get sheet
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            Map<String, Object[]> data = new HashMap<String, Object[]>();
            data.put("34", new Object[] {34d, "Test", "Test2"});

            // Set to Iterate and add rows into XLS file
            Set<String> newRows = data.keySet();
            // get the last row number to append new data
            int rownum = mySheet.getLastRowNum();
            for (String key : newRows) {
                // Creating a new Row in existing XLSX sheet
                Row row = mySheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }
                }
            }
            FileOutputStream os = new FileOutputStream(myFile);
            myWorkBook.write(os);
            System.out.println("Writing on XLSX file Finished ...");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setMarkets(ArrayList<String> markets) {
        for (int i = 0; i < markets.size(); i++) {
            indexCombobox.getItems().add(markets.get(i));
        }
    }
}