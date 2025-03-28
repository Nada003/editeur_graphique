package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseModel;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseRender;
import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class DynamicPanelContainers extends JPanel {
    JButton ovalButton = new JButton();
    JButton triangleButton = new JButton("...");
    JButton circleButton = new JButton();
    JButton pillButton = new JButton("...");

    ImageIcon icon7 = new ImageIcon("src/assets/oval.png");

    public DynamicPanelContainers(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow){
        circleButton.setFocusPainted(false);
        circleButton.setBorderPainted(false);
        circleButton.setContentAreaFilled(false);
        circleButton.setIcon(new ImageIcon(icon7.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        circleButton.setToolTipText("circle");


        circleButton.addActionListener(e -> {
            UseCaseModel model = new UseCaseModel("test");
            UseCaseRender usecase = new UseCaseRender(model);
            mainFlow.addElement(new UserAction("Ajouter usecase",usecase)); //save to user action
            components.addElement(usecase);
        });
       this.add(triangleButton);
       this.add(circleButton);
       this.add(pillButton);
    }
}
