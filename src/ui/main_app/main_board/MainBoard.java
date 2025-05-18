package ui.main_app.main_board;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.UMLComponentMovementListener;
import ui.custom_graphics.uml_components.connect_components.Relation;
import ui.custom_graphics.uml_components.connect_components.RelationPoint;
import ui.custom_graphics.uml_components.text_and_comments.CommentRender;
import ui.main_app.history.UserAction;
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
import java.util.LinkedList;
import java.util.Set;

import static ui.main_app.Application.mainFlow;

public class MainBoard extends JPanel implements UMLComponentMovementListener {
    // Modern color scheme
    private static final Color BACKGROUND_COLOR = new Color(252, 253, 255);
    private static final Color GRID_COLOR = new Color(232, 236, 241);
    private static final Color GRID_ACCENT_COLOR = new Color(220, 226, 234);
    private static final Color SELECTION_FILL_COLOR = new Color(66, 133, 244, 30);
    private static final Color SELECTION_BORDER_COLOR = new Color(66, 133, 244, 180);

    public final WatchedList<UMLComponent> components;
    public WatchedList<UMLComponent> selectedComponents = new WatchedList<>();
    private final Set<Integer> pressedKeys = new HashSet<>(); // Stores currently pressed keys
    private final int MOVE_AMOUNT = 5; // Default move step
    private Timer movementTimer;
    private JScrollPane scrollPane;

    private boolean showGrid = true; 
    private CommentRender selectedComment;
    private Rectangle selectionRect = new Rectangle();
    private Point startPoint;

    private static Relation relation;
    private static UMLComponent clickedComponent;

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

                // Snap to grid if grid is enabled
                if (targetPanel.showGrid) {
                    int gridSize = 20;
                    dropPoint.x = Math.round(dropPoint.x / (float)gridSize) * gridSize;
                    dropPoint.y = Math.round(dropPoint.y / (float)gridSize) * gridSize;
                }

                draggedPanel.listeners.clear();
                targetPanel.components.removeElement(draggedPanel);
                targetPanel.components.addElement(draggedPanel);
                updateRelationPoints(targetPanel.components, draggedPanel);
                draggedPanel.setLocation(dropPoint);
                if (draggedPanel instanceof RelationPoint obj) {
                    updateRelations(obj, targetPanel.components);
                }

                if (draggedPanel.isSelected()) {
                    targetPanel.selectedComponents.removeElement(draggedPanel);
                    targetPanel.selectedComponents.addElement(draggedPanel);
                }

                if (draggedPanel instanceof CommentRender obj) targetPanel.setSelectedComment(obj);

                // Add visual feedback for drop
                targetPanel.showDropFeedback(dropPoint);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void updateRelations(RelationPoint relationPoint, WatchedList<UMLComponent> components) {
            Relation r1 = null, r2 = null;
            relationPoint.listeners.clear();
            for (var v : components.getList()) {
                if (v instanceof Relation obj) {
                    if (obj.getStart().equals(relationPoint)) {
                        r1 = obj;
                        relationPoint.addListener(obj);
                    } else if (obj.getEnd().equals(relationPoint)) {
                        r2 = obj;
                        relationPoint.addListener(obj);
                    }
                }
            }

            assert r1 != null;
            r1.setStart(relationPoint);
            r1.setEnd(r1.getEnd());
            assert r2 != null;
            r2.setEnd(relationPoint);
        }

