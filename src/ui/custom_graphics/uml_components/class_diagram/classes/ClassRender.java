package ui.custom_graphics.uml_components.class_diagram.classes;

import ui.custom_graphics.ui_elements.UMLComponentParamPopup;
import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ClassRender extends UMLComponent {
    private final ClassModel model;
    int elements;

    public ClassRender(ClassModel model) {
        this.model = model;
        elements = 3 + (model.att.length == 0 ? 0 : model.att.length - 1) + (model.functions.length == 0 ? 0 : model.functions.length - 1);
        super.setHeight((32) * elements + 8);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        Rectangle outerBorer = new Rectangle(4, 4, super.getWidth() - 10, (32) * elements);
        graphics2D.draw(outerBorer);

        graphics2D.setColor(Color.BLUE);
        Rectangle innerBorder = new Rectangle(outerBorer.x + 5, outerBorer.y + 5, ((int) outerBorer.getWidth()) - 10, 25);
        graphics2D.draw(innerBorder);

        Rectangle innerBorderAtt = new Rectangle(outerBorer.x + 5, innerBorder.height + innerBorder.y + 5, (int) outerBorer.getWidth() - 10, model.att.length == 0 ? 25 :  model.att.length * 25);
        graphics2D.draw(innerBorderAtt);

        Rectangle innerBorderFunctions = new Rectangle(outerBorer.x + 5, innerBorderAtt.height + innerBorderAtt.y + 5, (int) outerBorer.getWidth() - 10, model.functions.length == 0 ? 25 : model.functions.length * 25);
        graphics2D.draw(innerBorderFunctions);

        graphics2D.drawString(model.name, (int) ((10 + innerBorder.getWidth() - model.name.length()) / 2), innerBorder.y + 18);


        graphics2D.drawString(model.name, (int) ((10 + innerBorder.getWidth() - model.name.length()) / 2), innerBorder.y + 18);


        int yOffset = innerBorderAtt.y + 20;
        for (String attribute : model.att) {
            graphics2D.drawString(attribute, innerBorderAtt.x + 3, yOffset);

        }


        yOffset = innerBorderFunctions.y + 20;
        for (String function : model.functions) {
            graphics2D.drawString(function, innerBorderFunctions.x + 3, yOffset);

        }




    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // Right-click to open popup
            new UMLComponentParamPopup((className, data) -> {
                String[] attArray = data.length > 0 && !data[0].isEmpty() ? data[0].split(",") : new String[]{};
                String[] funcArray = data.length > 1 && !data[1].isEmpty() ? data[1].split(",") : new String[]{};


                model.name = className;
                model.att = attArray;
                model.functions = funcArray;

                elements = 3 + attArray.length + funcArray.length;
                setHeight(32 * elements + 8);


                repaint();
            }, model.name, model.att, model.functions);
        }
    }
}


