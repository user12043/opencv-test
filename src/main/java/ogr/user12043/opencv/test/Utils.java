package ogr.user12043.opencv.test;

import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Created on 09.09.2018 - 17:12
 * part of opencv-test
 *
 * @author user12043
 */
public class Utils {
    public static BufferedImage getScreenCapture() throws AWTException {
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return new Robot().createScreenCapture(rectangle);
    }

    public static BufferedImage matToBufferedImage(Mat frame) {
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

    public static void saveImage(BufferedImage image) throws IOException {
        final File outFile = new File(Constants.PHOTO_OUTPUT_PATH);
        outFile.mkdirs();
        ImageIO.write(image, "png", outFile);
    }
}