        private void updateRelationPoints(WatchedList<UMLComponent> list, UMLComponent draggedPanel) {
            var temp = new LinkedList<UMLComponent>();
            for (UMLComponent component : list.getList()) {
                if (component instanceof RelationPoint obj && obj.belongTO != null && obj.belongTO.equals(draggedPanel)) {
                    obj.setBelongsTo(draggedPanel);
                    temp.add(obj);
                }
            }

            for (var v : temp)
                list.reAdd(v);
        }
    }

    public MainBoard(WatchedList<UMLComponent> components) {
        this.setFocusable(true);
        this.components = components;
        components.addListener(this::notifyComponentsListChanged);
        selectedComponents.addListener(this::notifySelectedComponentsListChanged);
        new PanelDropListener(this);

        setLayout(null);
        setBackground(BACKGROUND_COLOR);

        // Enhanced mouse listeners for better UX
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
                selectionRect.setBounds(0, 0, 0, 0);
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
                    if (pressedKeys.add(e.getKeyCode())) { // Only add & start if not already pressed
                        startMovementTimer();
                    }
                } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                    pressedKeys.remove(e.getKeyCode());
                    if (pressedKeys.isEmpty()) {
                        stopMovementTimer();
                    }
                }
            }
            return false; // Allow normal key processing
        });
    }

    // Visual feedback for drop operations
    private void showDropFeedback(Point location) {
        JPanel feedback = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

             
            }
        };

        feedback.setOpaque(false);
        feedback.setBounds(location.x - 20, location.y - 20, 40, 40);
        add(feedback);

        // Animate the feedback
        Timer timer = new Timer(500, e -> {
            remove(feedback);
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void paintAll() {
        if (components.getList().isEmpty()) return;
        for (var m : components.getList()) {
            if (!checkChild(m)) {
                this.add(m, 0);
                m.addListener(this);
                if (!(m instanceof RelationPoint))
                    m.addMouseListener(new MouseAdapter() {
                        @Override
                        public synchronized void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            if (relation == null) return;
                            clickedComponent = m;
                            RelationPoint model = new RelationPoint(m);
                            if (relation.getStart() == null) {
                                relation.setStart(model);
                                components.addElement(model);
                                mainFlow.addElement(new UserAction("Ajouter point", model));

                            } else if (relation.getStart().belongTO != m && relation.getEnd() == null) {
                                relation.setEnd(model);
                                components.addElement(model);
                                components.addElement(relation);
                                mainFlow.addElement(new UserAction("Ajouter point", model));
                            }
                        }
                    });
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (showGrid) {
            
            g2.setColor(GRID_COLOR);

            
            int gridSize = 20;
            for (int i = 0; i < getWidth(); i += gridSize) {
                g2.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += gridSize) {
                g2.drawLine(0, j, getWidth(), j);
            }

            // Draw accent lines every 5 cells for better orientation
            g2.setColor(GRID_ACCENT_COLOR);
            for (int i = 0; i < getWidth(); i += gridSize * 5) {
                g2.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += gridSize * 5) {
                g2.drawLine(0, j, getWidth(), j);
            }
        }

        // Draw the selection rectangle with improved visuals
        if (selectionRect.width > 0 && selectionRect.height > 0) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            
            g2.setColor(SELECTION_FILL_COLOR);
            g2.fill(selectionRect);

            
            g2.setColor(SELECTION_BORDER_COLOR);
            Stroke dashed = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                           0, new float[]{5}, 0);
            g2.setStroke(dashed);
            g2.draw(selectionRect);
        }
    }

    public void notifyComponentsListChanged() {
        removeAllPainted();
        paintAll();
        repaint();
    }

    public synchronized void notifySelectedComponentsListChanged() {
        if (selectedComponents.getList().isEmpty()) return;
        UMLComponent umlComponent = selectedComponents.getList().getLast();
        umlComponent.setSelected(true);
        umlComponent.repaint();
    }

    private void selectObjects() {
        unselect();
        for (var com : components.getList()) {
            if ((com.getPositionX() + 5 > selectionRect.x && com.getPositionX() + com.getWidth() + 5 < selectionRect.x + selectionRect.width) &&
                (com.getPositionY() + 5 > selectionRect.y && com.getPositionY() + com.getHeight() + 5 < selectionRect.y + selectionRect.height))
                selectedComponents.addElement(com);
        }
    }

    private void unselect() {
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

        // Move components based on their type
        for (var panel : selectedComponents.getList()) {
            if (!(panel instanceof RelationPoint) && !(panel instanceof Relation)) {
             
                panel.setLocation(new Point(panel.getPositionX() + dx, panel.getPositionY() + dy));
            } else if ((panel instanceof RelationPoint) && ((RelationPoint) panel).belongTO == null) {
                
                panel.setLocation(new Point(panel.getPositionX() + dx, panel.getPositionY() + dy));
            } else if (((panel instanceof RelationPoint) && !((RelationPoint) panel).belongTO.isSelected()) || (panel instanceof Relation)) {
               
                panel.setLocation(new Point(panel.getPositionX() + dx / MOVE_AMOUNT, panel.getPositionY() + dy / MOVE_AMOUNT));
            }
        }
    }

    @Override
    public void componentMoved(UMLComponent c) {
        moveViewPort(c);

        Point[] extremePoints = UMLComponent.getExtremePoints(this.components);
        if (this.getHeight() <= extremePoints[1].y && this.getWidth() <= extremePoints[1].x) {
            // get more space
            this.setPreferredSize(new Dimension((int) (extremePoints[1].x * 1.2), (int) (extremePoints[1].y * 1.2)));
        } else if (this.getHeight() <= extremePoints[1].y) {
            // get more space vertical
            this.setPreferredSize(new Dimension(this.getWidth(), (int) (extremePoints[1].y * 1.2)));
        } else if (this.getWidth() <= extremePoints[1].x) {
            // get more space horizontal
            this.setPreferredSize(new Dimension((int) (extremePoints[1].x * 1.2), this.getHeight()));
        }

        revalidate();
        repaint();
    }

    private void moveViewPort(UMLComponent c) {
        if (scrollPane == null) return;

        JViewport viewport = scrollPane.getViewport();
        Point viewPos = viewport.getViewPosition();
        int viewRight = viewPos.x + scrollPane.getWidth();
        int viewBottom = viewPos.y + scrollPane.getHeight();
        int componentRight = c.getPositionX() + c.getWidth() + 20;
        int componentBottom = c.getPositionY() + c.getHeight() + 20;

        int newX = viewPos.x;
        int newY = viewPos.y;

        // Move viewport horizontally if needed
        if (viewRight < componentRight) {
            newX += viewport.getX() + MOVE_AMOUNT - 3;
        }

        // Move viewport vertically if needed
        if (viewBottom < componentBottom) {
            newY += viewport.getY() + MOVE_AMOUNT - 3;
        }

        // Update viewport position if changed
        if (newX != viewPos.x || newY != viewPos.y) {
            viewport.setViewPosition(new Point(newX, newY));
        }
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public static Relation getRelation() {
        return relation;
    }

    public static void setRelation(Relation relation) {
        MainBoard.relation = relation;
    }
}
