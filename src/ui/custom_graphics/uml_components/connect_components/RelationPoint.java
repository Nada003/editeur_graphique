package ui.custom_graphics.uml_components.connect_components;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.UMLComponentMovementListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class RelationPoint extends UMLComponent implements UMLComponentMovementListener {

    public UMLComponent belongTO;
    public final LinkedList<RolationPointMovedListner> listeners = new LinkedList<>();
    private int relativePositionX = 0, relativePositionY = 0;

    public RelationPoint(UMLComponent belongTO){
        this.belongTO = belongTO;
        if (belongTO != null) {
            belongTO.addListener(this);
            setPositionX(belongTO.getPositionX());
            setPositionY(belongTO.getPositionY());
        }

        setWidth(10);
        setHeight(10);
        setBackground(new Color(0,0,0,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        if (!isSelected())
            graphics2D.fillOval(0,0,10,10);
        else{
            graphics2D.setColor(Color.white);
            graphics2D.fillOval(0,0,10,10);
            graphics2D.setColor(Color.black);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.drawOval(0, 0, 10, 10);
        }

    }

    @Override
    public void setPositionX(int positionX) {
        if (belongTO == null) {
            super.setPositionX(positionX);
            return;
        }
        if (getPositionY() == belongTO.getPositionY() || getPositionY() == belongTO.getPositionY()+ belongTO.getHeight()-5 ) {
            if (positionX >= belongTO.getPositionX() && positionX <= belongTO.getPositionX() + belongTO.getWidth()-5) {
                super.setPositionX(positionX);
            }
        }
        else if (positionX == belongTO.getPositionX() || positionX == belongTO.getPositionX() + belongTO.getWidth()-5) {
            super.setPositionX(positionX);
        }
        relativePositionX = this.getPositionX() - belongTO.getPositionX();

    }

    @Override
    public void setPositionY(int positionY) {
        if (belongTO == null) {
            super.setPositionY(positionY);
            return;
        }
        if (getPositionX() == belongTO.getPositionX() || getPositionX() == belongTO.getPositionX() + belongTO.getWidth()-5) {
            if (positionY >= belongTO.getPositionY() && positionY <= belongTO.getPositionY() + belongTO.getHeight()-5)
                super.setPositionY(positionY);
        } else if (positionY == belongTO.getPositionY() || positionY == belongTO.getPositionY()+ belongTO.getHeight()-5) {
            super.setPositionY(positionY);
        }
        relativePositionY = this.getPositionY() - belongTO.getPositionY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (listeners.size() < 2) return;
        Relation part1 = null , part2 = null;
        for (var l : listeners)
            if (l instanceof Relation obj){
                if (obj.getEnd() == this)
                    part1 = obj;
                else
                    part2 = obj;
            }

        assert part1 != null;
        assert part2 != null;
        part1.setEnd(part2.getEnd());
        part1.getBoardComponents().removeElement(part2);
        part1.getBoardComponents().removeElement(this);
    }

    @Override
    public void componentMoved(UMLComponent c) {
        setPositionX(c.getPositionX()+relativePositionX);
        setPositionY(c.getPositionY()+relativePositionY);
    }

    @Override
    public void notifyAllListeners() {
        super.notifyAllListeners();
        notifyAllRelationListeners();
    }

    public void notifyAllRelationListeners() {
        for (var v : listeners)
            v.doAction();
    }

    public void addListener(RolationPointMovedListner l){
        if (! listeners.contains(l))
            listeners.add(l);
    }

    public void removeListener(RolationPointMovedListner l){
        listeners.remove(l);
    }

    public void setBelongsTo(UMLComponent draggedPanel) {
        this.belongTO = draggedPanel;
        if (draggedPanel != null) {
            draggedPanel.addListener(this);
            setPositionX(draggedPanel.getPositionX()+ relativePositionX);
            setPositionY(draggedPanel.getPositionY() + relativePositionY);
        }
        revalidate();
        repaint();

    }
}
