package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationModel;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationRender;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationModel;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationRender;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationModel;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationRender;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class DynamicPanelConnect extends JPanel {
    ImageIcon icon4 = new ImageIcon("src/assets/association.png");
    ImageIcon icon5 = new ImageIcon("src/assets/generalization(heritage).png");
    ImageIcon icon6 = new ImageIcon("src/assets/aggregation.png");

    JButton associationButton = new JButton();
    JButton generalizationButton = new JButton();
    JButton aggregationButton = new JButton();

    public DynamicPanelConnect(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow){
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

        associationButton.setToolTipText("Association");
        generalizationButton.setToolTipText("generalization");
        aggregationButton.setToolTipText("aggregation");

        associationButton.addActionListener(e -> {
            AssociationModel model = new AssociationModel("");
            AssociationRender association = new AssociationRender(model);
            mainFlow.addElement(new UserAction("Ajouter Association",association)); //save to user action
            components.addElement(association);
        });

        generalizationButton.addActionListener(e -> {
            GeneralizationModel model = new GeneralizationModel("");
            GeneralizationRender generalization = new GeneralizationRender(model);
            mainFlow.addElement(new UserAction("Ajouter genralization",generalization)); //save to user action
            components.addElement(generalization);
        });

        aggregationButton.addActionListener(e -> {
            AggregationModel model = new AggregationModel("");
            AggregationRender aggregation = new AggregationRender(model);
            mainFlow.addElement(new UserAction("Ajouter aggregation",aggregation)); //save to user action
            components.addElement(aggregation);
        });

        this.add(associationButton);
        this.add(generalizationButton);
        this.add(aggregationButton);
    }
}
