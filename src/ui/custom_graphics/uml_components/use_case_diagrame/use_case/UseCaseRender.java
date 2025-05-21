package ui.custom_graphics.uml_components.use_case_diagrame.use_case;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.uml_components.UMLComponent;
import static utils.TextUtils.getTextWidth;

public class UseCaseRender extends UMLComponent {


    UseCaseModel model;

    public UseCaseRender(UseCaseModel model) {
        this.model = model;
        this.setOpaque(false);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(Color.black);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawOval(10, 10, getWidth()-20, getHeight()-20);
      
        graphics2D.setPaint(Color.black);

        graphics2D.drawString(model.scenario,(getWidth()-getTextWidth(model.scenario))/2, getHeight()/2);


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
