package ui.custom_graphics.uml_components.connect_components.dependency;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

public class DependencyRender extends UMLComponent implements DrawingSpecification {

    DependencyModel model;

    public DependencyRender(DependencyModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        float[] dash = {5f};
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));

        int x1 = 10, y1 = getHeight() / 2;
        int x2 = getWidth() - 20, y2 = getHeight() / 2;

        g2d.drawLine(x1, y1, x2 - 10, y2);
        drawArrow(g2d, x2, y2);
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
    public void drawHead(Graphics2D g2d, Point... point) {
        drawArrow(g2d, point[0].x, point[0].y);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        return null;
    }
}