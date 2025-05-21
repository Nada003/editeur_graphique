package ui.custom_graphics.uml_components.shapes;

import ui.custom_graphics.uml_components.UMLModel;

public class RectangleModel implements UMLModel {
    private String label;

    public RectangleModel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
