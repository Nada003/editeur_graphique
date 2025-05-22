package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.UMLModel;

public class ActivationBarModel implements UMLModel {
    private int x, y, width, height;
    private String name;

    public ActivationBarModel(String name) {
        this.name = name;
        this.width = 12;
        this.height = 60;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
