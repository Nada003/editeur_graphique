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

        // Ligne pointillée
        float[] dash = {5f};
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
        g2d.setColor(Color.black);

        int x1 = 10, y1 = getHeight() / 2;
        int x2 = getWidth() - 20, y2 = getHeight() / 2;

        // Dessin de la ligne (arrêtée avant la flèche)
        g2d.drawLine(x1, y1, x2 - 10, y2);

        // Dessin de la tête ">" en trait plein
        drawArrowHead(g2d, x2, y2);
    }

    private void drawArrowHead(Graphics2D g2d, int x, int y) {
        int size = 10;

        Stroke oldStroke = g2d.getStroke(); // sauvegarde du style actuel
        g2d.setStroke(new BasicStroke(2)); // ligne pleine pour la tête

        g2d.drawLine(x - size, y - size, x, y); // ligne diagonale haute
        g2d.drawLine(x - size, y + size, x, y); // ligne diagonale basse

        g2d.setStroke(oldStroke); // restauration du style précédent (pointillé)
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
