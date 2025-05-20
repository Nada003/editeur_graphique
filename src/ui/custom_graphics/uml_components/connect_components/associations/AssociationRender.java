package ui.custom_graphics.uml_components.connect_components.associations;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.DrawingSpecification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AssociationRender extends UMLComponent implements DrawingSpecification {

    AssociationModel model;
    public AssociationRender(AssociationModel model) {
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
        g2d.drawLine(2, 2, this.getWidth(), this.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void drawHead(Graphics2D graphics2D, Point... point) {

    }

    @Override
    public Graphics2D lineStyle(Graphics2D graphics2D) {
        return null;
    }
}
