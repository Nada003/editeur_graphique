package ui.custom_graphics.uml_components;

import ui.custom_graphics.ui_elements.UMLComponentParamPopup;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.LinkedList;

public abstract class UMLComponent extends JPanel implements MouseListener, DragGestureListener {
    private static int count = 0;

    private int Id;
    private Color colorStroke = Color.BLACK, backgroundColor = Color.white;
    private int rotation = 0, positionX = 400, positionY = 25, height = 110, width = 210;
    private boolean selected ;

    private final LinkedList<UMLComponentMovementListener> listeners = new LinkedList<>();

    private final DragSource dragSource;
    private final UMLComponent instance;

    protected UMLComponent() {
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Changes to hand cursor

        positionX += count * 20;
        positionY += count * 20;
        this.setBounds(positionX, positionY, width, height);
        this.addMouseListener(this);
        setFocusable(true);
        this.Id = count;
        increaseCount();

        instance = this;
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
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
        this.setBounds(positionX, positionY, width, height);
        notifyAllListeners();
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
        this.setBounds(positionX, positionY, width, height);
        notifyAllListeners();
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.setBounds(positionX, positionY, width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.setBounds(positionX, positionY, width, height);
    }

    public static void increaseCount() {
        count++;
    }

    public static void decreaseCount() {
        count--;
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof UMLComponent && this.getClass().equals(obj.getClass()) && this.Id == ((UMLComponent) obj).Id;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        Transferable transferable = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{new DataFlavor(UMLComponent.class, "UMLComponent")};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(new DataFlavor(UMLComponent.class, "UMLComponent"));
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                return instance;
            }
        };

        dragSource.startDrag(
                dge,
                DragSource.DefaultMoveDrop,
                transferable,
                new DragSourceAdapter() {
                }
        );
        instance.getParent().remove(instance);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3)
            new UMLComponentParamPopup();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void setLocation(Point p) {
        super.setLocation(p);
        this.setPositionY(p.y);
        this.setPositionX(p.x);
    }


    public static Point[] getExtremePoints(WatchedList<UMLComponent> components){
        int maxX = 0 , maxY = 0, minX = 0, minY = 0;
        for (var c : components.getList()){
            if(maxX < getLastPoint(c).x )
                maxX = getLastPoint(c).x;
            else if (minX > c.getPositionX())
                minX = c.getPositionX();

            if(maxY < getLastPoint(c).y )
                maxY = getLastPoint(c).y;
            else if (minY > c.getPositionY())
                minY = c.getPositionY();
        }

        return new Point[]{new Point(minX,minY) , new Point(maxX,maxY)};
    }

    private static Point getLastPoint(UMLComponent c){
        return new Point( c.getPositionX()+c.getWidth() ,c.getPositionY()+c.getHeight()  );
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void addListener(UMLComponentMovementListener l){
        if (! listeners.contains(l))
            listeners.add(l);
    }
    public void removeListener(UMLComponentMovementListener l){
        listeners.add(l);
    }

    public void notifyAllListeners(){
        for (var v : listeners)
            v.componentMoved(this);
    }
}


