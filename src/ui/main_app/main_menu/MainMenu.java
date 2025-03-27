package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationModel;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationRender;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationModel;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationRender;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationModel;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationRender;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseModel;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseRender;
import utils.custom_list.WatchedList;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseModel;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseRender;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    JButton buttonClass = new JButton();
    JButton associationButton = new JButton();
    JButton generalizationButton = new JButton();
    JButton aggregationButton = new JButton();
    JButton ovalButton = new JButton();
    JButton triangleButton = new JButton("...");
    JButton circleButton = new JButton();
    JButton pillButton = new JButton("...");

    ImageIcon icon = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/containers2.png");
    ImageIcon icon2 = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/connect_components.png");
    ImageIcon icon3 = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/shapes.png");
    ImageIcon icon4 = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/association.png");
    ImageIcon icon5 = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/generalization(heritage).png");
    ImageIcon icon6 = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/aggregation.png");
    ImageIcon icon7 = new ImageIcon("C:/Users/ng263/IdeaProjects/UML_desinger/src/assets/oval.png");

    JButton hide = new JButton();
    JButton connectButton = new JButton();
    JButton containersButton = new JButton();

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
        fixedMenu.setBackground(Color.white);
        fixedMenu.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
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

        generalizationButton.addActionListener(e -> {
            GeneralizationModel model = new GeneralizationModel("");
            GeneralizationRender generalization = new GeneralizationRender(model);
            components.addElement(generalization);
        });

        aggregationButton.addActionListener(e -> {
            AggregationModel model = new AggregationModel("");
            AggregationRender aggregation = new AggregationRender(model);
            components.addElement(aggregation);
        });

        circleButton.addActionListener(e -> {
            UseCaseModel model = new UseCaseModel("test");
            UseCaseRender usecase = new UseCaseRender(model);
            components.addElement(usecase);
        });

        associationButton.setToolTipText("Association");
        connectButton.setToolTipText("Connectors");
        containersButton.setToolTipText("containers");
        hide.setToolTipText("class");
        generalizationButton.setToolTipText("generalization");
        aggregationButton.setToolTipText("aggregation");
        circleButton.setToolTipText("circle");
        buttonClass.setToolTipText("Class");



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
        associationButton.setFocusPainted(false);
        associationButton.setBorderPainted(false);
        associationButton.setContentAreaFilled(false);
        associationButton.setIcon(new ImageIcon(icon4.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        generalizationButton.setFocusPainted(false);
        generalizationButton.setBorderPainted(false);
        generalizationButton.setContentAreaFilled(false);
        generalizationButton.setIcon(new ImageIcon(icon5.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        aggregationButton.setFocusPainted(false);
        aggregationButton.setBorderPainted(false);
        aggregationButton.setContentAreaFilled(false);
        aggregationButton.setIcon(new ImageIcon(icon6.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        circleButton.setFocusPainted(false);
        circleButton.setBorderPainted(false);
        circleButton.setContentAreaFilled(false);
        circleButton.setIcon(new ImageIcon(icon7.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        buttonClass.setFocusPainted(false);
        buttonClass.setBorderPainted(false);
        buttonClass.setContentAreaFilled(false);
        buttonClass.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));



        // Ajout des boutons dans le panel fixe avec des espaces
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(hide);
        fixedMenu.add(Box.createVerticalStrut(30));
        fixedMenu.setLayout(new BoxLayout(fixedMenu, BoxLayout.Y_AXIS));
        fixedMenu.add(connectButton);
        fixedMenu.add(Box.createVerticalStrut(30));
        fixedMenu.add(containersButton);


        // Ajout des boutons dans les panels dynamiques
        dynamicMenu.add(buttonClass);
        dynamicPanelConnect.add(associationButton);
        dynamicPanelConnect.add(generalizationButton);
        dynamicPanelConnect.add(aggregationButton);
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
