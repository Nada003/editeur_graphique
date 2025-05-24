package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MsgretourRender extends ResizableUMComponent implements DrawingSpecification {
    MsgretourModel model;

    public MsgretourRender(MsgretourModel model) {
        super.setId(UMLComponent.getCount());
        this.setOpaque(false);
        this.model = model;
    }


    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int size = 6;
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(x2, y2);
        arrowHead.addPoint(x2 + size, y2 - size);
        arrowHead.addPoint(x2 + size, y2 + size);
        g2d.fill(arrowHead);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void drawHead(Graphics2D g2d, Point... points) {
        if (points.length < 2) return;

        Point from = points[0]; // origine
        Point to = points[1];   // destination (extrémité arrière dans ce cas)

        // On veut dessiner la tête de flèche vers le point "from"
        double angle = Math.atan2(from.y - to.y, from.x - to.x);
        int arrowLength = 15;
        int arrowWidth = 10;

        // Position du sommet de la flèche (donc "from" maintenant)
        int x1 = from.x;
        int y1 = from.y;

        int x2 = (int) (x1 - arrowLength * Math.cos(angle - Math.PI / 6));
        int y2 = (int) (y1 - arrowLength * Math.sin(angle - Math.PI / 6));

        int x3 = (int) (x1 - arrowLength * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y1 - arrowLength * Math.sin(angle + Math.PI / 6));

        Polygon triangle = new Polygon();
        triangle.addPoint(x1, y1); // pointe
        triangle.addPoint(x2, y2); // coin 1
        triangle.addPoint(x3, y3); // coin 2

        g2d.setColor(Color.BLACK);
        g2d.fill(triangle);
        g2d.draw(triangle);
    }


    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        float[] dashPattern = {10, 10};
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, dashPattern, 0);
        graphics2D.setStroke(dashed);
        graphics2D.setColor(Color.BLACK);
        return graphics2D;
    }
}
