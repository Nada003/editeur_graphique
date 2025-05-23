package ui.custom_graphics.uml_components.shapes;

import ui.custom_graphics.uml_components.ResizableUMComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CadreMochRender extends ResizableUMComponent {
    private final CadreMochModel model;

    public CadreMochRender(CadreMochModel model) {
        this.model = model;
        setBounds(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 0)); // contour noir
        g2d.setStroke(new BasicStroke(2));

        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // juste le contour
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
