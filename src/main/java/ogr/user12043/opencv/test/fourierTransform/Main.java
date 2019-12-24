package ogr.user12043.opencv.test.fourierTransform;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 * Created on 23.07.2019 - 18:38
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

    public static Stage getStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fourier.fxml"));
        BorderPane rootPane = loader.load();
        FourierController controller = loader.getController();
        Scene scene = new Scene(rootPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("OpenCV Java - Fourier Transform");
        primaryStage.show();
        Main.primaryStage = primaryStage;
    }
}
