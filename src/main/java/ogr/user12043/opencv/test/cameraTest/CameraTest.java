package ogr.user12043.opencv.test.cameraTest;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Created on 05.09.2018 - 22:00
 * part of opencv-test
 *
 * @author user12043
 */
public class CameraTest {
    private VideoCapture videoCapture;
    private Mat frame;
    private BufferedImage bufferedImage;
    private File photoOutputFile;
    private int cameraIndex;
    private boolean grayScale = false;
    private Mat logo;


    public CameraTest(int cameraIndex) {
        this.videoCapture = new VideoCapture();
        this.cameraIndex = cameraIndex;
        frame = new Mat();
        photoOutputFile = new File(Constants.PHOTO_OUTPUT_PATH);
        photoOutputFile.mkdirs();
    }

    private void updateImage() {
        if (videoCapture.isOpened()) {
            videoCapture.read(frame);
            if (logo != null) { // TODO not working
                Rect roi = new Rect(frame.cols() - logo.cols(), frame.rows() - logo.rows(), logo.cols(), logo.rows());
                Mat imageRoi = frame.submat(roi);

                // method 1
                Core.addWeighted(imageRoi, 1.0, logo, 0.7, 0.0, imageRoi);

                //method 2
//                Mat mask = logo.clone();
//                logo.copyTo(imageRoi, mask);

//                frame = imageRoi;
            }

            if (grayScale) {
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
            }
            bufferedImage = matToBufferedImage(frame);
        }
    }

    public void savePhoto() throws IOException {
        if (videoCapture.isOpened()) {
            updateImage();
            ImageIO.write(bufferedImage, "png", photoOutputFile);
        }
    }

    public BufferedImage getImage() {
        if (videoCapture.isOpened()) {
            updateImage();
            return bufferedImage;
        } else {
            throw new IllegalStateException("Camera has not opened yet");
        }
    }

    private BufferedImage matToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

    public void start() {
        videoCapture.open(cameraIndex);
    }

    public void end() {
        videoCapture.release();
    }

    public boolean isRunning() {
        return videoCapture.isOpened();
    }

    public void setGrayScale(boolean grayScale) {
        this.grayScale = grayScale;
    }

    public void setLogo(String logoPath) {
        if (logoPath != null && !logoPath.isEmpty()) {
            File file = new File(logoPath);
            if (file.exists()) {
                this.logo = Imgcodecs.imread(logoPath);
            } else {
                System.err.println("logoPath is not valid!");
                System.err.println("Logo path: " + logoPath);
                System.err.println("Working dir: " + System.getProperty("user.dir"));
                this.logo = null;
            }
        } else {
            this.logo = null;
        }
    }
}
