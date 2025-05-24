package ui.custom_graphics.uml_components.shapes;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CircleRender extends ResizableUMComponent  {

    private final CircleModel model;
    private JTextField textField;

    public CircleRender(CircleModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        setPreferredSize(new Dimension(100, 100));
        setOpaque(false);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Remplissage blanc dans le cercle
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, size - 1, size - 1);

        // Bordure noire
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x, y, size - 1, size - 1);

        if (textField == null || !textField.isVisible()) {
            String text = model.getText();
            Font font = new Font("Arial", Font.BOLD, 14);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            int textX = x + (size - textWidth) / 2;
            int textY = y + (size + textHeight) / 2 - 4;
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, textX, textY);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (textField == null) {
            textField = new JTextField(model.getText());
            textField.setFont(new Font("Arial", Font.BOLD, 14));
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setBorder(null);
            textField.setOpaque(false);
            textField.setForeground(Color.BLACK);
            textField.setBounds(10, getHeight() / 2 - 10, getWidth() - 20, 20);
            textField.addActionListener(evt -> {
                model.setText(textField.getText());
                textField.setVisible(false);
                repaint();
            });
            setLayout(null);
            add(textField);
        }

        textField.setText(model.getText());
        textField.setVisible(true);
        textField.requestFocusInWindow();
    }

    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
