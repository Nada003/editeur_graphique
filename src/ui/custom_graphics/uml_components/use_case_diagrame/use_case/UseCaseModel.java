package ui.custom_graphics.uml_components.use_case_diagrame.use_case;

import ui.custom_graphics.uml_components.UMLModel;
import utils.UML_diagrame;

public class UseCaseModel implements UMLModel {
    private String scenario;
    public final UML_diagrame DIAGRAM = UML_diagrame.diagrameCasUtilisation;

    public UseCaseModel(String scenario) {
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
