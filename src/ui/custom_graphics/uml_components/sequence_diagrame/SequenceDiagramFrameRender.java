package ui.custom_graphics.uml_components.sequence_diagrame;

import ui.custom_graphics.uml_components.ResizableUMComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SequenceDiagramFrameRender extends ResizableUMComponent implements MouseListener, KeyListener {

    private final SequenceDiagramFrameModel model;
    private JTextField nameInput;

    public SequenceDiagramFrameRender(SequenceDiagramFrameModel model) {
        super();
        this.model = model;

        setLayout(null);
        setBounds(model.getX(), model.getY(), Math.max(model.getWidth(), 600), Math.max(model.getHeight(), 400));
        setOpaque(false);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        String text = model.getName();  // texte totalement libre modifiable
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int tabHeight = fm.getHeight();
        int tabWidth = textWidth + 20; // padding horizontal

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);

        Polygon header = new Polygon();
        header.addPoint(0, 0);
        header.addPoint(tabWidth + 10, 0);
        header.addPoint(tabWidth, tabHeight);
        header.addPoint(0, tabHeight);

        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(header);

        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(header);

        g2d.drawString(text, 10, fm.getAscent());
    }

    private void showNameInput() {
        if (nameInput != null) remove(nameInput);

        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        FontMetrics fm = getFontMetrics(font);
        String text = model.getName();
        int textWidth = fm.stringWidth(text);

        int width = textWidth + 20;
        if (width < 60) width = 60; // taille minimum

        nameInput = new JTextField(text);
        nameInput.setFont(font);
        nameInput.setBounds(10, 3, width, 20); // champ texte dans header, 10px marge gauche
        nameInput.addKeyListener(this);

        add(nameInput);
        nameInput.requestFocusInWindow();
        repaint();
    }

    private void applyNameChange() {
        if (nameInput != null) {
            model.setName(nameInput.getText());
            remove(nameInput);
            nameInput = null;
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        FontMetrics fm = getFontMetrics(font);
        String text = model.getName();
        int textWidth = fm.stringWidth(text);
        int tabHeight = fm.getHeight();
        int tabWidth = textWidth + 20;

        Rectangle headerArea = new Rectangle(0, 0, tabWidth + 10, tabHeight);
        if (headerArea.contains(e.getPoint())) {
            showNameInput();
        }
    }

    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) applyNameChange();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    public boolean contains(int x, int y) {
        return x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight();
    }

    public SequenceDiagramFrameModel getModel() {
        return model;
    }
}
