package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.UMLModel;

public class SequenceDiagramFrameModel implements UMLModel {

    private int x, y, width, height;
    private String name;
    private String attribute;

    public SequenceDiagramFrameModel(String name, String attribute) {
        this.name = name;
        this.attribute = attribute;
        this.width = 300;
        this.height = 200;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAttribute() { return attribute; }
    public void setAttribute(String attribute) { this.attribute = attribute; }
}
