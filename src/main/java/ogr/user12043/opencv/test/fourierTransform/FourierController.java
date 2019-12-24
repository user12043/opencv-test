package ogr.user12043.opencv.test.fourierTransform;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ogr.user12043.opencv.test.Utils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 23.07.2019 - 18:22
 * part of opencv-test
 *
 * @author user12043
 */
public class FourierController {
    @FXML
    private ImageView imgView_original;
    @FXML
    private ImageView imgView_transformed;
    @FXML
    private ImageView imgView_antiTransformed;
    @FXML
    private Button btn_loadImage;
    @FXML
    private Button btn_transform;
    @FXML
    private Button btn_antiTransform;
    private FileChooser fileChooser;
    private Mat image;
    private List<Mat> planes;

    public FourierController() {
        this.fileChooser = new FileChooser();
        this.planes = new ArrayList<>();
    }

    @FXML
    public void loadImage(ActionEvent event) {
        File file = new File("res");
        this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(Main.getStage());
        if (file != null && file.exists()) {
            this.image = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
            this.imgView_original.setImage(SwingFXUtils.toFXImage(Utils.matToBufferedImage(this.image), null));
        }
    }

    private Mat getOptimizedImageDim() {
        Mat padded = new Mat();
        int addRows = Core.getOptimalDFTSize(image.rows());
        int addCols = Core.getOptimalDFTSize(image.cols());
        Core.copyMakeBorder(this.image, padded, addRows - image.rows(), 0, addRows - image.rows(), 0, Core.BORDER_CONSTANT, Scalar.all(0));
        return padded;
    }

    @FXML
    private void transform(ActionEvent event) {
        Mat padded = getOptimizedImageDim();
        padded.convertTo(padded, CvType.CV_32F);
        this.planes.add(padded);
        this.planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        Mat complexImage = new Mat();
        Core.merge(planes, complexImage);
        Core.dft(complexImage, complexImage);
        List<Mat> newPlanes = new ArrayList<>();
        Core.split(complexImage, newPlanes);
        Mat mag = new Mat();
        Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
        Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
        Core.log(mag, mag);
        this.shiftDFT(mag);
        Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX);
        mag.convertTo(mag, CvType.CV_8UC1);
        this.imgView_transformed.setImage(SwingFXUtils.toFXImage(Utils.matToBufferedImage(mag), null));
    }

    private void shiftDFT(Mat image) {
        image = image.submat(new Rect(0, 0, image.cols() & -2, image.rows() & -2));
        int cx = image.cols() / 2;
        int cy = image.rows() / 2;

        Mat q0 = new Mat(image, new Rect(0, 0, cx, cy));
        Mat q1 = new Mat(image, new Rect(cx, 0, cx, cy));
        Mat q2 = new Mat(image, new Rect(0, cy, cx, cy));
        Mat q3 = new Mat(image, new Rect(cx, cy, cx, cy));

        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);

        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
    }
}
