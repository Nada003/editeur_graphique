package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationModel;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationRender;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    JButton buttonClass = new JButton("Add class");
    JButton connectButton = new JButton("C");
    JButton containersButton = new JButton("Co");
    JButton associationButton = new JButton("Association");
    JButton triangleButton = new JButton("Triangle");
    JButton circleButton = new JButton("Circle");
    JButton pillButton = new JButton("Pill");

    ImageIcon icon = new ImageIcon("C:/Users/hiche/Downloads/editeur_graphique-main/editeur_graphique-main/src/assets/Screenshot_20250326_180752.png");
    JButton hide = new JButton();

    JPanel dynamicMenu = new JPanel();
    JPanel dynamicPanelConnect = new JPanel();
    JPanel dynamicPanelContainers = new JPanel();

    private static final int FIXED_PANEL_WIDTH = 50;   // Largeur du panel fixe et des boutons
    private static final int DYNAMIC_PANEL_WIDTH = 300; // Largeur des panels dynamiques
    private static final int BUTTON_HEIGHT = 50;       // Hauteur des boutons

    public MainMenu(WatchedList<UMLComponent> components) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Panel fixe (50px de large)
        JPanel fixedMenu = new JPanel();
        fixedMenu.setBackground(Color.WHITE);
        fixedMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setPanelSize(fixedMenu, FIXED_PANEL_WIDTH);

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
        buttonClass.addActionListener(e -> {
            ClassModel model = new ClassModel("", new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            components.addElement(render);
        });

        hide.addActionListener(e -> togglePanel(dynamicMenu));
        connectButton.addActionListener(e -> togglePanel(dynamicPanelConnect));
        containersButton.addActionListener(e -> togglePanel(dynamicPanelContainers));

        associationButton.addActionListener(e -> {
            AssociationModel model = new AssociationModel("");
            AssociationRender association = new AssociationRender(model);
            components.addElement(association);
        });

        triangleButton.addActionListener(e -> System.out.println("Triangle Container ajouté"));
        circleButton.addActionListener(e -> System.out.println("Circle Container ajouté"));
        pillButton.addActionListener(e -> System.out.println("Pill Container ajouté"));

        // Stylisation du bouton hide avec une icône
        hide.setFocusPainted(false);
        hide.setBorderPainted(false);
        hide.setContentAreaFilled(false);
        hide.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        // Ajout des boutons dans le panel fixe avec des espaces
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(hide);
        fixedMenu.add(Box.createVerticalStrut(10)); // Espace de 10px
        fixedMenu.add(connectButton);
        fixedMenu.add(Box.createVerticalStrut(10)); // Espace de 10px
        fixedMenu.add(containersButton);

        // Ajout des boutons dans les panels dynamiques
        dynamicMenu.add(buttonClass);
        dynamicPanelConnect.add(associationButton);
        dynamicPanelContainers.add(triangleButton);
        dynamicPanelContainers.add(circleButton);
        dynamicPanelContainers.add(pillButton);

        // Ajout des panels à l'interface principale
        this.add(fixedMenu);
        this.add(dynamicMenu);
        this.add(dynamicPanelConnect);
        this.add(dynamicPanelContainers);
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
