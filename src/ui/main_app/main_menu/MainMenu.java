package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MainMenu extends JPanel {
    private static final LinkedList<MenuExpandingListener> listeners = new LinkedList<>();
    private final JButton hideButton;
    private final JButton connectButton;
    private final JButton containersButton;

    private final JPanel dynamicMenu;
    private final JPanel dynamicPanelConnect;
    private final JPanel dynamicPanelContainers;

    public static final int FIXED_PANEL_WIDTH = 50;
    public static final int DYNAMIC_PANEL_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 50;

    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;

    public MainMenu(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Creating and configuring fixed menu panel
        JPanel fixedMenu = new JPanel();
        fixedMenu.setBackground(Color.WHITE);
        fixedMenu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        setPanelSize(fixedMenu, FIXED_PANEL_WIDTH);

        dynamicMenu = new DynamicMenu(components, mainFlow);
        dynamicPanelConnect = new DynamicPanelConnect(components, mainFlow);
        dynamicPanelContainers = new DynamicPanelContainers(components, mainFlow);

        // Set panel sizes
        setPanelSize(dynamicMenu, DYNAMIC_PANEL_WIDTH);
        setPanelSize(dynamicPanelConnect, DYNAMIC_PANEL_WIDTH);
        setPanelSize(dynamicPanelContainers, DYNAMIC_PANEL_WIDTH);

        // Initialize buttons with their sizes and tooltips
        hideButton = createButton("src/assets/containers2.png", "class");
        connectButton = createButton("src/assets/connect_components.png", "Connectors");
        containersButton = createButton("src/assets/shapes.png", "Containers");

        // Adding buttons to the fixed menu
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(hideButton);
        fixedMenu.add(Box.createVerticalStrut(30));
        fixedMenu.add(connectButton);
        fixedMenu.add(Box.createVerticalStrut(30));
        fixedMenu.add(containersButton);

        // Add action listeners to buttons
        hideButton.addActionListener(e -> togglePanel(dynamicMenu));
        connectButton.addActionListener(e -> togglePanel(dynamicPanelConnect));
        containersButton.addActionListener(e -> togglePanel(dynamicPanelContainers));

        // Adding panels to the main interface
        this.add(fixedMenu);
        this.add(dynamicMenu);
        this.add(dynamicPanelConnect);
        this.add(dynamicPanelContainers);

        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

        // Set all dynamic panels to be initially invisible
        setAllDynamicPanelsVisibility(false);
    }

    private JButton createButton(String iconPath, String tooltip) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setIcon(new ImageIcon(new ImageIcon(iconPath)
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        button.setToolTipText(tooltip);
        setButtonSize(button, new Dimension(FIXED_PANEL_WIDTH, BUTTON_HEIGHT));
        return button;
    }

    private void togglePanel(JPanel panel) {
        if (panel.isVisible()) {
            panel.setVisible(false);
        } else {
            setAllDynamicPanelsVisibility(false);
            panel.setVisible(true);
        }
        notifyListeners();
        updateSize();
        revalidate(); // Important to revalidate after changing visibility
        repaint();    // Important to repaint the component
    }

    private void setAllDynamicPanelsVisibility(boolean isVisible) {
        dynamicMenu.setVisible(isVisible);
        dynamicPanelConnect.setVisible(isVisible);
        dynamicPanelContainers.setVisible(isVisible);
    }

    private void setPanelSize(JPanel panel, int width) {
        panel.setMaximumSize(new Dimension(width, Short.MAX_VALUE));
        panel.setPreferredSize(new Dimension(width, Short.MAX_VALUE));
        panel.setMinimumSize(new Dimension(width, Short.MAX_VALUE));
    }

    private void updateSize() {
        this.setPreferredSize(new Dimension(FIXED_PANEL_WIDTH + (isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), Integer.MAX_VALUE));
        revalidate(); // Notify layout manager to re-layout components
    }

    private void setButtonSize(JButton button, Dimension size) {
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
    }

    public boolean isExpanded() {
        return dynamicMenu.isVisible() || dynamicPanelConnect.isVisible() || dynamicPanelContainers.isVisible() ;
    }

    public void addListener(MenuExpandingListener listener){
        if(!listeners.contains(listener))
            listeners.add(listener);
    }

    public void notifyListeners(){
        for (var v : listeners)
            v.doAction();
    }
}
