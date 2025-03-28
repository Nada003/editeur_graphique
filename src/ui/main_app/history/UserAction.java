package ui.main_app.history;

import ui.custom_graphics.uml_components.UMLComponent;

public class UserAction {
    private String actionName;
    private UMLComponent component;

    public UserAction(String actionName, UMLComponent component) {
        this.actionName = actionName;
        this.component = component;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public UMLComponent getComponent() {
        return component;
    }

    public void setComponent(UMLComponent component) {
        this.component = component;
    }
}
