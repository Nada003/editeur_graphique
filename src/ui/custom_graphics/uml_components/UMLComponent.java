package ui.custom_graphics.uml_components;

import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public abstract class UMLComponent extends JPanel implements MouseListener, MouseMotionListener, DragGestureListener, Serializable {
    private static int count = 0;

    private int Id;
    private Color colorStroke = Color.BLACK, backgroundColor = Color.white;
    private int rotation = 0, positionX = 400, positionY = 25, height = 110, width = 210;
    private Integer maxWidth, maxHeight;

    private boolean selected ;

    public final LinkedList<UMLComponentMovementListener> listeners = new LinkedList<>();

    private final DragSource dragSource;
    private final UMLComponent instance;

    private static final int RESIZE_MARGIN = 10;
    private boolean resizing = false;
    private Point resizeClickPoint;
    private int resizeCursor = Cursor.DEFAULT_CURSOR;
    private Point lastMousePressLocation;



    protected UMLComponent() {
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Changes to hand cursor

        positionX = 400 + (int) (Math.random() * width);
        positionY = 10 + (int) (Math.random() * height);
        this.setBounds(positionX, positionY, width, height);
        this.addMouseListener(this);
        setFocusable(true);
        this.Id = count;
        increaseCount();
        this.addMouseMotionListener(this);
        instance = this;
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isInResizeCorner(e.getPoint())) {
            resizeCursor = Cursor.SE_RESIZE_CURSOR;
        } else {
            resizeCursor = Cursor.HAND_CURSOR;
        }
        setCursor(Cursor.getPredefinedCursor(resizeCursor));
        System.out.println(resizeCursor);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (resizing) {
            resizeComponent(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastMousePressLocation = e.getPoint();

        if (isInResizeCorner(lastMousePressLocation)) {
            resizing = true;
            resizeClickPoint = lastMousePressLocation;
        }
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        if (lastMousePressLocation != null && isInResizeCorner(lastMousePressLocation)) {
            return; // Cancel drag if press was in resize corner
        }

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
                new DragSourceAdapter() {}
        );

        instance.getParent().remove(instance);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        resizing = false;
    }

    enum ResizeZone {
        NONE, NORTH, SOUTH, EAST, WEST,
        NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST
    }
    private boolean isInResizeCorner(Point p) {
        return getResizeZone(p) != ResizeZone.NONE;
    }


    private ResizeZone getResizeZone(Point p) {
        boolean left = p.x <= RESIZE_MARGIN;
        boolean right = p.x >= getWidth() - RESIZE_MARGIN;
        boolean top = p.y <= RESIZE_MARGIN;
        boolean bottom = p.y >= getHeight() - RESIZE_MARGIN;

        if (top && left) return ResizeZone.NORTH_WEST;
        if (top && right) return ResizeZone.NORTH_EAST;
        if (bottom && left) return ResizeZone.SOUTH_WEST;
        if (bottom && right) return ResizeZone.SOUTH_EAST;
        if (top) return ResizeZone.NORTH;
        if (bottom) return ResizeZone.SOUTH;
        if (left) return ResizeZone.WEST;
        if (right) return ResizeZone.EAST;

        return ResizeZone.NONE;
    }


    private void resizeComponent(MouseEvent e) {
        int dx = e.getX() - resizeClickPoint.x;
        int dy = e.getY() - resizeClickPoint.y;

        int newX = getX();
        int newY = getY();
        int newWidth = getWidth();
        int newHeight = getHeight();

        // Left edge or top-left corner
        if (resizeClickPoint.x < RESIZE_MARGIN) {
            newX += dx;
            newWidth -= dx;
        }

        // Top edge or top-left corner
        if (resizeClickPoint.y < RESIZE_MARGIN) {
            newY += dy;
            newHeight -= dy;
        }

        // Right edge or bottom-right corner
        if (resizeClickPoint.x > getWidth() - RESIZE_MARGIN) {
            newWidth += dx;
        }

        // Bottom edge or bottom-right corner
        if (resizeClickPoint.y > getHeight() - RESIZE_MARGIN) {
            newHeight += dy;
        }

        // Enforce minimum dimensions
        if (newWidth < 50) {
            newX = getX(); // cancel position shift
            newWidth = 50;
        }
        if (newHeight < 50) {
            newY = getY();
            newHeight = 50;
        }

        if  (maxWidth == null || newWidth <= maxWidth){
            setWidth(newWidth);
            setPositionX(newX);
        }

        if  (maxHeight == null || newHeight <= maxHeight){
            setHeight(newHeight);
            setPositionY(newY);
        }

        revalidate();
        repaint();

        resizeClickPoint = e.getPoint(); // Update for continuous resizing
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

    @Override
    public String toString() {
        return this.getClass() + "[x : " +positionX + ", y : " + positionY + ", width: " + width + ", height : " + height+"]";
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }
}


