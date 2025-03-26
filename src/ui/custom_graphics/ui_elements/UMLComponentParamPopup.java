package ui.custom_graphics.ui_elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class UMLComponentParamPopup extends JDialog {
    private JDialog instance;
    public UMLComponentParamPopup(){
        super((Frame) null,"",true);
        this.setMinimumSize(new Dimension(600,200));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        instance = this;

        this.setUndecorated(true); // Remove default title bar
        this.setLayout(new BorderLayout());

        // Custom title bar panel
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.DARK_GRAY);
        titleBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Close Button
        JButton closeButton = new JButton("✖");
        closeButton.setFocusable(false);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.addActionListener(e -> this.dispose());
        titleBar.add(closeButton);

        enableDragging(this, titleBar);

        this.add(titleBar, BorderLayout.NORTH);
        this.add(getMainPopupView(), BorderLayout.CENTER);
        this.setVisible(true);
        // Enable dragging the window
    }

    private JPanel getMainPopupView() {
        JPanel main = new JPanel();
        main.setBackground(Color.WHITE);
        return main;
    }


    private static void enableDragging(JDialog dialog, JPanel titleBar) {
        final Point[] mouseDownCompCoords = {null};

        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                dialog.setLocation(currCoords.x - mouseDownCompCoords[0].x, currCoords.y - mouseDownCompCoords[0].y);
            }
        });
    }
}
