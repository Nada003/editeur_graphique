package ui.custom_graphics.uml_components.use_case_diagrame;

import ui.custom_graphics.uml_components.UMLModel;

public class ActorModel implements UMLModel{
    private String name;

    public ActorModel(String name, String[] par, String[] par1) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
