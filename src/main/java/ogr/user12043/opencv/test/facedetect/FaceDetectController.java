package ogr.user12043.opencv.test.facedetect;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
 * Created on 24.12.2019 - 09:43
 * part of opencv-test
 *
 * @author user12043
 */
public class FaceDetectController {
    private VideoCapture videoCapture;
    private ScheduledExecutorService service;

    @FXML
    private Button btn_toggleCamera;

    @FXML
    private ImageView imgView_display;

    @FXML
    private RadioButton radioBtn_haar;

    @FXML
    private RadioButton radioBtn_lbp;

    @FXML
    private TextField textField_sourceLocation;


    public FaceDetectController() {
        videoCapture = new VideoCapture();
    }

    @FXML
    private void toggleCamera(ActionEvent event) {
        if (videoCapture.isOpened()) {
            endService();
            btn_toggleCamera.setText("Start Camera");
        } else {
            initService();
            btn_toggleCamera.setText("Stop Camera");
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
