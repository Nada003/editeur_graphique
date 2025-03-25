package ui.main_app.main_board;

import ui.custom_graphics.uml_components.UMLComponent;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MainBoard extends JPanel {
    public MainBoard(LinkedList<UMLComponent> components){
        this.setBackground(Color.lightGray);
        this.setMinimumSize(new Dimension(750,600));
        this.setLayout(null);

        for (var m : components)
         this.add(m);
    }
}
