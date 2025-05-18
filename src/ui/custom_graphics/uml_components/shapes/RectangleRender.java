package ui.custom_graphics.uml_components.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

public class RectangleRender extends UMLComponent implements DrawingSpecification {

    RectangleModel model;

    public RectangleRender(RectangleModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setPreferredSize(new Dimension(120, 70));
        this.setOpaque(false);
    }

   @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int width = getWidth();
    int height = getHeight();

    g2d.setColor(Color.WHITE); // fond
    g2d.fillRect(0, 0, width - 1, height - 1);

    g2d.setColor(Color.BLACK); // bordure
    g2d.setStroke(new BasicStroke(2));
    g2d.drawRect(0, 0, width - 1, height - 1);
}

}
