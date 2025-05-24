package ui.custom_graphics.uml_components.connect_components.composition;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CompositionRender extends ResizableUMComponent implements DrawingSpecification {

    CompositionModel model;

    public CompositionRender(CompositionModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }


    private void drawFilledDiamond(Graphics2D g2d, int x, int y) {
        int size = 20;
        int[] xPoints = {x, x - size, x, x + size};
        int[] yPoints = {y - size / 2, y, y + size / 2, y};

        g2d.setColor(Color.black);
        g2d.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

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

        // Ligne principale
        graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);

        // Construction du losange (4 points)
        int diamondSize = arrowSize;
        Polygon diamond = new Polygon();

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        // Centre du losange : p2 (extrémité de la flèche)
        int x = p2.x;
        int y = p2.y;

        diamond.addPoint(x, y); // pointe avant
        diamond.addPoint((int) (x - diamondSize * cos + (diamondSize / 2.0) * sin),
                (int) (y - diamondSize * sin - (diamondSize / 2.0) * cos)); // coin bas
        diamond.addPoint((int) (x - 2 * diamondSize * cos),
                (int) (y - 2 * diamondSize * sin)); // arrière
        diamond.addPoint((int) (x - diamondSize * cos - (diamondSize / 2.0) * sin),
                (int) (y - diamondSize * sin + (diamondSize / 2.0) * cos)); // coin haut

        graphics2D.fillPolygon(diamond);
    }


    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        float[] dash = {5f};
        graphics2D.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        return graphics2D;
    }
}