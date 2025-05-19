package ui.custom_graphics.uml_components.class_diagram.classes;

import java.awt.event.MouseEvent;
import ui.custom_graphics.ui_elements.UMLComponentParamPopup;

public class ClassInterface extends ClassRender {

    public ClassInterface(ClassModel model) {
        super(model);
        this.model.stereotype = "<<interface>>"; 
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit pour modifier
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
