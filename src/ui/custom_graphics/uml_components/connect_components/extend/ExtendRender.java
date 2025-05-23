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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Ligne pointillée
        float[] dash = {5f};
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        g2d.setColor(Color.black);

        int x1 = 10, y1 = getHeight() / 2;
        int x2 = getWidth() - 20, y2 = getHeight() / 2;

        // Ligne sans la flèche
        g2d.drawLine(x1, y1, x2 - 10, y2);

        // Flèche
        drawArrowHead(g2d, x2, y2);

        // Texte <<extend>>
        drawLabel(g2d, "<<extend>>", x1, x2, y1);
    }

    private void drawArrowHead(Graphics2D g2d, int x, int y) {
        int size = 10;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x - size, y - size, x, y);
        g2d.drawLine(x - size, y + size, x, y);
        g2d.setStroke(oldStroke);
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
    public void drawHead(Graphics2D g2d, Point... point) {
        drawArrowHead(g2d, point[0].x, point[0].y);
    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        return null;
    }
}
