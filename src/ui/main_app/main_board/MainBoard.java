package ui.main_app.main_board;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.text_and_comments.CommentRender;
import utils.custom_list.ListListener;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

public class MainBoard extends JPanel implements ListListener {
    public final WatchedList<UMLComponent> components;
    private boolean showGrid = false;
    private CommentRender selectedComment ;

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

                if (draggedPanel instanceof CommentRender obj) targetPanel.setSelectedComment(obj);
                draggedPanel.setLocation(dropPoint);
                targetPanel.components.removeElement(draggedPanel);
                targetPanel.components.addElement(draggedPanel);
                targetPanel.repaint(); // Ensure repaint after drop

                System.out.println("new position : " + dropPoint);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public MainBoard(WatchedList<UMLComponent> components) {
        this.components = components;
        setLayout(null);
        setBackground(Color.WHITE);
        components.addListener(this);
        new PanelDropListener(this);
    }

    public void paintAll() {
        for (var m : components.getList()) {
            if (!checkChild(m)) {
                this.add(m);
            }
        }
        repaint();
    }

    private void removeAllPainted() {
        for (Component c : getComponents()) {
            if (c instanceof UMLComponent) {
                remove(c);
            }
        }
        repaint();
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
    }


    @Override
    public void notifyListChanged() {
        removeAllPainted();
        paintAll();
        repaint();
    }
}
