package shipspack.ships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ShipBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);

        stage.setTitle("Ships (beta v0.1)");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("Ships_ico.png"));
        stage.setMaximized(false);
        stage.resizableProperty().set(true);


    }

    public static void main(String[] args) {
        launch();
    }
}