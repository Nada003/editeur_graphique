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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Style de trait pointillé
        float[] dashPattern = {10, 10};
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, dashPattern, 0);
        g2d.setStroke(dashed);

        int x1 = getWidth() - 10; // droite
        int x2 = 10;              // gauche
        int y = getHeight() / 2;

        // Dessiner la ligne horizontale pointillée
        g2d.drawLine(x1, y, x2 + 10, y);

        // Dessiner la flèche pleine à gauche
        drawArrow(g2d, x2 + 10, y, x2, y);
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
    public void drawHead(Graphics2D graphics2D, Point... point) {
        // Optionnel : dessiner la flèche manuellement si nécessaire
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
