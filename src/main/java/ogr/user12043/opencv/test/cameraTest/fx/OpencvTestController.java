package ogr.user12043.opencv.test.cameraTest.fx;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ogr.user12043.opencv.test.Utils;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
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
    private VideoCapture videoCapture;
    private ScheduledExecutorService service;
    @FXML
    private Button btn_control;
    @FXML
    private ImageView imgView_display;
    @FXML
    private TextField textField_sourceLocation;

    public OpencvTestController() {
        videoCapture = new VideoCapture();
    }

    @FXML
    public void controlCamera(ActionEvent event) {
        if (videoCapture.isOpened()) {
            endService();
            btn_control.setText("Start Camera");
        } else {
            initService();
            btn_control.setText("Stop Camera");
        }
    }

    private void update() {
        Mat mat = new Mat();
        videoCapture.read(mat);
        BufferedImage frame = Utils.matToBufferedImage(mat);
        imgView_display.setImage(SwingFXUtils.toFXImage(frame, null));
    }

    private void initService() {
        final String sourceLocation = textField_sourceLocation.getText();
        Utils.initVideoCapture(videoCapture, sourceLocation);
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::update, 0, 33, TimeUnit.MILLISECONDS);
    }

    void endService() {
        if (videoCapture.isOpened()) {
            service.shutdownNow();
            try {
                service.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                System.err.println("Can not stop the frame!");
            }
            videoCapture.release();
        }
    }
}
