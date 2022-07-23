import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

//        Map<String, Object[]> data = new HashMap<String, Object[]>();
//        data.put("34", new Object[] {34d, "Test", "Test2"});

    }

    public void setMarkets(ArrayList<String> markets) {
        for (int i = 0; i < markets.size(); i++) {
            indexCombobox.getItems().add(markets.get(i));
        }
    }
}
