package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    JButton button = new JButton("Add class");

    public MainMenu(WatchedList<UMLComponent> components) {
        this.setMaximumSize(new Dimension(10000,Integer.MAX_VALUE));
        this.add(button);
        button.addActionListener(e->{

            ClassModel model = new ClassModel("", new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            components.addElement(render);
        });



    }
}
