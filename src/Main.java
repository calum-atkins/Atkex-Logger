import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Initialising..");
        stage.setTitle("Atkex");
        stage.setResizable(true);


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
