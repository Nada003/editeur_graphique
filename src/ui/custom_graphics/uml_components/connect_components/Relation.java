package ui.custom_graphics.uml_components.connect_components;

import ui.custom_graphics.uml_components.UMLComponent;
import utils.custom_list.WatchedList;

import java.awt.*;
import java.awt.event.MouseEvent;

import static java.awt.geom.Line2D.ptLineDist;

public class Relation extends UMLComponent implements RolationPointMovedListner {

    private final DrawingSpecification drawingSpecification;
    private final  WatchedList<UMLComponent> components;
    private RelationPoint start, end;


    private int normalizedX, normalizedY, normalizedWidth, normalizedHeight;

    public Relation(DrawingSpecification drawingSpecification ,WatchedList<UMLComponent> components){
        this.drawingSpecification = drawingSpecification;
        this.components = components;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start == null || end == null) return;

        Graphics2D g2 = drawingSpecification != null ? drawingSpecification.lineStyle((Graphics2D) g) :  (Graphics2D)g;


        boolean isSpec = drawingSpecification != null;
        int offset = isSpec ? 8 : 0;

        // Get actual width/height
        int width = getWidth();
        int height = getHeight();

        boolean isHorizontal = height <= 10;
        boolean isVertical = width <= 10;

        boolean isAfter = start.getPositionX() >= end.getPositionX();
        boolean isBellow = start.getPositionY() >= end.getPositionY();

        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        // Apply offset for arrowhead
        int offsetX = 0, offsetY = 0;
        if (!isVertical && !isHorizontal) {
            // Diagonal
            x1 = isAfter ? width : 0;
            y1 = isBellow ? height : 0;
            x2 = isAfter ? 0 : width;
            y2 = isBellow ? 0 : height;


        }
        else if (isHorizontal) {
            // Horizontal
            x1 = isAfter ? width : 5;
            y1 = 5;
            x2 = (isAfter ? 5 : width);
            y2 = 5;
        }
        else if (isVertical) {
            // Vertical
            x1 = 5;
            y1 = isBellow ? height : 5;
            x2 = 5;
            y2 = isBellow ? 5 : height;

        }

        g2.drawLine(x1, y1, x2, y2);

        if (drawingSpecification != null && getEnd().belongTO != null) {
            drawingSpecification.drawHead(g2,new Point(start.getPositionX() - getPositionX(),start.getPositionY() - getPositionY()),new Point(end.getPositionX() - getPositionX() + offsetX,end.getPositionY() - getPositionY() + offsetY));

        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        double distance = ptLineDist(start.getPositionX(), start.getPositionY(),end.getPositionX(), end.getPositionY(), e.getX()+getPositionX(), e.getY()+getPositionY());
        if (distance > 20) return;

        // Create a midpoint relation point in the **center** of start and end
        var relationPoint = new RelationPoint(null);
        relationPoint.setLocation(new Point(
                (start.getPositionX() + end.getPositionX()) / 2,
               (start.getPositionY() + end.getPositionY()) / 2 // Use full midpoint
        ));

        components.removeElement(this);

        components.addElement(relationPoint);

        var relation1 = new Relation(drawingSpecification,components);
        relation1.setStart(start);
        relation1.setEnd(relationPoint);
        components.addElement(relation1);

        start.removeListener(this);
        this.setStart(relationPoint);
        this.setEnd(end);
        components.addElement(this);
    }

    public RelationPoint getStart() {
        return start;
    }

    public void setStart(RelationPoint start) {
        this.start = start;
        start.addListener(this);
    }

    public RelationPoint getEnd() {
        return end;
    }

    public void setEnd(RelationPoint end) {
        this.end = end;
        end.addListener(this);
        normalize();
        repaint();
    }

    private void normalize(){
         normalizedX = Math.min(start.getPositionX()+3, end.getPositionX()+3);
         normalizedY = Math.min(start.getPositionY()+3, end.getPositionY()+3);
         normalizedWidth = Math.abs(end.getPositionX()+3 - (start.getPositionX()+3));
         normalizedHeight = Math.abs( end.getPositionY()+3 - (start.getPositionY()+3));
         setPositionX(normalizedX);
         setPositionY(normalizedY);
         setWidth(Math.max(normalizedWidth, 10));
         setHeight(Math.max(normalizedHeight, 10));
    }

    @Override
    public void doAction() {
        if (end == null || start == null ) return;
        normalize();
        repaint();
    }

    public void setPosition(int positionX, int positionY) {
        if (positionX > Math.min(start.getPositionX(), end.getPositionX()) &&
                positionX < Math.max(start.getPositionX(), end.getPositionX()) &&
                positionY > Math.min(start.getPositionY(), end.getPositionY()) &&
                positionY < Math.max(start.getPositionY(), end.getPositionY()) &&
                positionX+getWidth() < Math.max(start.getPositionX(), end.getPositionX()) &&
                positionY+getHeight() < Math.max(start.getPositionY(), end.getPositionY()) )
            super.setLocation(new Point(positionX,positionY));
    }

    @Override
    public void setPositionX(int positionX) {
        if ( positionX > Math.min(start.getPositionX(), end.getPositionX()) && positionX < Math.max(start.getPositionX(), end.getPositionX()))
            super.setPositionX(positionX);
    }

    @Override
    public void setPositionY(int positionY) {
        if ( positionY > Math.min(start.getPositionY(), end.getPositionY()) && positionY < Math.max(start.getPositionY(), end.getPositionY()))
            super.setPositionY(positionY);
    }

    @Override
    public void setLocation(Point p) {
        setPosition(p.x,p.y);
    }

    public WatchedList<UMLComponent> getBoardComponents() {
        return components;
    }
}
