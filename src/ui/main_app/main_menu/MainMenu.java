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

    ImageIcon icon = new ImageIcon("src/assets/Screenshot_20250326_180752.png");
    JButton hide = new JButton();

    public MainMenu(WatchedList<UMLComponent> components) {
        this.setMaximumSize(new Dimension(400,Short.MAX_VALUE));
        this.setPreferredSize(new Dimension(400, Short.MAX_VALUE));
        this.setMinimumSize(new Dimension(400, Short.MAX_VALUE));

        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        JPanel fixedMenu = new JPanel();
        fixedMenu.setBackground(Color.WHITE);
        fixedMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        fixedMenu.setMaximumSize(new Dimension(80,Short.MAX_VALUE));
        fixedMenu.setPreferredSize(new Dimension(80, Short.MAX_VALUE));
        fixedMenu.setMinimumSize(new Dimension(80, Short.MAX_VALUE));

        JPanel dynamicMenu = new JPanel();

        button.addActionListener(e->{
            ClassModel model = new ClassModel("", new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            components.addElement(render);
        });

        button2.addActionListener(e -> {
                    AssociationModel model = new AssociationModel("");
                    AssociationRender association = new AssociationRender(model);
                    components.addElement(association);
                });
        hide.addActionListener(
                e -> {
                    dynamicMenu.setVisible(!dynamicMenu.isVisible());
                    this.setMaximumSize(new Dimension(dynamicMenu.isVisible() ? 400 : 80,Integer.MAX_VALUE));
                    this.setPreferredSize(new Dimension(dynamicMenu.isVisible() ? 400 : 80,Integer.MAX_VALUE));
                    this.setMinimumSize(new Dimension(dynamicMenu.isVisible() ? 400 : 80,Integer.MAX_VALUE));
                }
        );


        // Remove button borders and background for a clean look
        hide.setFocusPainted(false);
        hide.setBorderPainted(false);
        hide.setContentAreaFilled(false);
        hide.setMaximumSize(new Dimension(80,80));
        hide.setIcon(scaledIcon);

        fixedMenu.add(hide);
        dynamicMenu.add(button);
        dynamicMenu.add(button2);
        this.add(fixedMenu);
        this.add(dynamicMenu);

    }
}
