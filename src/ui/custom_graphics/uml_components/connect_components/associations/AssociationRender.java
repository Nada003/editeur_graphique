package ui.custom_graphics.uml_components.connect_components.associations;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AssociationRender extends ResizableUMComponent implements DrawingSpecification {

    AssociationModel model;
    public AssociationRender(AssociationModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }



    @Override
    public void mouseClicked(MouseEvent e) {

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

        graphics2D = lineStyle(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(2));

        graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);


    }





    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        float[] dash = {5f};
        graphics2D.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        return graphics2D;
    }
}
