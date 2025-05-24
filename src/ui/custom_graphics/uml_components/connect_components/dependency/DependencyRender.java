package ui.custom_graphics.uml_components.connect_components.dependency;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DependencyRender extends ResizableUMComponent implements DrawingSpecification {

    DependencyModel model;

    public DependencyRender(DependencyModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }


    private void drawArrow(Graphics2D g2d, int x, int y) {
        int size = 10;
        Polygon arrow = new Polygon();
        arrow.addPoint(x, y);
        arrow.addPoint(x - size, y - size);
        arrow.addPoint(x - size, y + size);
        g2d.fill(arrow);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void drawHead(Graphics2D g2d, Point... points) {
        if (points.length < 2) return;

        Point from = points[0];
        Point to = points[1];

        // Angle de la ligne
        double angle = Math.atan2(to.y - from.y, to.x - from.x);
        int arrowLength = 15;
        int arrowWidth = 10;

        // Points du triangle
        int x1 = to.x;
        int y1 = to.y;

        int x2 = (int) (x1 - arrowLength * Math.cos(angle - Math.PI / 6));
        int y2 = (int) (y1 - arrowLength * Math.sin(angle - Math.PI / 6));

        int x3 = (int) (x1 - arrowLength * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y1 - arrowLength * Math.sin(angle + Math.PI / 6));

        Polygon triangle = new Polygon();
        triangle.addPoint(x1, y1);
        triangle.addPoint(x2, y2);
        triangle.addPoint(x3, y3);

        g2d.setColor(Color.BLACK); // fond blanc pour être vide
        g2d.fill(triangle);
        g2d.setColor(Color.BLACK); // contour noir
        g2d.draw(triangle);
    }


    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        float[] dash = {5f};
        graphics2D.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        return graphics2D;
    }
}