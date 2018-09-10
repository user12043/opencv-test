package ogr.user12043.opencv.test.basics;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import ogr.user12043.opencv.test.Utils;
import ogr.user12043.opencv.test.Constants;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.File;
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
    private VideoCapture videoCapture;
    private boolean grayScale;
    private Mat logo;
    @FXML
    private Button button_control;

    @FXML
    private ImageView imageView_display;

    @FXML
    private CheckBox checkBox_grayScale;

    @FXML
    private CheckBox checkBox_addLogo;

    public BasicsController() {
        videoCapture = new VideoCapture();
    }

    @FXML
    public void controlCamera(ActionEvent event) {
        if (!videoCapture.isOpened()) {
            initService();
            button_control.setText("Stop Camera");
        } else {
            stopService();
            button_control.setText("Start Camera");
        }
    }

    @FXML
    public void controlGrayScale(ActionEvent event) {
        this.grayScale = checkBox_grayScale.isSelected();
    }

    @FXML
    public void controlAddLogo(ActionEvent event) {
        if (checkBox_addLogo.isSelected()) {
            File file = new File(Constants.LOGO_PATH);
            if (file.exists()) {
                this.logo = Imgcodecs.imread(Constants.LOGO_PATH);
            } else {
                System.err.println("logoPath is not valid!");
                System.err.println("Logo path: " + file.getAbsolutePath());
                System.err.println("Working dir: " + System.getProperty("user.dir"));
                this.logo = null;
            }
        } else {
//            cameraTest.setLogo("");
            this.logo = null;
        }
    }

    private void update() {
        Mat frame = new Mat();
        videoCapture.read(frame);

        if (this.logo != null) {
            Rect roi = new Rect(frame.cols() - logo.cols(), frame.rows() - logo.rows(), logo.cols(), logo.rows());
            Mat imageRoi = frame.submat(roi);

            // method 1
            Core.addWeighted(imageRoi, 1.0d, logo, 1.0d, -5.0d, imageRoi);

            // method 2
//                Mat mask = logo.clone();
//                logo.copyTo(imageRoi, mask);
        }

        if (this.grayScale) {
            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        }

        imageView_display.setImage(SwingFXUtils.toFXImage(Utils.matToBufferedImage(frame), null));
//        imageView_display.setImage(SwingFXUtils.toFXImage(cameraTest.getImage(), null));
    }

    private void initService() {
        videoCapture.open(Constants.CAMERA_INDEX);
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::update, 0, 33, TimeUnit.MILLISECONDS);
    }

    void stopService() {
        if (service != null && !service.isShutdown()) {
            service.shutdownNow();
            try {
                service.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                System.err.println("Can not stop the frame");
            }
        }

        if (videoCapture.isOpened()) {
            videoCapture.release();
        }
    }
}
