package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.Relation;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationModel;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationRender;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationModel;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationRender;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationModel;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationRender;
import ui.main_app.history.UserAction;
import ui.main_app.main_board.MainBoard;
import utils.custom_list.WatchedList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DynamicPanelConnect extends JPanel {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);  // Blue
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);  // Light gray
    private static final Color HOVER_COLOR = new Color(232, 240, 254);  // Light blue
    private static final Color BORDER_COLOR = new Color(218, 220, 224);  // Border gray
    private static final Color TEXT_COLOR = new Color(60, 64, 67);  // Dark gray
    private static final Color ACTIVE_COLOR = new Color(209, 231, 252);  // Selected blue

    private final JButton associationButton;
    private final JButton generalizationButton;
    private final JButton aggregationButton;
    private final JButton compositionButton;
    private final JButton dependencyButton;
    private final JButton realizationButton;

    public DynamicPanelConnect(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 10, 15, 10));

        // Create title label
        JLabel titleLabel = new JLabel("Relationship Types");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 5, 15, 5));
        add(titleLabel);

        // Create relationship buttons with custom icons
        associationButton = createRelationshipButton("Association",
            "src/assets/association.png",
            "Connect classes with a simple association");

        generalizationButton = createRelationshipButton("Generalization",
            "src/assets/generalization(heritage).png",
            "Create inheritance relationships");

        aggregationButton = createRelationshipButton("Aggregation",
            "src/assets/aggregation.png",
            "Create a 'has-a' relationship");

        compositionButton = createRelationshipButton("Composition",
            "src/assets/composition.png",
            "Create a strong 'contains' relationship");

        dependencyButton = createRelationshipButton("Dependency",
            "src/assets/dependency.png",
            "Create a 'uses' relationship");

        realizationButton = createRelationshipButton("Realization",
            "src/assets/realization.png",
            "Connect class to interface implementation");

        // Add buttons to panel with spacing
        add(associationButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(generalizationButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(aggregationButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(compositionButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(dependencyButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(realizationButton);
        add(Box.createVerticalGlue());

        // Add action listeners
        associationButton.addActionListener(e -> {
            resetButtonStates();
            associationButton.setBackground(ACTIVE_COLOR);

            AssociationModel model = new AssociationModel("");
            AssociationRender association = new AssociationRender(model);
            mainFlow.addElement(new UserAction("Add Association", association));
            components.addElement(association);
        });

        generalizationButton.addActionListener(e -> {
            resetButtonStates();
            generalizationButton.setBackground(ACTIVE_COLOR);

            GeneralizationModel model = new GeneralizationModel("");
            GeneralizationRender generalization = new GeneralizationRender(model);
            MainBoard.setRelation(new Relation(generalization, components));

           
        });

        aggregationButton.addActionListener(e -> {
            resetButtonStates();
            aggregationButton.setBackground(ACTIVE_COLOR);

            AggregationModel model = new AggregationModel("");
            AggregationRender aggregation = new AggregationRender(model);
            mainFlow.addElement(new UserAction("Add aggregation", aggregation));
            components.addElement(aggregation);
        });

        compositionButton.addActionListener(e -> {
            resetButtonStates();
            compositionButton.setBackground(ACTIVE_COLOR);

            
            JOptionPane.showMessageDialog(
                this,
                "Placeholder for composition - would need actual implementation",
                "Create Composition",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        dependencyButton.addActionListener(e -> {
            resetButtonStates();
            dependencyButton.setBackground(ACTIVE_COLOR);

            
            JOptionPane.showMessageDialog(
                this,
                "Placeholder for dependency - would need actual implementation",
                "Create Dependency",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        realizationButton.addActionListener(e -> {
            resetButtonStates();
            realizationButton.setBackground(ACTIVE_COLOR);

            
            JOptionPane.showMessageDialog(
                this,
                " Placeholder for realization - would need actual implementation",
                "Create Realization",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    private void resetButtonStates() {
        associationButton.setBackground(Color.WHITE);
        generalizationButton.setBackground(Color.WHITE);
        aggregationButton.setBackground(Color.WHITE);
        compositionButton.setBackground(Color.WHITE);
        dependencyButton.setBackground(Color.WHITE);
        realizationButton.setBackground(Color.WHITE);
    }

    private JButton createRelationshipButton(String text, String iconPath, String tooltip) {
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
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(Color.WHITE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(SECONDARY_COLOR);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(HOVER_COLOR);
                }
            }
        });

        return button;
    }
}
