package ui.custom_graphics.uml_components.connect_components.generalization;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Renders a generalization relationship in UML diagrams.
 */
public class GeneralizationRender extends ResizableUMComponent implements DrawingSpecification {

    private GeneralizationModel model;

    public GeneralizationRender(GeneralizationModel model) {
        super.setId(UMLComponent.getCount());
        this.setOpaque(false);
        this.model = model;
    }


    private void drawLabel(Graphics2D g2d, String text, int x1, int x2, int y) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = (x1 + x2) / 2 - textWidth / 2;
        int textY = y - 5;
        g2d.setStroke(new BasicStroke(1));
        g2d.drawString(text, textX, textY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Implement mouse click behavior if needed
    }

    @Override
    public void drawHead(Graphics2D graphics2D, Point... points) {
        if (points.length < 2) return;

        Point from = points[0];
        Point to = points[1];
        int arrowSize = 10;

        int translateX = Math.max(0, -Math.min(from.x, to.x));
        int translateY = Math.max(0, -Math.min(from.y, to.y));

        Point p1 = new Point(from.x + translateX, from.y + translateY);
        Point p2 = new Point(to.x + translateX, to.y + translateY);

        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        Point arrowLeft = new Point(
                (int) (p2.x - arrowSize * Math.cos(angle - Math.PI / 6)),
                (int) (p2.y - arrowSize * Math.sin(angle - Math.PI / 6))
        );

        Point arrowRight = new Point(
                (int) (p2.x - arrowSize * Math.cos(angle + Math.PI / 6)),
                (int) (p2.y - arrowSize * Math.sin(angle + Math.PI / 6))
        );

        graphics2D = lineStyle(graphics2D);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);

        int[] xPoints = {p2.x, arrowLeft.x, arrowRight.x};
        int[] yPoints = {p2.y, arrowLeft.y, arrowRight.y};

        graphics2D.setColor(Color.WHITE); // Remplissage blanc
        graphics2D.fillPolygon(xPoints, yPoints, 3);

        graphics2D.setColor(Color.BLACK); // Contour noir
        graphics2D.drawPolygon(xPoints, yPoints, 3);
    }


    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(Color.BLACK);
        return graphics2D;
    }
}
