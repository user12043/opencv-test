package ogr.user12043.opencv.test.cameraTest.swing.gui;

import ogr.user12043.opencv.test.Constants;
import ogr.user12043.opencv.test.Utils;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created on 06.09.2018 - 10:19
 * part of opencv-test
 *
 * @author user12043
 */
public class CameraDisplay extends javax.swing.JFrame {

    private VideoCapture videoCapture;
    private ScheduledExecutorService service;
    private BufferedImage frame;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ogr.user12043.opencv.test.cameraTest.swing.gui.CameraPanel cameraPanel;
    private javax.swing.JButton jButton_takePhoto;
    private javax.swing.JToggleButton jToggleButton_control;
    // End of variables declaration//GEN-END:variables


    /**
     * Creates new form CameraDisplay
     */
    public CameraDisplay() {
        initComponents();
        videoCapture = new VideoCapture();
    }

    private void initService() {
        service = Executors.newSingleThreadScheduledExecutor();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                videoCapture.release();
                super.windowClosed(e);
            }
        });
        service.scheduleAtFixedRate(this::update, 0, 33, TimeUnit.MILLISECONDS);
    }

    private void endService() {
        service.shutdownNow();
        try {
            jButton_takePhoto.setEnabled(false);
            service.awaitTermination(1, TimeUnit.MINUTES);
            videoCapture.release();
        } catch (InterruptedException e) {
            System.err.println("Cannot stop the camera");
            e.printStackTrace();
        }
    }

    private void update() {
        Mat mat = new Mat();
        videoCapture.read(mat);
        this.frame = Utils.matToBufferedImage(mat);
        cameraPanel.setImage(this.frame);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jToggleButton_control = new javax.swing.JToggleButton();
        jButton_takePhoto = new javax.swing.JButton();
        cameraPanel = new ogr.user12043.opencv.test.cameraTest.swing.gui.CameraPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Camera");
        setLocationByPlatform(true);
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(640, 480));
        setSize(new java.awt.Dimension(640, 480));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jToggleButton_control.setText("Start");
        jToggleButton_control.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton_controlItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(jToggleButton_control, gridBagConstraints);

        jButton_takePhoto.setText("Take Photo");
        jButton_takePhoto.setEnabled(false);
        jButton_takePhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_takePhotoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(jButton_takePhoto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 10.0;
        getContentPane().add(cameraPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton_controlItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton_controlItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            jToggleButton_control.setText("Stop");
            videoCapture.open(Constants.CAMERA_INDEX);
            initService();
            jButton_takePhoto.setEnabled(true);
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            jToggleButton_control.setText("Start");
            endService();
        }
    }//GEN-LAST:event_jToggleButton_controlItemStateChanged

    private void jButton_takePhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_takePhotoActionPerformed
        try {
            Utils.saveImage(this.frame);
        } catch (IOException e) {
            System.err.println("Can not take photo");
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton_takePhotoActionPerformed
}
