package ogr.user12043.opencv.test.cameraTest.swing;

import ogr.user12043.opencv.test.cameraTest.swing.gui.CameraDisplay;

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
            // Set library path relatively
            /*java.io.File libPathLinux = new java.io.File("lib" + java.io.File.separator + "opencv-linux");
            java.io.File libPathWindows = new java.io.File("lib" + java.io.File.separator + "opencv-windows" + java.io.File.separator + "x64");
            String libraryPath = System.getProperty("java.library.path") + java.io.File.pathSeparator + libPathLinux.getAbsolutePath() + java.io.File.pathSeparator + libPathWindows.getAbsolutePath();
            System.setProperty("java.library.path", libraryPath);
            java.lang.reflect.Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);*/

            // Instead of this, run the program with jvm argument (requires absolute path in this case):
            // -Djava.library.path=/path/to/opencv/library/file
            // e.g:
            // -Djava.library.path=C:\Users\ME99735\Desktop\Folder\projects\java\NonToyota\opencv-test\lib\opencv-windows\x64


            System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
            SwingUtilities.invokeLater(() -> new CameraDisplay().setVisible(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
