package ui.custom_graphics.uml_components.connect_components.associations;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;

public class AssociationRender extends UMLComponent {

    AssociationModel model;
    public AssociationRender(AssociationModel model) {
        super.setId(UMLComponent.getCount());
        UMLComponent.increaseCount();
        this.model = model;
        this.setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.green);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(2, 2, this.getWidth(), this.getHeight());
    }
}
