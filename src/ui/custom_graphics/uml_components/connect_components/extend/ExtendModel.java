package ui.custom_graphics.uml_components.connect_components.extend;

import ui.custom_graphics.uml_components.UMLModel;

public class ExtendModel implements UMLModel {

    private String sourceId;
    private String targetId;

    public ExtendModel(String sourceId, String targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
