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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(3));

        int x1 = 10, y1 = getHeight() / 2;
        int x2 = getWidth() - 20, y2 = getHeight() / 2;

        g2d.drawLine(x1, y1, x2 - 10, y2);
        drawFilledDiamond(g2d, x2, y2);
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
    public void drawHead(Graphics2D g2d, Point... point) {
        drawFilledDiamond(g2d, point[0].x, point[0].y);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        return null;
    }
}