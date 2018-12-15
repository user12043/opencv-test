package ogr.user12043.opencv.test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 15.12.2018 - 20:07
 * part of opencv-test
 *
 * @author user12043
 */
// Use the computer screen as camera
public class OnScreenCamera {
    private static Robot robot;
    private static Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    private static Robot getRobot() throws AWTException {
        if (robot == null) {
            robot = new Robot();
        }
        return robot;
    }

    public static BufferedImage getFrame() {
        try {
            return getRobot().createScreenCapture(screenRectangle);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        return null;
    }
}
