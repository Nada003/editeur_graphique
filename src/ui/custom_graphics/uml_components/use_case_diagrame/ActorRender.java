package ui.custom_graphics.uml_components.use_case_diagrame;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ActorRender extends UMLComponent {
    private final ActorModel model;

    public ActorRender(ActorModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(100, 150));
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.DARK_GRAY);
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2));

        int centerX = getWidth() / 2;

        g2.drawOval(centerX - 15, 10, 30, 30);
        g2.drawLine(centerX, 40, centerX, 90);
        g2.drawLine(centerX - 20, 60, centerX + 20, 60);
        g2.drawLine(centerX, 90, centerX - 20, 130);
        g2.drawLine(centerX, 90, centerX + 20, 130);

        g2.setStroke(originalStroke);

        Font font = new Font("SansSerif", Font.PLAIN, 12);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        String name = model.getName();
        int nameWidth = fm.stringWidth(name);
        g2.drawString(name, centerX - nameWidth / 2, 145);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
