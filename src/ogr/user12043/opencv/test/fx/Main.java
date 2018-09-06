package ogr.user12043.opencv.test.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created on 06.09.2018 - 14:32
 * part of opencv-test
 *
 * @author user12043
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("opencv-test.fxml"));
        BorderPane rootPane = loader.load();
        OpencvTestController controller = loader.getController();
    }
}
