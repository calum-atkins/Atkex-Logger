import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    ComboBox indexCombobox;
    @FXML
    ComboBox sideCombobox;

    @FXML
    Label lblLabel;

    @FXML
    DatePicker dpiOrderDate;

    @FXML
    Button btnAdd;

    @FXML
    TextField txtUnits;
    @FXML
    TextField txtStartPrice;
    @FXML
    TextField txtStopLossPrice;
    @FXML
    TextField txtTakeProfitPrice;
    @FXML
    TextField txtStopLossProfit;
    @FXML
    TextField txtTakeProfitProfit;
    @FXML
    TextField txtOrderTime;

    @FXML
    TextArea txtDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexCombobox.getItems().clear();

        sideCombobox.getItems().addAll("Long","Short");

    }

    @FXML void addClick() {
        System.out.println(indexCombobox.getSelectionModel().getSelectedItem().toString() + " "+ txtUnits.getText());

        System.out.println("src/main/resources/spreadsheets/" + indexCombobox.getSelectionModel().getSelectedItem().toString());
        File myFile = new File("src/main/resources/spreadsheets/" + indexCombobox.getSelectionModel().getSelectedItem().toString());

        try {

            FileInputStream fis = new FileInputStream(myFile);

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

            //Get sheet
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            //Assign values to object
            Map<String, Object[]> data = new HashMap<String, Object[]>();
            data.put("1", new Object[] {
                    txtUnits.getText(),
                    sideCombobox.getSelectionModel().getSelectedItem().toString(),
                    txtStartPrice.getText(),
                    txtStopLossPrice.getText(),
                    txtTakeProfitPrice.getText(),
                    "=",
                    "=",
                    txtStopLossProfit.getText(),
                    txtTakeProfitProfit.getText(),
                    "Open",
                    dpiOrderDate.toString(),
                    txtOrderTime.getText(),
                    "Date",
                    "Time",
                    "=",
                    "=",
                    "=",
                    txtDescription.getText()
            });

            // Set to Iterate and add rows into XLS file
            Set<String> newRows = data.keySet();

            // get the last row number to append new data
            int rownum = mySheet.getLastRowNum() + 1;
            System.out.println(rownum);

            FormulaEvaluator evaluator = myWorkBook.getCreationHelper().createFormulaEvaluator();

            for (String key : newRows) {
                // Creating a new Row in existing XLSX sheet
                Row row = mySheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if(obj.toString().startsWith("=")) {
                        //String formula = obj.toString().substring(1);
                        switch (cellnum) {
                            case 6 :
                                if (sideCombobox.getSelectionModel().getSelectedItem().toString().equals("Long")) {
                                    cell.setCellFormula(txtStopLossPrice.getText() + "-" + txtStartPrice.getText());
                                } else {
                                    cell.setCellFormula(txtStartPrice.getText() + "-" + txtStopLossPrice.getText());
                                }
                                break;
                            case 7:
                                if (sideCombobox.getSelectionModel().getSelectedItem().toString().equals("Long")) {
                                    cell.setCellFormula(txtTakeProfitPrice.getText() + "-" + txtStartPrice.getText());
                                } else {
                                    cell.setCellFormula(txtStartPrice.getText() + "-" + txtTakeProfitPrice.getText());
                                }
                                break;
                            case 15:
                                //cell.setCellFormula("DAYS(M" + rownum + "," + "K" + rownum + ")");
                                System.out.println("Date");
                                //cell.setCellFormula(formula);
                                break;
                            case 16:
                                cell.setCellFormula("IF(J" + rownum + "=\"SL\",F" + rownum + ",IF(J" + rownum + "=\"TP\",G" + rownum + ",\"Open\"))");
                                break;
                            case 17:
                                cell.setCellFormula("IF(J" + rownum + "=\"SL\",H" + rownum + ",IF(J" + rownum + "=\"TP\",I" + rownum + ",\"Open\"))");
                                break;
                        }
                    }

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
            evaluator.evaluateAll();
            myWorkBook.write(os);
            System.out.println("Writing on XLSX file Finished ...");

            os.close();
            myWorkBook.close();
            fis.close();

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