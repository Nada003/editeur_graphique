package ui.custom_graphics.uml_components.connect_components;

import java.awt.*;

public interface DrawingSpecification {
    void drawHead(Graphics2D graphics2D, Point ... point);
    Graphics2D lineStyle();
}
