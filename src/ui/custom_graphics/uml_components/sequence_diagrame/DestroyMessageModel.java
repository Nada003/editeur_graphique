package ui.custom_graphics.uml_components.sequence_diagrame;
import ui.custom_graphics.uml_components.UMLModel;
public class DestroyMessageModel implements UMLModel {
    private String name;

    public DestroyMessageModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
