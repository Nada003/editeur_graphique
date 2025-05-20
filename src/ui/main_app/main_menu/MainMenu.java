package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import utils.UML_diagrame;
import utils.custom_list.WatchedList;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class MainMenu extends JPanel {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);  // Blue
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);  // Light gray
    private static final Color HOVER_COLOR = new Color(232, 240, 254);  // Light blue
    private static final Color SELECTED_COLOR = new Color(209, 231, 252);  // Selected blue
    private static final Color BORDER_COLOR = new Color(218, 220, 224);  // Border gray
    private static final Color TEXT_COLOR = new Color(60, 64, 67);  // Dark gray
    private static final Color ICON_COLOR = new Color(95, 99, 104);  // Icon gray

    private static final LinkedList<MenuExpandingListener> listeners = new LinkedList<>();
    private final JButton classButton;
    private final JButton connectButton;
    private final JButton containersButton;

    private final JPanel dynamicMenu;
    private final JPanel dynamicPanelConnect;
    private final JPanel dynamicPanelContainers;

    public static final int FIXED_PANEL_WIDTH = 60;
    public static final int DYNAMIC_PANEL_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 60;
    public static int height = Integer.MAX_VALUE;
    public final UML_diagrame currentDiagramme;

    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;

    public MainMenu(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow, UML_diagrame currentDiagrame) {
        this.currentDiagramme = currentDiagrame;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Creating and configuring fixed menu panel
        JPanel fixedMenu = new JPanel();
        fixedMenu.setBackground(SECONDARY_COLOR);
        fixedMenu.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));
        setPanelSize(fixedMenu, FIXED_PANEL_WIDTH);

        dynamicMenu = new DynamicMenu(components, mainFlow, currentDiagramme);
        dynamicMenu.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));
        dynamicMenu.setBackground(Color.WHITE);

        dynamicPanelConnect = new DynamicPanelConnect(components, mainFlow, currentDiagramme);
        dynamicPanelConnect.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));
        dynamicPanelConnect.setBackground(Color.WHITE);

        dynamicPanelContainers = new DynamicPanelContainers(components, mainFlow, currentDiagramme);
        dynamicPanelContainers.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));
        dynamicPanelContainers.setBackground(Color.WHITE);

        // Set panel sizes
        setPanelSize(dynamicMenu, DYNAMIC_PANEL_WIDTH);
        setPanelSize(dynamicPanelConnect, DYNAMIC_PANEL_WIDTH);
        setPanelSize(dynamicPanelContainers, DYNAMIC_PANEL_WIDTH);

        // Initialize buttons with modern icons and styling
        classButton = createModernButton("Class Diagrams", "src/assets/containers2.png", "Create and manage class diagrams");
        connectButton = createModernButton("Connectors", "src/assets/connect_components.png", "Add relationships between elements");
        containersButton = createModernButton("Containers", "src/assets/shapes.png", "Add container elements to your diagram");

        // Adding buttons to the fixed menu with better spacing
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(Box.createVerticalStrut(15));
        fixedMenu.add(classButton);
        fixedMenu.add(Box.createVerticalStrut(15));
        fixedMenu.add(connectButton);
        fixedMenu.add(Box.createVerticalStrut(15));
        fixedMenu.add(containersButton);
        fixedMenu.add(Box.createVerticalGlue());

        // Add action listeners to buttons with visual feedback
        classButton.addActionListener(e -> togglePanel(dynamicMenu, classButton));
        connectButton.addActionListener(e -> togglePanel(dynamicPanelConnect, connectButton));
        containersButton.addActionListener(e -> togglePanel(dynamicPanelContainers, containersButton));

        // Adding panels to the main interface
        this.add(fixedMenu);
        this.add(dynamicMenu);
        this.add(dynamicPanelConnect);
        this.add(dynamicPanelContainers);

        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

        // Set all dynamic panels to be initially invisible
        setAllDynamicPanelsVisibility(false);
        resetButtonStyles();
    }

    private JButton createModernButton(String tooltip, String iconPath, String description) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        // Create a modern icon with consistent styling
        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            // Fallback if image not found
            button.setText(tooltip.substring(0, 1));
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setForeground(PRIMARY_COLOR);
        }

        // Add tooltip with modern styling
        button.setToolTipText("<html><b>" + tooltip + "</b><br>" + description + "</html>");

        // Set button size
        setButtonSize(button, new Dimension(FIXED_PANEL_WIDTH, BUTTON_HEIGHT));

        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!Boolean.TRUE.equals(button.getClientProperty("selected"))) {
                    button.setBackground(HOVER_COLOR);
                    button.setContentAreaFilled(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!Boolean.TRUE.equals(button.getClientProperty("selected"))) {
                    button.setContentAreaFilled(false);
                }
            }
        });

        return button;
    }

    private void togglePanel(JPanel panel, JButton button) {
        resetButtonStyles();

        if (panel.isVisible()) {
            panel.setVisible(false);
            button.setContentAreaFilled(false);
            button.putClientProperty("selected", false);
        } else {
            setAllDynamicPanelsVisibility(false);
            panel.setVisible(true);

            // Highlight the selected button
            button.setBackground(SELECTED_COLOR);
            button.setContentAreaFilled(true);
            button.putClientProperty("selected", true);
        }

        notifyListeners();
        updateSize();
        revalidate();
    }

    private void resetButtonStyles() {
        classButton.setContentAreaFilled(false);
        classButton.putClientProperty("selected", false);

        connectButton.setContentAreaFilled(false);
        connectButton.putClientProperty("selected", false);

        containersButton.setContentAreaFilled(false);
        containersButton.putClientProperty("selected", false);
    }

    private void setAllDynamicPanelsVisibility(boolean isVisible) {
        dynamicMenu.setVisible(isVisible);
        dynamicPanelConnect.setVisible(isVisible);
        dynamicPanelContainers.setVisible(isVisible);
    }

    private void setPanelSize(JPanel panel, int width) {
        panel.setMaximumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
    }

    private void updateSize() {
        this.setPreferredSize(new Dimension(FIXED_PANEL_WIDTH + (isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), height));
        revalidate();
    }

    private void setButtonSize(JButton button, Dimension size) {
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
    }

    public boolean isExpanded() {
        return dynamicMenu.isVisible() || dynamicPanelConnect.isVisible() || dynamicPanelContainers.isVisible();
    }

    public void addListener(MenuExpandingListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public void notifyListeners() {
        for (var v : listeners)
            v.doAction();
    }

    public void setHeight(int height) {
        MainMenu.height = height;
        revalidate();
    }
}
