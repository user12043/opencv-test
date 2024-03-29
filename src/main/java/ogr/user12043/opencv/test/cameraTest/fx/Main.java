package ogr.user12043.opencv.test.cameraTest.fx;

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

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("opencvTest.fxml"));
        BorderPane rootPane = loader.load();
        OpencvTestController controller = loader.getController();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Exiting...");
            controller.endService();
        });
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("OpenCV Java - Camera Test");
        primaryStage.show();
    }
}
