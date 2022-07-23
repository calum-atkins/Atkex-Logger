import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    /** Constant variables for convenient use */
    private final String rawDataLocation = "C:\\Users\\calum\\OneDrive\\Documents\\Trading\\Atkex Logger\\src\\main\\resources\\spreadsheets";

    ArrayList<String> markets = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Initialising..");
        stage.setTitle("Atkex");
        stage.setResizable(true);

        //Get data from spreadsheets (folder)
        File directoryP = new File(rawDataLocation);
        String contents[] = directoryP.list();
        for (int i = 0; i < contents.length; i++) {
            markets.add(contents[i]);
        }


        System.out.println("Displaying UI.");
        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.show();
    }

    public static void main(String []args) {
        launch(args);
    }

    /**
     * Loads the main pane for the UI.
     * Sets up chart selector switching the markets chart.
     * Creates all the charts that can be displayed.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        "mainScreen.fxml"
                )
        );

        mainPane.setPrefHeight(800);
        mainPane.setPrefWidth(1200);
        MainController mainController = loader.getController();

        mainController.setMarkets(markets);

        return mainPane;
    }

    /**
     * Creates the main application screen.
     *
     * @param mainPane the main application layout.
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );

        scene.getStylesheets().setAll(
                getClass().getResource("styles/style.css").toExternalForm()
        );

        return scene;
    }
}
