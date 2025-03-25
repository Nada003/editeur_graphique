package ui.custom_graphics.uml_components.class_diagram.classes;

public class ClassModel {
    public String name;
    public String[] att = new String[]{};
    public String[] functions= new String[]{};

    public ClassModel(String name, String[] att, String[] functions) {
        this.name = name;
        this.att = att;
        this.functions = functions;
    }
}
