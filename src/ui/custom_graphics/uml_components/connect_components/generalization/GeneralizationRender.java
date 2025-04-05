package ui.custom_graphics.uml_components.connect_components.generalization;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GeneralizationRender extends UMLComponent implements DrawingSpecification {

    GeneralizationModel model;

    public GeneralizationRender(GeneralizationModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(3));


        int x1 = 2, y1 = 2;
        int x2 = this.getWidth() - 2, y2 = this.getHeight() - 2;


        g2d.drawLine(x1, y1, x2, y2);


        drawArrow(g2d, x1, y1, x2, y2);
    }
    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;


        double angle = Math.atan2(y2 - y1, x2 - x1);


        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));


        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x2, y2, x4, y4);
    }

    @Override
    public void drawHead(Graphics2D graphics2D, Point... point) {
        if (point.length < 2) return;

        Point from = point[0];
        Point to = point[1];

        int arrowSize = 10;

        // Calculate the bounding box to ensure everything fits in the canvas
        int minX = Math.min(from.x, to.x);
        int minY = Math.min(from.y, to.y);
        int maxX = Math.max(from.x, to.x);
        int maxY = Math.max(from.y, to.y);

        // Calculate the translation required to move the line into the canvas view
        int translateX = Math.max(0, -minX);  // Shift right if the minX is negative
        int translateY = Math.max(0, -minY);  // Shift down if the minY is negative

        // Apply the translation to both points
        Point translatedFrom = new Point(from.x + translateX, from.y + translateY);
        Point translatedTo = new Point(to.x + translateX, to.y + translateY);

        // Angle from 'translatedFrom' to 'translatedTo', correctly handles all directions
        double angle = Math.atan2(translatedTo.y - translatedFrom.y, translatedTo.x - translatedFrom.x);

        // Arrowhead base points (adjusted)
        int x3 = (int) (translatedTo.x - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (translatedTo.y - arrowSize * Math.sin(angle - Math.PI / 6));

        int x4 = (int) (translatedTo.x - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (translatedTo.y - arrowSize * Math.sin(angle + Math.PI / 6));

        // Special handling for vertical lines (same x-coordinates)
        if (translatedFrom.x == translatedTo.x) {
            if (translatedFrom.y > translatedTo.y) {
                // If 'from' is below 'to', the arrowhead should point upwards
                int tempX = x3;
                int tempY = y3;
                x3 = x4;
                y3 = y4;
                x4 = tempX;
                y4 = tempY;
            }
        }

        // Special handling for horizontal lines (same y-coordinates)
        if (translatedFrom.y == translatedTo.y) {
            if (translatedFrom.x > translatedTo.x) {
                // If 'from' is to the right of 'to', the arrowhead should point to the left
                int tempX = x3;
                int tempY = y3;
                x3 = x4;
                y3 = y4;
                x4 = tempX;
                y4 = tempY;
            }
        }


        // Draw the arrowhead
        graphics2D.drawLine(translatedTo.x, translatedTo.y, x3, y3);  // First side of the arrowhead
        graphics2D.drawLine(translatedTo.x, translatedTo.y, x4, y4);  // Second side of the arrowhead
    }







    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public Graphics2D lineStyle() {
        return null;
    }
}
