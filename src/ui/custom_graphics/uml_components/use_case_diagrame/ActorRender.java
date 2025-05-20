package ui.custom_graphics.uml_components.use_case_diagrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;

public class ActorRender extends UMLComponent {
    private ActorModel model;

    public ActorRender(ActorModel model) {
        this.model = model;
       
        this.setPreferredSize(new Dimension(100, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Head (circle)
        g2.setColor(Color.BLACK);
        g2.drawOval(35, 10, 30, 30);

        // Body
        g2.drawLine(50, 40, 50, 90);

        // Arms
        g2.drawLine(30, 60, 70, 60);

        // Legs
        g2.drawLine(50, 90, 30, 130);
        g2.drawLine(50, 90, 70, 130);

        // Draw the name below
        g2.drawString(model.getName(), 20, 145);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
