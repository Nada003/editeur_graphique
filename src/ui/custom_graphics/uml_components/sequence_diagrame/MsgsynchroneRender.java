package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MsgsynchroneRender extends ResizableUMComponent implements DrawingSpecification {

    MsgsynchroneModel model;

    public MsgsynchroneRender(MsgsynchroneModel model) {
        super.setId(UMLComponent.getCount());
        this.setOpaque(false);
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Ligne pleine horizontale avec une flèche
        int y = getHeight() / 2;
        int x1 = 10;
        int x2 = getWidth() - 10;

        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y, x2 - 10, y);

        // Flèche pleine
        int[] xPoints = {x2 - 10, x2 - 10, x2};
        int[] yPoints = {y - 5, y + 5, y};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void drawHead(Graphics2D graphics2D, Point... point) {
        int x2 = point[1].x;
        int y = point[1].y;

        int[] xPoints = {x2 - 10, x2 - 10, x2};
        int[] yPoints = {y - 5, y + 5, y};

        graphics2D.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(Color.BLACK);
        return graphics2D;
    }
}
