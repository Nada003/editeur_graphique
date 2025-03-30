package ui.main_app.main_board;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.UMLComponentMovementListener;
import ui.custom_graphics.uml_components.text_and_comments.CommentRender;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class MainBoard extends JPanel implements UMLComponentMovementListener {
    public final WatchedList<UMLComponent> components;
    public WatchedList<UMLComponent> selectedComponents = new WatchedList<>();
    private final Set<Integer> pressedKeys = new HashSet<>(); // Stores currently pressed keys
    private final int MOVE_AMOUNT = 5; // Default move step
    private Timer movementTimer;
    private JScrollPane scrollPane;

    private boolean showGrid = false;
    private CommentRender selectedComment ;
    private Rectangle selectionRect = new Rectangle();
    private Point startPoint;



    private static class PanelDropListener extends DropTargetAdapter {
        private final MainBoard targetPanel;

        public PanelDropListener(MainBoard targetPanel) {
            this.targetPanel = targetPanel;
            new DropTarget(targetPanel, DnDConstants.ACTION_MOVE, this, true, null);
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                UMLComponent draggedPanel = (UMLComponent) dtde.getTransferable()
                        .getTransferData(new DataFlavor(UMLComponent.class, DataFlavor.javaJVMLocalObjectMimeType));

                Point dropPoint = dtde.getLocation();
                dropPoint.translate(-draggedPanel.getWidth() / 2, -draggedPanel.getHeight() / 2);

                targetPanel.components.removeElement(draggedPanel);
                targetPanel.components.addElement(draggedPanel);
                draggedPanel.setLocation(dropPoint);

               

                if (draggedPanel instanceof CommentRender obj) targetPanel.setSelectedComment(obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public MainBoard( WatchedList<UMLComponent> components) {
        this.setFocusable(true);
        this.components = components;
        components.addListener(this::notifyComponentsListChanged);
        selectedComponents.addListener(this::notifySelectedComponentsListChanged);
        new PanelDropListener(this);

        setLayout(null);
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                selectionRect.setBounds(startPoint.x, startPoint.y, 0, 0);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectObjects();
                selectionRect.setBounds(0,0,0,0);
                repaint(); // Final repaint after selection
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = Math.min(startPoint.x, e.getX());
                int y = Math.min(startPoint.y, e.getY());
                int width = Math.abs(startPoint.x - e.getX());
                int height = Math.abs(startPoint.y - e.getY());

                selectionRect.setBounds(x, y, width, height);
                repaint();
            }
        });



        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            synchronized (pressedKeys) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    pressedKeys.add(e.getKeyCode());
                    startMovementTimer();  // Start movement when a key is pressed
                } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                    pressedKeys.remove(e.getKeyCode());
                    if (pressedKeys.isEmpty()) stopMovementTimer(); // Stop when no keys are pressed
                }
            }
            return false; // Allow normal key processing
        });
    }

    public void paintAll() {
        if (components.getList().isEmpty()) return;
        for (var m : components.getList()) {
            if (!checkChild(m)) {
                this.add(m);
                m.addListener(this);
            }
        }
    }

    private void removeAllPainted() {
        for (Component c : getComponents()) {
            if (c instanceof UMLComponent) {
                remove(c);
            }
        }
    }

    private boolean checkChild(Component child) {
        for (var c : getComponents()) {
            if (c.equals(child)) {
                return true;
            }
        }
        return false;
    }


    public void toggleGrid() {
        showGrid = !showGrid;
        repaint();
    }

    public CommentRender getSelectedComment() {
        return selectedComment;
    }

    public void setSelectedComment(CommentRender selectedComment) {
        if (this.selectedComment != null)
            this.selectedComment.setSelected(false);
        selectedComment.setSelected(true);
        this.selectedComment = selectedComment;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (showGrid) {
            g2.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < getWidth(); i += 20) {
                g2.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += 20) {
                g2.drawLine(0, j, getWidth(), j);
            }
        }

        // Draw the selection rectangle
        if (selectionRect.width > 0 && selectionRect.height > 0) {
            g2.setColor(new Color(0, 0, 255, 50)); // Semi-transparent blue
            g2.fill(selectionRect);
            g2.setColor(Color.BLUE);
            g2.draw(selectionRect);
        }
    }


    public void notifyComponentsListChanged() {
        removeAllPainted();
        paintAll();
        repaint();
    }

    public void notifySelectedComponentsListChanged(){
        if (selectedComponents.getList().isEmpty()) return;
        UMLComponent umlComponent = selectedComponents.getList().getLast();
        umlComponent.setSelected(true);
        umlComponent.repaint();
    }

    private void selectObjects(){
        unselect();
        for (var com : components.getList()) {
            if ((com.getPositionX() >= selectionRect.x && com.getWidth() <= selectionRect.width) && (com.getPositionY() >= selectionRect.y && com.getHeight() <= selectionRect.height))
                selectedComponents.addElement(com);
        }
    }

    private void unselect(){
        for (var com : selectedComponents.getList()) {
            com.setSelected(false);
        }
        selectedComponents.removeAll();
    }

    private void startMovementTimer() {
        if (movementTimer == null || !movementTimer.isRunning()) {
            movementTimer = new Timer(50, e -> moveSelectedComponents()); // Moves every 50ms
            movementTimer.start();
        }
    }

    private void stopMovementTimer() {
        if (movementTimer != null) {
            movementTimer.stop();
        }
    }

    private void moveSelectedComponents() {
        int dx = 0, dy = 0;

        if (pressedKeys.contains(KeyEvent.VK_UP)) dy = -MOVE_AMOUNT;
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) dy = MOVE_AMOUNT;
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) dx = -MOVE_AMOUNT;
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) dx = MOVE_AMOUNT;

        // Move diagonally if two direction keys are pressed
        for (var panel : selectedComponents.getList())
            panel.setLocation(new Point(panel.getPositionX() + dx,panel.getPositionY() + dy));
    }

    @Override
    public void componentMoved(UMLComponent c) {
        moveViewPort(c);

        Point[] extremePoints = UMLComponent.getExtremePoints(this.components);
        if (this.getHeight() <= extremePoints[1].y && this.getWidth() <= extremePoints[1].x) {
            // get more space
            this.setPreferredSize(new Dimension((int) (extremePoints[1].x*1.2), (int) (extremePoints[1].y*1.2)));
        }
        else
        if (this.getHeight() <= extremePoints[1].y) {
            // get more space vertical
            this.setPreferredSize(new Dimension(this.getWidth(), (int) (extremePoints[1].y*1.2)));
        } else
        if (this.getWidth() <= extremePoints[1].x) {
            // get more space horizontal
            this.setPreferredSize(new Dimension((int) (extremePoints[1].x*1.2),this.getHeight()));
        }

        revalidate();
        repaint();
    }

    private void moveViewPort(UMLComponent c) {
        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(Color.BLUE);

        Point viewPos = viewport.getViewPosition();
        int viewRight = viewPos.x + scrollPane.getWidth();
        int viewBottom = viewPos.y + scrollPane.getHeight();
        int componentRight = c.getPositionX() + c.getWidth()+20;
        int componentBottom = c.getPositionY() + c.getHeight()+20;

        int newX = viewPos.x;
        int newY = viewPos.y;

        // Move viewport horizontally if needed
        if (viewRight < componentRight) {
            newX += viewport.getX() + MOVE_AMOUNT-3;
        }

        // Move viewport vertically if needed
        if (viewBottom < componentBottom) {
            newY += viewport.getY() +MOVE_AMOUNT-3;
        }

        // Update viewport position if changed
        if (newX != viewPos.x || newY != viewPos.y) {
            viewport.setViewPosition(new Point(newX, newY));
        }
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

}
