package ogr.user12043.opencv.test.basics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 * Created on 08.09.2018 - 22:48
 * part of opencv-test
 *
 * @author user12043
 */
public class Main extends Application {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("basics.fxml"));
        BorderPane rootPane = loader.load(); // First load. (loading after getController will cause NullPointerException)
        BasicsController controller = loader.getController();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Exiting...");
            controller.stopService();
        });

        Scene scene = new Scene(rootPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("OpenCV Java - Basics");
        primaryStage.show();
    }
}
