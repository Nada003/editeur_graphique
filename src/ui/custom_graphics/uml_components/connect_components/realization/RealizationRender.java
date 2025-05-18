

package ui.custom_graphics.uml_components.connect_components.realization;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

public class RealizationRender extends UMLComponent implements DrawingSpecification {

    RealizationModel model;

    public RealizationRender(RealizationModel model) {
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
        drawHollowArrow(g2d, x2, y2);
    }

    private void drawHollowArrow(Graphics2D g2d, int x, int y) {
        int size = 10;
        int[] xPoints = {x, x - size, x - size};
        int[] yPoints = {y, y - size, y + size};

        g2d.setColor(Color.white);
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(Color.black);
        g2d.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void drawHead(Graphics2D g2d, Point... point) {
        drawHollowArrow(g2d, point[0].x, point[0].y);
    }

    @Override
    public Graphics2D lineStyle() {
        return null;
    }
}
