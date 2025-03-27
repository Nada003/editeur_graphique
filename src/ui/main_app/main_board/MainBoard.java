package ui.main_app.main_board;

import ui.custom_graphics.uml_components.UMLComponent;
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
    private final WatchedList<UMLComponent> components ;
    private boolean showGrid = false;

    private static class PanelDropListener extends DropTargetAdapter {
        private final DropTarget dropTarget;
        private final MainBoard targetPanel;

        public PanelDropListener(MainBoard targetPanel) {
            this.targetPanel = targetPanel;
            dropTarget = new DropTarget(targetPanel, DnDConstants.ACTION_MOVE, this, true, null);
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                UMLComponent draggedPanel = (UMLComponent) dtde.getTransferable().getTransferData(new DataFlavor(UMLComponent.class,"UMLComponent"));

                // Move panel to new location
                Point dropPoint = dtde.getLocation();
                dropPoint.x -= draggedPanel.getWidth()/2;
                dropPoint.y -= draggedPanel.getHeight()/2;

                draggedPanel.setLocation(dropPoint);

                targetPanel.components.removeElement(draggedPanel);
                targetPanel.components.addElement(draggedPanel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public MainBoard(WatchedList<UMLComponent> components){
        this.components = components;
        this.setBackground(Color.lightGray);
        this.setMinimumSize(new Dimension(750,600));
        this.setLayout(null);
        components.addListener(this);

        new PanelDropListener(this);
    }

    public void paintAll(){
        for (var m : components.getList())
            if (!checkChild(m))
                this.add(m);

        this.repaint();
    }

    private void removeAllPainted(){
        this.removeAll();
        this.repaint();
    }
    private boolean checkChild(Component child){
        Component[] children = this.getComponents();
        for( var c : children)
            if (c.equals(child))
                return true;

        return false;
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showGrid) {
            dessinerGrille(g);
        }
    }

    private void dessinerGrille(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < getWidth(); i += 20) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int j = 0; j < getHeight(); j += 20) {
            g.drawLine(0, j, getWidth(), j);
        }
    }


    public void toggle(){
        showGrid = !showGrid;
        this.revalidate();
        this.repaint();
    }

    @Override
    public void notifyListChanged() {
        removeAllPainted();
        paintAll();
    }
}
