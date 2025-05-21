

package ui.custom_graphics.uml_components.sequence_diagrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

public class ClasslifelineRender extends UMLComponent implements DrawingSpecification {

    ClasslifelineModel model;

    public ClasslifelineRender(ClasslifelineModel model) {
        super.setId(UMLComponent.getCount());
        this.setOpaque(false);
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Définir un style de trait pointillé
        float[] dashPattern = {10, 10}; // 10px ligne, 10px vide
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, dashPattern, 0);
        g2d.setStroke(dashed);

        int x = getWidth() / 2;
        int yStart = 0;
        int yEnd = getHeight();

        // Dessiner une ligne verticale en pointillés
        g2d.drawLine(x, yStart, x, yEnd);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void drawHead(Graphics2D graphics2D, Point... point) {
        
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
