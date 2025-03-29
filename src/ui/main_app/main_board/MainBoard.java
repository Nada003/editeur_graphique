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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainBoard extends JPanel implements ListListener {
    private final WatchedList<UMLComponent> components;
    private boolean showGrid = false;
    private final List<TextEntry> texts = new ArrayList<>();
    private Color textColor = Color.BLACK;
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    private String fontFamily = "Arial";
    private boolean textModeActive = false;
    private int currentFontSize = 14;

    private static class TextEntry {
        String text;
        int x, y;

        public TextEntry(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }

    private class PanelDropListener extends DropTargetAdapter {
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

                draggedPanel.setLocation(dropPoint);
                targetPanel.components.removeElement(draggedPanel);
                targetPanel.components.addElement(draggedPanel);
                targetPanel.repaint(); // Ensure repaint after drop
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public MainBoard(WatchedList<UMLComponent> components) {
        this.components = components;
        setBackground(Color.WHITE);
        setMinimumSize(new Dimension(750, 600));
        setPreferredSize(new Dimension(1200, 800)); // Ajuste selon la taille de ta fenêtre
        setSize(new Dimension(1200, 800));

        setLayout(null);
        components.addListener(this);
        new PanelDropListener(this);

        // MouseListener for text handling
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textModeActive) {
                    addTextField(e.getX(), e.getY());
                }
            }
        });
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

    public void addText(String text, int x, int y) {
        texts.add(new TextEntry(text, x, y));
        repaint();
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        repaint();
    }

    public void toggleBold() {
        isBold = !isBold;
        repaint();
    }

    public void toggleItalic() {
        isItalic = !isItalic;
        repaint();
    }

    public void toggleUnderline() {
        isUnderline = !isUnderline;
        repaint();
    }

    public void setFontFamily(String font) {
        this.fontFamily = font;
        repaint();
    }

    public void toggleGrid() {
        showGrid = !showGrid;
        repaint();
    }

    public String getCurrentFont() {
        return fontFamily;
    }

    public void setCurrentFont(String font) {
        this.fontFamily = font;
        repaint();
    }

    public Color getCurrentTextColor() {
        return textColor;
    }

    public void setFontSize(int size) {
        this.currentFontSize = size;
        repaint(); // Ensure repaint when changing font size
    }

    public int getFontSize() {
        return currentFontSize;
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

        int fontStyle = Font.PLAIN;
        if (isBold) fontStyle |= Font.BOLD;
        if (isItalic) fontStyle |= Font.ITALIC;

        Font font = new Font(fontFamily, fontStyle, currentFontSize);
        g2.setFont(font);
        g2.setColor(textColor);

        for (TextEntry entry : texts) {
            g2.drawString(entry.text, entry.x, entry.y);
            if (isUnderline) {
                int textWidth = g2.getFontMetrics().stringWidth(entry.text);
                g2.drawLine(entry.x, entry.y + 2, entry.x + textWidth, entry.y + 2);
            }
        }
    }

    public void activateTextMode() {
        textModeActive = true;
    }

    private void addTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 100, 30);
        add(textField);
        textField.requestFocus();

        textField.addActionListener(e -> {
            String text = textField.getText();
            addText(text, x, y);
            remove(textField);
            repaint();
        });
    }

    @Override
    public void notifyListChanged() {
        removeAllPainted();
        paintAll();
        repaint();
    }
}
