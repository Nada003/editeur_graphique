package ui.custom_graphics.uml_components.class_diagram.classes;

import java.awt.*;

public class ClassDetail extends ClassRender {

    public ClassDetail(ClassModel model) {
        super(model);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Dessin normal par ClassRender

        Graphics2D graphics2D = (Graphics2D) g;

        // Dimensions du petit rectangle
        int boxWidth = 20;
        int boxHeight = 20;

        // Position (en haut à droite avec un petit décalage)
        int x = getWidth() - boxWidth - 5;
        int y = 5;

        // Dessiner le rectangle plein (gris clair)
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(x, y, boxWidth, boxHeight);

        // Contour noir
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(x, y, boxWidth, boxHeight);

        // Dessiner le "T" centré
        String label = "T";
        FontMetrics fm = graphics2D.getFontMetrics();
        int textWidth = fm.stringWidth(label);
        int textHeight = fm.getAscent();
        int textX = x + (boxWidth - textWidth) / 2;
        int textY = y + (boxHeight + textHeight) / 2 - 3;

        graphics2D.drawString(label, textX, textY);
    }
}
