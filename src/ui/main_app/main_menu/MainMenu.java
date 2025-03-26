package ui.main_app.main_menu;

import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.main_app.Application;
import ui.main_app.main_board.MainBoard;

import javax.swing.*;

public class MainMenu extends JPanel {

    JButton button = new JButton("Main Menu");

    public MainMenu() {
        this.add(button);
        button.addActionListener(e->{

            ClassModel model = new ClassModel("", new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            Application.getModelsFromMainMenu(render);
        });



    }
}
