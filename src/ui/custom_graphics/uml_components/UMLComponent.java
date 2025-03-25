package ui.custom_graphics.uml_components;

import javax.swing.*;
import java.awt.*;

public abstract class UMLComponent extends JPanel {
    private static int count = 0;
    private int Id;
    private Color colorStroke = Color.BLACK, backgroundColor = Color.white;
    private int positionX = 25, positionY = 25, height = 110 , width = 210;

    protected UMLComponent(){
        this.setBounds(positionX,positionY,width,height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(colorStroke);
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        UMLComponent.count = count;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Color getColorStroke() {
        return colorStroke;
    }

    public void setColorStroke(Color colorStroke) {
        this.colorStroke = colorStroke;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
        this.setBounds(positionX,positionY,width,height);
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
        this.setBounds(positionX,positionY,width,height);
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.setBounds(positionX,positionY,width,height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.setBounds(positionX,positionY,width,height);
    }
}
