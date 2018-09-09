package ogr.user12043.opencv.test.basics;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import ogr.user12043.opencv.test.cameraTest.CameraTest;
import ogr.user12043.opencv.test.cameraTest.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created on 08.09.2018 - 22:21
 * part of opencv-test
 *
 * @author user12043
 */
public class BasicsController {
    private ScheduledExecutorService service;
    private CameraTest cameraTest;
    @FXML
    private Button button_control;

    @FXML
    private ImageView imageView_display;

    @FXML
    private CheckBox checkBox_grayScale;

    @FXML
    private CheckBox checkBox_addLogo;

    public BasicsController() {
        cameraTest = new CameraTest(Constants.CAMERA_INDEX);
    }

    @FXML
    public void controlCamera(ActionEvent event) {
        if (!cameraTest.isRunning()) {
            initService();
            button_control.setText("Stop Camera");
        } else {
            stopService();
            button_control.setText("Start Camera");
        }
    }

    @FXML
    public void controlGrayScale(ActionEvent event) {
        cameraTest.setGrayScale(checkBox_grayScale.isSelected());
    }

    @FXML
    public void controlAddLogo(ActionEvent event) {
        if (checkBox_addLogo.isSelected()) {
            cameraTest.setLogo("res/img/resim2.png");
        } else {
            cameraTest.setLogo("");
        }
    }

    private void update() {
        imageView_display.setImage(SwingFXUtils.toFXImage(cameraTest.getImage(), null));
    }

    private void initService() {
        cameraTest.start();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::update, 0, 33, TimeUnit.MILLISECONDS);
    }

    void stopService() {
        if (cameraTest.isRunning()) {
            cameraTest.end();
        }

        if (service != null && !service.isShutdown()) {
            service.shutdownNow();
        }
    }
}
