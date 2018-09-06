package ogr.user12043.opencv.test;

import ogr.user12043.opencv.test.gui.CameraDisplay;

import javax.swing.*;

/**
 * Created on 05.09.2018 - 20:42
 * part of opencv-test
 *
 * @author user12043
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
            SwingUtilities.invokeLater(() -> new CameraDisplay().setVisible(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
