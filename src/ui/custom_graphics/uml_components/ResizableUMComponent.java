package ui.custom_graphics.uml_components;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public abstract class ResizableUMComponent extends UMLComponent implements MouseMotionListener{
    private Integer maxWidth, maxHeight;
    private final ResizableUMComponent instance;

    private static final int RESIZE_MARGIN = 10;
    private boolean resizing = false;
    private Point resizeClickPoint;
    private int resizeCursor = Cursor.DEFAULT_CURSOR;
    private Point lastMousePressLocation;

    protected ResizableUMComponent(){
        this.addMouseMotionListener(this);
        instance = this;
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

        super.dragSource.startDrag(
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
