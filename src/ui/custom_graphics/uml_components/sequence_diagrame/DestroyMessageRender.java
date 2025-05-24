package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.ResizableUMComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DestroyMessageRender extends ResizableUMComponent implements MouseListener, MouseMotionListener {
    private final DestroyMessageModel model;
    private Point initialClick;

    public DestroyMessageRender(DestroyMessageModel model) {
        this.model = model;
        setOpaque(false);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        int crossSize = 8;
        int centerX = width / 2;
        int centerY = height / 2;

        g2.drawLine(centerX - crossSize, centerY - crossSize, centerX + crossSize, centerY + crossSize);
        g2.drawLine(centerX + crossSize, centerY - crossSize, centerX - crossSize, centerY + crossSize);
    }

    public DestroyMessageModel getModel() {
        return model;
    }


    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getX() - initialClick.x;
        int deltaY = e.getY() - initialClick.y;

        int newX = getX() + deltaX;
        int newY = getY() + deltaY;

        setLocation(newX, newY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
