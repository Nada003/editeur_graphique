package ui.main_app.main_menu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import utils.UML_diagrame;
import utils.custom_list.WatchedList;
import utils.models.JButtonHelper;

public class MainMenu extends JPanel {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);  // Blue
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);  // Light gray
    private static final Color HOVER_COLOR = new Color(232, 240, 254);  // Light blue
    private static final Color SELECTED_COLOR = new Color(209, 231, 252);  // Selected blue
    private static final Color BORDER_COLOR = new Color(218, 220, 224);  // Border gray

    private static final LinkedList<MenuExpandingListener> listeners = new LinkedList<>();
    private final JButton classButton;
    private final JButton connectButton;
    private final JButton containersButton;
    private final JButton actorButton;

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

    public MainMenu(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow, UML_diagrame currentDiagramme) {
        this.currentDiagramme = currentDiagramme;
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
        actorButton = createModernButton(
                currentDiagramme == UML_diagrame.diagrameCasUtilisation ? "acteurs" : "",
                "src/assets/actor.png",
                "Ajouter des acteurs"
        );
        classButton = createModernButton(
                currentDiagramme == UML_diagrame.diagrameClass ? "diagrammes" : "",
                "src/assets/containers2.png",
                "Créer et modifier des diagrammes"
        );
        connectButton = createModernButton(
                "Relations",
                "src/assets/connect_components.png",
                "Ajouter des relations entre les éléments"
        );
        containersButton = createModernButton(
                "Conteneurs",
                "src/assets/shapes.png",
                "Ajouter des conteneurs pour votre diagramme"
        );

        JButtonHelper[] buttons = {
                new JButtonHelper(actorButton, UML_diagrame.diagrameCasUtilisation),
                new JButtonHelper(actorButton, UML_diagrame.diagrameSequence),
                new JButtonHelper(connectButton, UML_diagrame.diagrameCasUtilisation),
                new JButtonHelper(connectButton, UML_diagrame.diagrameSequence),
                new JButtonHelper(connectButton, UML_diagrame.diagrameClass),
                new JButtonHelper(classButton, UML_diagrame.diagrameClass),
                new JButtonHelper(containersButton, UML_diagrame.diagrameClass)
        };

        for (JButtonHelper buttonHelper : buttons) {
            if (buttonHelper.umlDiagrame == currentDiagramme) {
                fixedMenu.add(buttonHelper.button);
                fixedMenu.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        actorButton.addActionListener(e -> togglePanel(dynamicMenu, actorButton));
        classButton.addActionListener(e -> togglePanel(dynamicMenu, classButton));
        connectButton.addActionListener(e -> togglePanel(dynamicPanelConnect, connectButton));
        containersButton.addActionListener(e -> togglePanel(dynamicPanelContainers, containersButton));
        
        this.add(fixedMenu);
        this.add(dynamicMenu);
        this.add(dynamicPanelConnect);
        this.add(dynamicPanelContainers);

        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

        setAllDynamicPanelsVisibility(false);
        resetButtonStyles();
    }

    private JButton createModernButton(String tooltip, String iconPath, String description) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            if (!tooltip.isEmpty()) {
                button.setText(tooltip.substring(0, 1).toUpperCase());
                button.setFont(new Font("Segoe UI", Font.BOLD, 16));
                button.setForeground(PRIMARY_COLOR);
            }
        }

        button.setToolTipText("<html><b>" + tooltip + "</b><br>" + description + "</html>");
        setButtonSize(button, new Dimension(FIXED_PANEL_WIDTH, BUTTON_HEIGHT));

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

        actorButton.setContentAreaFilled(false);
        actorButton.putClientProperty("selected", false);
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

    private void setButtonSize(JButton button, Dimension size) {
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
    }

    private void updateSize() {
        this.setPreferredSize(new Dimension(FIXED_PANEL_WIDTH + (isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), height));
        revalidate();
    }

    public boolean isExpanded() {
        return dynamicMenu.isVisible() || dynamicPanelConnect.isVisible() || dynamicPanelContainers.isVisible();
    }

    public void addListener(MenuExpandingListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void notifyListeners() {
        for (MenuExpandingListener listener : listeners) {
            listener.doAction();
        }
    }

    public void setHeight(int height) {
        MainMenu.height = height;
        revalidate();
    }
}
