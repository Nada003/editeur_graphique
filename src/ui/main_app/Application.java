package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.main_board.MainBoard;
import ui.main_app.main_menu.MainMenu;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Application extends JFrame {
    private static final WatchedList<UMLComponent> components = new WatchedList<>();

    public Application(){
        this.setTitle("UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setMinimumSize(new Dimension(900,600));
        this.setLocationRelativeTo(null);
        this.add(initMain());
        this.setVisible(true);
    }

    private static JPanel initMain() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.X_AXIS));
        main.add(new MainMenu(components));
        main.add(new MainBoard(components));
        return main;
    }

    private void getComponentFromFile(File file){
        var interpreter = new MyInterpreter();
        interpreter.interpretFile(file,components.getList());

    }
}
