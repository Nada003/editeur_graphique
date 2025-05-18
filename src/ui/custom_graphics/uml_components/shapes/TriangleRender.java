package ui.custom_graphics.uml_components.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

public class TriangleRender extends UMLComponent implements DrawingSpecification {

    TriangleModel model;

    public TriangleRender(TriangleModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setPreferredSize(new Dimension(100, 100));
        this.setOpaque(false);
    }

   @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int w = getWidth();
    int h = getHeight();
    int[] xPoints = { w / 2, 0, w };
    int[] yPoints = { 0, h, h };

    g2d.setColor(Color.WHITE); // fond
    g2d.fillPolygon(xPoints, yPoints, 3);

    g2d.setColor(Color.BLACK); // bordure
    g2d.setStroke(new BasicStroke(2));
    g2d.drawPolygon(xPoints, yPoints, 3);
}


    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void drawHead(Graphics2D g2d, Point... point) {}

    @Override
    public Graphics2D lineStyle() {
        return null;
    }
}
