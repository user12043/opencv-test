package ogr.user12043.opencv.test.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

/**
 * Created on 06.09.2018 - 14:32
 * part of opencv-test
 *
 * @author user12043
 */
public class Main extends Application {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    public void start(Stage stage) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("opencvTest.fxml"));
        BorderPane rootPane = loader.load();
        OpencvTestController controller = loader.getController();
        stage.setOnCloseRequest(event -> {
            System.out.println("Exiting...");
            controller.endService();
        });
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setTitle("Camera");
        stage.show();
    }
}
