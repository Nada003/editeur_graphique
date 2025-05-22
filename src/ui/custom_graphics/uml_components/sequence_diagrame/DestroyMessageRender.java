package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DestroyMessageRender extends UMLComponent implements MouseListener, MouseMotionListener {
    private final DestroyMessageModel model;
    private Point initialClick;

    public DestroyMessageRender(DestroyMessageModel model) {
        this.model = model;
        setOpaque(false);
        setBounds(100, 100, 40, 40);
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

        // dessiner la croix X au centre, plus petite
        g2.drawLine(centerX - crossSize, centerY - crossSize, centerX + crossSize, centerY + crossSize);
        g2.drawLine(centerX + crossSize, centerY - crossSize, centerX - crossSize, centerY + crossSize);
    }


    public DestroyMessageModel getModel() {
        return model;
    }

    // MouseListener
    @Override
    public void mousePressed(MouseEvent e) {
        initialClick = e.getPoint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        int thisX = getX();
        int thisY = getY();

        int xMoved = e.getX() - initialClick.x;
        int yMoved = e.getY() - initialClick.y;

        int newX = thisX + xMoved;
        int newY = thisY + yMoved;

        setLocation(newX, newY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
