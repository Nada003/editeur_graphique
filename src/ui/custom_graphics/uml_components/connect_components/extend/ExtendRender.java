package ui.custom_graphics.uml_components.connect_components.extend;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ExtendRender extends ResizableUMComponent implements DrawingSpecification {

    private ExtendModel model;  // référence au modèle (à créer/importer)

    public ExtendRender() {
        super.setId(UMLComponent.getCount());
        this.setOpaque(false);
    }

    // Nouveau constructeur qui accepte un modèle ExtendModel
    public ExtendRender(ExtendModel model) {
        this();  // appelle le constructeur par défaut pour éviter duplication
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
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void drawHead(Graphics2D graphics2D, Point... point) {
        if (point.length < 2) return;
        graphics2D.setStroke(new BasicStroke());

        Point from = point[0];
        Point to = point[1];
        int arrowSize = 10;

        // Ensure the arrow fits within the canvas bounds
        int translateX = Math.max(0, -Math.min(from.x, to.x));
        int translateY = Math.max(0, -Math.min(from.y, to.y));

        Point p1 = new Point(from.x + translateX, from.y + translateY);
        Point p2 = new Point(to.x + translateX, to.y + translateY);

        // Calculate angle of the line
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        // Arrowhead points based on the angle
        Point arrowLeft = new Point(
                (int) (p2.x - arrowSize * Math.cos(angle - Math.PI / 6)),
                (int) (p2.y - arrowSize * Math.sin(angle - Math.PI / 6))
        );

        Point arrowRight = new Point(
                (int) (p2.x - arrowSize * Math.cos(angle + Math.PI / 6)),
                (int) (p2.y - arrowSize * Math.sin(angle + Math.PI / 6))
        );

        // Draw the arrowhead
        graphics2D.drawLine(p2.x, p2.y, arrowLeft.x, arrowLeft.y);
        graphics2D.drawLine(p2.x, p2.y, arrowRight.x, arrowRight.y);


        int x1 = 10, y1 = getHeight() / 2;
        int x2 = getWidth() - 20, y2 = getHeight() / 2;

        // Texte <<extend>>
        drawLabel(graphics2D, "<<extend>>", x1,x2 , y2);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        float[] dash = {5f};
        graphics2D.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        return graphics2D;
    }
}