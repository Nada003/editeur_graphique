package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseModel;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseRender;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DynamicPanelContainers extends JPanel {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);  // Blue
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);  // Light gray
    private static final Color HOVER_COLOR = new Color(232, 240, 254);  // Light blue
    private static final Color BORDER_COLOR = new Color(218, 220, 224);  // Border gray
    private static final Color TEXT_COLOR = new Color(60, 64, 67);  // Dark gray

    private final JButton ovalButton;
    private final JButton triangleButton;
    private final JButton circleButton;
    private final JButton pillButton;
    private final JButton rectangleButton;
    private final JButton packageButton;

    public DynamicPanelContainers(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 10, 15, 10));

        // Create title label
        JLabel titleLabel = new JLabel("Container Elements");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 5, 15, 5));
        add(titleLabel);

        // Create shape buttons
        ovalButton = createShapeButton("Oval", "src/assets/oval.png",
            "Add an oval shape to your diagram");
        triangleButton = createShapeButton("Triangle", "src/assets/triangle.png",
            "Add a triangle shape to your diagram");
        circleButton = createShapeButton("Circle", "src/assets/circle.png",
            "Add a circle shape to your diagram");
        pillButton = createShapeButton("Pill", "src/assets/pill.png",
            "Add a pill shape to your diagram");
        rectangleButton = createShapeButton("Rectangle", "src/assets/rectangle.png",
            "Add a rectangle shape to your diagram");
        packageButton = createShapeButton("Package", "src/assets/package.png",
            "Add a package container to your diagram");

        // Add buttons to panel with spacing
        add(ovalButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(circleButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(rectangleButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(triangleButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(pillButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(packageButton);
        add(Box.createVerticalGlue());

        // Add action listeners
        circleButton.addActionListener(e -> {
            UseCaseModel model = new UseCaseModel("Use Case");
            UseCaseRender usecase = new UseCaseRender(model);
            mainFlow.addElement(new UserAction("Add use case", usecase));
            components.addElement(usecase);

          
            
        });

        ovalButton.addActionListener(e -> {
            UseCaseModel model = new UseCaseModel("Oval Element");
            UseCaseRender usecase = new UseCaseRender(model);
            mainFlow.addElement(new UserAction("Add oval", usecase));
            components.addElement(usecase);

           
        });

        triangleButton.addActionListener(e -> {
            // Placeholder for triangle - would need actual implementation
            showFeedback("Triangle shape not implemented yet");
        });

        pillButton.addActionListener(e -> {
            // Placeholder for pill - would need actual implementation
            showFeedback("Pill shape not implemented yet");
        });

        rectangleButton.addActionListener(e -> {
            // Placeholder for rectangle - would need actual implementation
            showFeedback("Rectangle not implemented yet");
        });

        packageButton.addActionListener(e -> {
            // Placeholder for package - would need actual implementation
            showFeedback("Package container not implemented yet");
        });
    }

    private void showFeedback(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Element Added",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JButton createShapeButton(String text, String iconPath, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_COLOR);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(8, 12, 8, 12)
        ));
        button.setToolTipText(tooltip);
        button.setBackground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        // Try to load icon
        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setIconTextGap(10);
        } catch (Exception e) {
            // If icon fails to load, continue without it
            System.out.println("Failed to load icon: " + iconPath);
        }

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
        });

        return button;
    }
}
