package ui.custom_graphics.uml_components.connect_components.generalization;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GeneralizationRender extends ResizableUMComponent implements DrawingSpecification {

    GeneralizationModel model;

    public GeneralizationRender(GeneralizationModel model) {
        super.setId(UMLComponent.getCount());
        this.setOpaque(false);
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        int y = getHeight() / 2;
        int x1 = 10;
        int x2 = getWidth() - 10;

        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y, x2 - 10, y);

        int xTip = x2;
        int xBase = x2 - 10;
        int yTop = y - 5;
        int yBottom = y + 5;

        g2d.drawLine(xBase, yTop, xTip, y);
        g2d.drawLine(xBase, yBottom, xTip, y);
        g2d.drawLine(xBase, yTop, xBase, yBottom);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void drawHead(Graphics2D graphics2D, Point... point) {
        int xTip = point[1].x;
        int y = point[1].y;

        int xBase = xTip - 10;
        int yTop = y - 5;
        int yBottom = y + 5;

        graphics2D.drawLine(xBase, yTop, xTip, y);
        graphics2D.drawLine(xBase, yBottom, xTip, y);
        graphics2D.drawLine(xBase, yTop, xBase, yBottom);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(Color.BLACK);
        return graphics2D;
    }
}
