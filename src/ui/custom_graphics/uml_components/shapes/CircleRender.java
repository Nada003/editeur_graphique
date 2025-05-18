package ui.custom_graphics.uml_components.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

public class CircleRender extends UMLComponent implements DrawingSpecification {

    CircleModel model;

    public CircleRender(CircleModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setPreferredSize(new Dimension(100, 100));
        this.setOpaque(false);
    }

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int size = Math.min(getWidth(), getHeight());
    int x = (getWidth() - size) / 2;
    int y = (getHeight() - size) / 2;

    g2d.setColor(Color.WHITE); // fond
    g2d.fillOval(x, y, size - 1, size - 1);

    g2d.setColor(Color.BLACK); // bordure
    g2d.setStroke(new BasicStroke(2));
    g2d.drawOval(x, y, size - 1, size - 1);
}



    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void drawHead(Graphics2D g2d, Point... point) {}

    @Override
    public Graphics2D lineStyle() {
        return null;
    }
}
