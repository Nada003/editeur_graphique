package ui.custom_graphics.uml_components.connect_components.aggregations;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AggregationRender extends UMLComponent {

    AggregationModel model;

    public AggregationRender(AggregationModel model) {
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

        int x1 = 10, y1 = getHeight() / 2;
        int x2 = getWidth() - 20, y2 = getHeight() / 2;

        g2d.drawLine(x1, y1, x2 - 10, y2);
        drawDiamond(g2d, x2, y2);
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
}

