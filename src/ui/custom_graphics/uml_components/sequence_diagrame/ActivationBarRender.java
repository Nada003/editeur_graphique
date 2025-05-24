package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.ResizableUMComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ActivationBarRender extends ResizableUMComponent implements MouseListener {
    private final ActivationBarModel model;

    public ActivationBarRender(ActivationBarModel model) {
        super();
        this.model = model;
        setOpaque(false);
        setWidth(20);
        setHeight(80);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= 0 && x <= getWidth()
                && y >= 0 && y <= getHeight();
    }

    public ActivationBarModel getModel() {
        return model;
    }


    @Override
    public void mouseClicked(MouseEvent e) {}

}
