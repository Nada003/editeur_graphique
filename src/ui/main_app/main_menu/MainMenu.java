package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {


    JButton ovalButton = new JButton();
    JButton triangleButton = new JButton("...");
    JButton circleButton = new JButton();
    JButton pillButton = new JButton("...");

    ImageIcon icon = new ImageIcon("src/assets/containers2.png");
    ImageIcon icon2 = new ImageIcon("src/assets/connect_components.png");
    ImageIcon icon3 = new ImageIcon("src/assets/shapes.png");


    JButton hide = new JButton();
    JButton connectButton = new JButton();
    JButton containersButton = new JButton();

    private final JPanel dynamicMenu;
    private final JPanel dynamicPanelConnect;
    JPanel dynamicPanelContainers = new JPanel();

    private static final int FIXED_PANEL_WIDTH = 50;   // Largeur du panel fixe et des boutons
    private static final int DYNAMIC_PANEL_WIDTH = 300; // Largeur des panels dynamiques
    private static final int BUTTON_HEIGHT = 50;       // Hauteur des boutons


    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;

    public MainMenu(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Panel fixe (50px de large)
        JPanel fixedMenu = new JPanel();
        fixedMenu.setBackground(Color.white);
        fixedMenu.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        setPanelSize(fixedMenu, FIXED_PANEL_WIDTH);
        dynamicPanelConnect = new DynamicPanelConnect(components,mainFlow);
        dynamicMenu = new DynamicMenu(components,mainFlow);
        dynamicPanelContainers = new DynamicPanelContainers(components,mainFlow);

        // Définition de la largeur des panels dynamiques (300px)
        setPanelSize(dynamicMenu, DYNAMIC_PANEL_WIDTH);
        setPanelSize(dynamicPanelConnect, DYNAMIC_PANEL_WIDTH);
        setPanelSize(dynamicPanelContainers, DYNAMIC_PANEL_WIDTH);

        // Définition de la même taille pour tous les boutons
        Dimension buttonSize = new Dimension(FIXED_PANEL_WIDTH, BUTTON_HEIGHT);
        setButtonSize(hide, buttonSize);
        setButtonSize(connectButton, buttonSize);
        setButtonSize(containersButton, buttonSize);

        // Panels dynamiques cachés par défaut
        dynamicMenu.setVisible(false);
        dynamicPanelConnect.setVisible(false);
        dynamicPanelContainers.setVisible(false);

        // Actions des boutons


        hide.addActionListener(e -> togglePanel(dynamicMenu));
        connectButton.addActionListener(e -> togglePanel(dynamicPanelConnect));
        containersButton.addActionListener(e -> togglePanel(dynamicPanelContainers));

        connectButton.setToolTipText("Connectors");
        containersButton.setToolTipText("containers");
        hide.setToolTipText("class");

        // Stylisation du bouton hide avec une icône
        hide.setFocusPainted(false);
        hide.setBorderPainted(false);
        hide.setContentAreaFilled(false);
        hide.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        connectButton.setFocusPainted(false);
        connectButton.setBorderPainted(false);
        connectButton.setContentAreaFilled(false);
        connectButton.setIcon(new ImageIcon(icon2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        containersButton.setFocusPainted(false);
        containersButton.setBorderPainted(false);
        containersButton.setContentAreaFilled(false);
        containersButton.setIcon(new ImageIcon(icon3.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        // Ajout des boutons dans le panel fixe avec des espaces
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(hide);
        fixedMenu.add(Box.createVerticalStrut(30));
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(connectButton);
        fixedMenu.add(Box.createVerticalStrut(30));
        fixedMenu.add(containersButton);

        // Ajout des panels à l'interface principale
        this.add(fixedMenu);
        this.add(dynamicMenu);
        this.add(dynamicPanelConnect);
        this.add(dynamicPanelContainers);

        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

    }

    private void togglePanel(JPanel panel) {
        boolean isCurrentlyVisible = panel.isVisible();
        dynamicMenu.setVisible(false);
        dynamicPanelConnect.setVisible(false);
        dynamicPanelContainers.setVisible(false);
        panel.setVisible(!isCurrentlyVisible);
        updateSize();
    }

    private void setPanelSize(JPanel panel, int width) {
        panel.setMaximumSize(new Dimension(width, Short.MAX_VALUE));
        panel.setPreferredSize(new Dimension(width, Short.MAX_VALUE));
        panel.setMinimumSize(new Dimension(width, Short.MAX_VALUE));
    }

    private void updateSize() {
        boolean isExpanded = dynamicMenu.isVisible() || dynamicPanelConnect.isVisible() || dynamicPanelContainers.isVisible();
        this.setMaximumSize(new Dimension(FIXED_PANEL_WIDTH + (isExpanded ? DYNAMIC_PANEL_WIDTH : 0), Integer.MAX_VALUE));
        this.setPreferredSize(new Dimension(FIXED_PANEL_WIDTH + (isExpanded ? DYNAMIC_PANEL_WIDTH : 0), Integer.MAX_VALUE));
        this.setMinimumSize(new Dimension(FIXED_PANEL_WIDTH + (isExpanded ? DYNAMIC_PANEL_WIDTH : 0), Integer.MAX_VALUE));
    }

    private void setButtonSize(JButton button, Dimension size) {
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
    }

}
