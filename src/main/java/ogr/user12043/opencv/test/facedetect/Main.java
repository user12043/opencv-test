package ogr.user12043.opencv.test.facedetect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 * Created on 24.12.2019 - 09:41
 * part of opencv-test
 *
 * @author user12043
 */
public class Main extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("facedetect.fxml"));
        BorderPane rootPane = loader.load();
        FaceDetectController controller = loader.getController();
        Scene scene = new Scene(rootPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("OpenCV Java - Face Detection and Tracking");
        primaryStage.show();
        Main.primaryStage = primaryStage;
    }
}
