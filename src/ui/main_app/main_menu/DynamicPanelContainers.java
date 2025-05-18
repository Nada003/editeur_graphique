package ui.main_app.main_menu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.shapes.CircleModel;
import ui.custom_graphics.uml_components.shapes.CircleRender;
import ui.custom_graphics.uml_components.shapes.RectangleModel;
import ui.custom_graphics.uml_components.shapes.RectangleRender;
import ui.custom_graphics.uml_components.shapes.TriangleModel;
import ui.custom_graphics.uml_components.shapes.TriangleRender;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseModel;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseRender;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

public class DynamicPanelContainers extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);
    private static final Color HOVER_COLOR = new Color(232, 240, 254);
    private static final Color BORDER_COLOR = new Color(218, 220, 224);
    private static final Color TEXT_COLOR = new Color(60, 64, 67);

    private final JButton ovalButton;
    private final JButton triangleButton;
    private final JButton circleButton;
    private final JButton rectangleButton;
   

    public DynamicPanelContainers(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel titleLabel = new JLabel("Container Elements");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 5, 15, 5));
        add(titleLabel);

        ovalButton = createShapeButton("Oval", "src/assets/oval.png", "Add an oval shape to your diagram");
        triangleButton = createShapeButton("Triangle", "src/assets/triangle.png", "Add a triangle shape to your diagram");
        circleButton = createShapeButton("Circle", "src/assets/circle.png", "Add a circle shape to your diagram");
        rectangleButton = createShapeButton("Rectangle", "src/assets/rectangle.png", "Add a rectangle shape to your diagram");
        

        add(ovalButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(circleButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(rectangleButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(triangleButton);
        
        add(Box.createVerticalGlue());

        ovalButton.addActionListener(e -> {
            UseCaseModel model = new UseCaseModel("Oval Element");
            UseCaseRender usecase = new UseCaseRender(model);
            mainFlow.addElement(new UserAction("Add oval", usecase));
            components.addElement(usecase);
        });

        circleButton.addActionListener(e -> {
            CircleModel model = new CircleModel("Circle");
            CircleRender render = new CircleRender(model);
            mainFlow.addElement(new UserAction("Add circle", render));
            components.addElement(render);
        });

        triangleButton.addActionListener(e -> {
            TriangleModel model = new TriangleModel("Triangle");
            TriangleRender render = new TriangleRender(model);
            mainFlow.addElement(new UserAction("Add triangle", render));
            components.addElement(render);
        });

        rectangleButton.addActionListener(e -> {
            RectangleModel model = new RectangleModel("Rectangle");
            RectangleRender render = new RectangleRender(model);
            mainFlow.addElement(new UserAction("Add rectangle", render));
            components.addElement(render);
        });

       
    }

    private void showFeedback(String message) {
        JOptionPane.showMessageDialog(this, message, "Element Added", JOptionPane.INFORMATION_MESSAGE);
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

        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setIconTextGap(10);
        } catch (Exception e) {
            System.out.println("Failed to load icon: " + iconPath);
        }

        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(HOVER_COLOR); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(Color.WHITE); }
            @Override public void mousePressed(MouseEvent e) { button.setBackground(SECONDARY_COLOR); }
            @Override public void mouseReleased(MouseEvent e) { button.setBackground(HOVER_COLOR); }
        });

        return button;
    }
}
