package ui.custom_graphics.uml_components.connect_components.generalization;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GeneralizationRender extends UMLComponent {

    GeneralizationModel model;

    public GeneralizationRender(GeneralizationModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(3));


        int x1 = 2, y1 = 2;
        int x2 = this.getWidth() - 2, y2 = this.getHeight() - 2;


        g2d.drawLine(x1, y1, x2, y2);


        drawArrow(g2d, x1, y1, x2, y2);
    }
    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;


        double angle = Math.atan2(y2 - y1, x2 - x1);


        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));


        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x2, y2, x4, y4);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
