package ui.custom_graphics.uml_components.class_diagram.classes;

import java.awt.*;
import java.awt.event.MouseEvent;
import ui.custom_graphics.ui_elements.UMLComponentParamPopup;
import ui.custom_graphics.uml_components.UMLComponent;

public class ClassRender extends UMLComponent {
    protected final ClassModel model;
    int elements;

    public ClassRender(ClassModel model) {
        this.model = model;
        elements = 3 + model.att.length + model.functions.length;
        super.setHeight(32 * elements + 8);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        Rectangle outerBorder = new Rectangle(4, 4, super.getWidth() - 10, 32 * elements);
        graphics2D.draw(outerBorder);

        graphics2D.setColor(Color.BLUE);
        Rectangle innerBorder = new Rectangle(outerBorder.x + 5, outerBorder.y + 5, outerBorder.width - 10, 25);
        graphics2D.draw(innerBorder);

        Rectangle innerBorderAtt = new Rectangle(outerBorder.x + 5, innerBorder.y + innerBorder.height + 5,
                outerBorder.width - 10, model.att.length == 0 ? 25 : model.att.length * 25);
        graphics2D.draw(innerBorderAtt);

        Rectangle innerBorderFunctions = new Rectangle(outerBorder.x + 5, innerBorderAtt.y + innerBorderAtt.height + 5,
                outerBorder.width - 10, model.functions.length == 0 ? 25 : model.functions.length * 25);
        graphics2D.draw(innerBorderFunctions);

        // Dessiner le stéréotype (<<interface>>, <<enumeration>>, etc.)
        String stereotypeText = model.stereotype;
        FontMetrics fm = graphics2D.getFontMetrics();
        int componentWidth = getWidth();
        int y = innerBorder.y + 15;

        if (!stereotypeText.isEmpty()) {
            int stereotypeWidth = fm.stringWidth(stereotypeText);
            int x = (componentWidth - stereotypeWidth) / 2;
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(stereotypeText, x, y);
            y += 15;
        }

        // Dessiner le nom de la classe
        int nameWidth = fm.stringWidth(model.name);
        int nameX = (componentWidth - nameWidth) / 2;
        graphics2D.drawString(model.name, nameX, y);

        // Dessiner les attributs
        int yOffset = innerBorderAtt.y + 20;
        for (String attribute : model.att) {
            graphics2D.drawString(attribute, innerBorderAtt.x + 10, yOffset);
            yOffset += 20;
        }

        // Dessiner les fonctions
        yOffset = innerBorderFunctions.y + 20;
        for (String function : model.functions) {
            graphics2D.drawString(function, innerBorderFunctions.x + 10, yOffset);
            yOffset += 20;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
            new UMLComponentParamPopup((className, data) -> {
                String[] attArray = data.length > 0 && !data[0].isEmpty() ? data[0].split("\n") : new String[]{};
                String[] funcArray = data.length > 1 && !data[1].isEmpty() ? data[1].split("\n") : new String[]{};

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
