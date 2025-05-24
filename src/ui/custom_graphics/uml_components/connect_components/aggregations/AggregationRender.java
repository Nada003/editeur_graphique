package ui.custom_graphics.uml_components.connect_components.aggregations;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AggregationRender extends ResizableUMComponent implements DrawingSpecification {

    AggregationModel model;

    public AggregationRender(AggregationModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }


    private void drawDiamond(Graphics2D g2d, int x, int y) {
        int size = 20;
        int[] xPoints = {x, x - size, x, x + size};
        int[] yPoints = {y - size / 2, y, y + size / 2, y};

        g2d.setColor(Color.white);
        g2d.fillPolygon(xPoints, yPoints, 4);

        g2d.setColor(Color.black);
        g2d.drawPolygon(xPoints, yPoints, 4);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void drawHead(Graphics2D graphics2D, Point... points) {
        if (points.length < 2) return;

        Point from = points[0];
        Point to = points[1];
        int arrowSize = 20;

        int translateX = Math.max(0, -Math.min(from.x, to.x));
        int translateY = Math.max(0, -Math.min(from.y, to.y));

        Point p1 = new Point(from.x + translateX, from.y + translateY);
        Point p2 = new Point(to.x + translateX, to.y + translateY);

        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        graphics2D = lineStyle(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(2));

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        int cx = (int) (p2.x - arrowSize * cos);
        int cy = (int) (p2.y - arrowSize * sin);

        graphics2D.drawLine(p1.x, p1.y, cx, cy);

        Polygon diamond = new Polygon();

        diamond.addPoint(p2.x, p2.y);
        diamond.addPoint((int) (p2.x - arrowSize * cos + (arrowSize / 2.0) * sin),
                (int) (p2.y - arrowSize * sin - (arrowSize / 2.0) * cos));
        diamond.addPoint((int) (p2.x - 2 * arrowSize * cos),
                (int) (p2.y - 2 * arrowSize * sin));
        diamond.addPoint((int) (p2.x - arrowSize * cos - (arrowSize / 2.0) * sin),
                (int) (p2.y - arrowSize * sin + (arrowSize / 2.0) * cos));

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillPolygon(diamond);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawPolygon(diamond);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        float[] dash = {5f};
        graphics2D.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        return graphics2D;
    }
}

