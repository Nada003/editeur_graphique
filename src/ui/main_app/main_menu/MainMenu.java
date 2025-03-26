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

    JButton button = new JButton("Add class");
    JButton button2 = new JButton("Add Association");

    public MainMenu(WatchedList<UMLComponent> components) {
        this.setMaximumSize(new Dimension(10000, Integer.MAX_VALUE));

        this.add(button);

        this.add(button2);

        button.addActionListener(e -> {
            ClassModel model = new ClassModel("", new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            components.addElement(render);

        });

        button2.addActionListener(e -> {
            AssociationModel model = new AssociationModel("");
            AssociationRender association = new AssociationRender(model);
            components.addElement(association);

        });
    }
}
