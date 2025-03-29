package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class DynamicMenu extends JPanel {
    ImageIcon icon = new ImageIcon("src/assets/containers2.png");

    JButton buttonClass = new JButton();
    public DynamicMenu(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow){
        this.setBackground(Color.red);
        buttonClass.setFocusPainted(false);
        buttonClass.setBorderPainted(false);
        buttonClass.setContentAreaFilled(false);
        buttonClass.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40,Image.SCALE_SMOOTH)));
        buttonClass.setToolTipText("Class");
        buttonClass.addActionListener(e -> {
            ClassModel model = new ClassModel("", new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            mainFlow.addElement(new UserAction("Ajouter une class",render)); //save to user action
            components.addElement(render);
        });
        buttonClass.setFocusPainted(false);
        buttonClass.setBorderPainted(false);
        buttonClass.setContentAreaFilled(false);
        buttonClass.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40,Image.SCALE_SMOOTH)));
        buttonClass.setToolTipText("Class");
       this.add(buttonClass);
    }
}
