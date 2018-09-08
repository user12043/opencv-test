package ogr.user12043.opencv.test.cameraTest.fx;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import ogr.user12043.opencv.test.cameraTest.CameraTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created on 06.09.2018 - 14:36
 * part of opencv-test
 *
 * @author user12043
 */
public class OpencvTestController {
    private CameraTest cameraTest;
    private ScheduledExecutorService service;
    @FXML
    private Button btn_control;
    @FXML
    private ImageView imgview_display;

    public OpencvTestController() {
        cameraTest = new CameraTest(0);
    }

    @FXML
    public void controlCamera(ActionEvent event) {
        if (cameraTest.isRunning()) {
            endService();
            btn_control.setText("Start Camera");
        } else {
            initService();
            btn_control.setText("Stop Camera");
        }
    }

    private void update() {
        imgview_display.setImage(SwingFXUtils.toFXImage(cameraTest.getImage(), null));
    }

    private void initService() {
        cameraTest.start();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::update, 0, 33, TimeUnit.MILLISECONDS);
    }

    public void endService() {
        if (cameraTest.isRunning()) {
            service.shutdown();
            cameraTest.end();
        }
    }
}
