package ogr.user12043.opencv.test.swing;

import org.opencv.core.Mat;
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

    public CameraTest(int cameraIndex) {
        this.videoCapture = new VideoCapture();
        this.cameraIndex = cameraIndex;
        frame = new Mat();
        photoOutputFile = new File(Constants.PHOTO_OUTPUT_PATH);
    }

    private void updateImage() {
        if (videoCapture.isOpened()) {
            videoCapture.read(frame);
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

    /*public void displayOnFrame(JFrame displayFrame) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            updateImage();
//            SwingUtilities.invokeLater(() -> {
            if (displayFrame != null) {
//                displayFrame.update(bufferedImage);
            }
//            });
        }, 0, 33, TimeUnit.MILLISECONDS);
    }*/

    public void start() {
        videoCapture.open(cameraIndex);
    }

    public void end() {
        videoCapture.release();
    }
}
