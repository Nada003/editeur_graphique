package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.ResizableUMComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ActivationBarRender extends ResizableUMComponent implements MouseListener {
    private final ActivationBarModel model;
    private static final int FIXED_WIDTH = 25;
    public ActivationBarRender(ActivationBarModel model) {
        super();
        this.model = model;
        setOpaque(false);
        setWidth(FIXED_WIDTH);
        setHeight(80);
        setMaxWidth(FIXED_WIDTH);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.dispose();

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