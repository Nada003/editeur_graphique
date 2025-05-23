package ui.custom_graphics.uml_components.use_case_diagrame;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ActorRender extends UMLComponent {
    private final ActorModel model;

    public ActorRender(ActorModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        setPreferredSize(new Dimension(100, 150));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.DARK_GRAY);

        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2));

        int centerX = getWidth() / 2;

        // Tête
        g2d.drawOval(centerX - 15, 10, 30, 30);

        // Corps
        g2d.drawLine(centerX, 40, centerX, 90);

        // Bras
        g2d.drawLine(centerX - 20, 60, centerX + 20, 60);

        // Jambes
        g2d.drawLine(centerX, 90, centerX - 20, 130);
        g2d.drawLine(centerX, 90, centerX + 20, 130);

        g2d.setStroke(originalStroke);

        // Nom de l’acteur centré
        Font font = new Font("SansSerif", Font.PLAIN, 12);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        String name = model.getName();
        int nameWidth = fm.stringWidth(name);
        g2d.drawString(name, centerX - nameWidth / 2, 145);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // A implémenter si nécessaire
    }
}
