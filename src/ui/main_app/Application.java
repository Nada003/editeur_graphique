package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.main_app.main_board.MainBoard;
import ui.main_app.main_menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;

public class Application extends JFrame {
    private final LinkedList<UMLComponent> components = new LinkedList<>();

    public Application(){
        this.setTitle("UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(400,400));
        this.add(initMain());
    }

    private JPanel initMain() {
        var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.add(new MainMenu());

        /* todo: remove the test */
            var v = new ClassRender(
                    new ClassModel(
                            "Test",
                            new String[]{"att : int"},
                            new String[]{}
                    )
            );
            v.repaint();
            components.add(v);
        /* end test */


        mainPanel.add(new MainBoard(components));
        return mainPanel;
    }

    private void getComponentFromFile(File file){
        var interpreter = new MyInterpreter();
        interpreter.interpretFile(file,components);
    }
}
