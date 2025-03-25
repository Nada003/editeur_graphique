package ui.custom_graphics.uml_components.class_diagram.interfaces;

import ui.custom_graphics.uml_components.UMLComponent;

import java.awt.*;

public class InterfaceRender extends UMLComponent {
    private final InterfaceModel model ;
    int elements;

    public InterfaceRender(InterfaceModel model) {
        super();
        super.setId(UMLComponent.getCount());
        UMLComponent.setCount(UMLComponent.getCount()+1);
        this.model = model;

        elements = 3 + (model.att.length == 0 ? 0: model.att.length-1 )+ (model.functions.length == 0 ? 0: model.functions.length-1 );
        super.setHeight((32) * elements+8);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        Rectangle outerBorer = new Rectangle(4,4, super.getWidth()-10,(32) * elements);
        graphics2D.draw(outerBorer);

        graphics2D.setColor(Color.BLUE);
        Rectangle innerBorder = new Rectangle(outerBorer.x+5,outerBorer.y+5, ((int) outerBorer.getWidth())-10,25);
        graphics2D.draw(innerBorder);

        Rectangle innerBorderAtt = new Rectangle(outerBorer.x+5,innerBorder.height+ innerBorder.y+5,(int) outerBorer.getWidth()-10,model.att.length == 0 ? 25 : model.att.length * 25);
        graphics2D.draw(innerBorderAtt);

        Rectangle innerBorderFunctions = new Rectangle(outerBorer.x+5,innerBorderAtt.height+ innerBorderAtt.y+5,(int) outerBorer.getWidth()-10,model.functions.length == 0 ? 25 : model.functions.length * 25);
        graphics2D.draw(innerBorderFunctions);

        graphics2D.drawString(model.name, (int) ((10+innerBorder.getWidth()-model.name.length())/2),innerBorder.y+18);

        var t1 = new Thread(()->{
            for (int i = 0; i < model.att.length; i++)
                graphics2D.drawString(model.att[i],innerBorderAtt.x+20,innerBorderAtt.y+18*(i+1));
        });

        var t2= new Thread(()->{
            for (int i = 0; i < model.functions.length; i++)
                graphics2D.drawString(model.functions[i],innerBorderFunctions.x+20,innerBorderFunctions.y+18*(i+1));
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
