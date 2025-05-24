package ui.custom_graphics.uml_components.shapes;

import ui.custom_graphics.uml_components.ResizableUMComponent;
import ui.custom_graphics.uml_components.UMLComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RectangleRender extends ResizableUMComponent implements MouseListener {

    private final RectangleModel model;
    private JTextField textField;

    public RectangleRender(RectangleModel model) {
        super.setId(UMLComponent.getCount());
        this.model = model;
        this.setPreferredSize(new Dimension(120, 70));
        this.setOpaque(false);
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (textField == null) {
            textField = new JTextField(model.getLabel());
            textField.setFont(new Font("Arial", Font.BOLD, 14));
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setBorder(null);
            textField.setOpaque(false);
            textField.setForeground(Color.BLACK);
            textField.addActionListener(evt -> {
                model.setLabel(textField.getText());
                textField.setVisible(false);
                repaint();
            });
            setLayout(null);
            add(textField);
        }
        textField.setText(model.getLabel());
        textField.setVisible(true);
        textField.requestFocusInWindow();
    }


    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(0, 0, width - 1, height - 1);

        if (textField == null || !textField.isVisible()) {
            String text = model.getLabel();
            Font font = new Font("Arial", Font.BOLD, 14);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            int textX = (width - textWidth) / 2;
            int textY = (height + textHeight) / 2 - 4;
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, textX, textY);
        }
    }
}
