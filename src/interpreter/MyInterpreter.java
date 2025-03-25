package interpreter;

import ui.custom_graphics.uml_components.UMLComponent;

import java.io.File;
import java.util.LinkedList;

public class MyInterpreter {
    public void interpretFile(File file, LinkedList<UMLComponent> components) {
        if (!file.isFile() || !file.exists()) throw new IllegalArgumentException("No file");


    }
}
