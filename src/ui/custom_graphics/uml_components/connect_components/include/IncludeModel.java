package ui.custom_graphics.uml_components.connect_components.include;

import ui.custom_graphics.uml_components.UMLModel;

import java.awt.*;

public class IncludeModel implements UMLModel {

    private Point startPoint;
    private Point endPoint;

    public IncludeModel(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
}
