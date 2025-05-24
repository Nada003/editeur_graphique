package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.UMLModel;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "DestroyMessageModel{name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DestroyMessageModel)) return false;
        DestroyMessageModel that = (DestroyMessageModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
