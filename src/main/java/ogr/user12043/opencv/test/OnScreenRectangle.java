package ogr.user12043.opencv.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created on 18.09.2018 - 22:53
 * part of opencv-test
 *
 * @author user12043
 */
public class OnScreenRectangle extends JFrame {
    private Point location;
    private Dimension size;
    private Color lineColor;
    private long nanoTime;

    public OnScreenRectangle(Point location, Dimension size, Color lineColor) {
        this.location = location;
        this.lineColor = lineColor;
        this.size = size;
        initComponents();
        registerEvents();
    }

    private void initComponents() {
        setLayout(new FlowLayout());

        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createLineBorder(lineColor, 3, true));

        setContentPane(content);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setResizable(false);
        setType(Type.POPUP);
        setBackground(new Color(0, 0, 0, 0));
        pack();
        setLocation(location);
        setSize(size);
    }

    private void registerEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                nanoTime = System.nanoTime();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouse released");
                final long passedTime = System.nanoTime() - nanoTime;
                if (passedTime >= 300000000L) { // if pressed more than 3 seconds
                    dispose();
                }
            }
        });
    }
}
