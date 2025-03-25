package ui.custom_graphics.uml_components.class_diagram.interfaces;

public class InterfaceModel {
    public String name;
    public String[] att = new String[]{};
    public String[] functions= new String[]{};

    public InterfaceModel(String name, String[] att, String[] functions) {
        this.name = name;
        this.att = att;
        this.functions = functions;
    }
}
