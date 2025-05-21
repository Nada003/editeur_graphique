package ui.custom_graphics.uml_components.use_case_diagrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;

public class ActorRender extends UMLComponent {
     ActorModel model;

    public ActorRender(ActorModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setPreferredSize(new Dimension(100, 150));
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.DARK_GRAY);
        Stroke originalStroke = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(2));

        int centerX = getWidth() / 2;

        // tete
        graphics2D.drawOval(centerX - 15, 10, 30, 30);

        // Corps
        graphics2D.drawLine(centerX, 40, centerX, 90);

        // Bras
        graphics2D.drawLine(centerX - 20, 60, centerX + 20, 60);

        // Jambes
        graphics2D.drawLine(centerX, 90, centerX - 20, 130);
        graphics2D.drawLine(centerX, 90, centerX + 20, 130);

        graphics2D.setStroke(originalStroke);

        // Nom de l’acteur
        Font font = new Font("SansSerif", Font.PLAIN, 12);
        graphics2D.setFont(font);
        FontMetrics fm = graphics2D.getFontMetrics();
        String name = model.getName();
        int nameWidth = fm.stringWidth(name);
        graphics2D.drawString(name, centerX - nameWidth / 2, 145);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
