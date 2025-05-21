package ui.custom_graphics.uml_components.use_case_diagrame.use_case;

import ui.custom_graphics.uml_components.UMLComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static utils.TextUtils.getTextWidth;

public class UseCaseRender extends UMLComponent {

    private UseCaseModel model;
    private JTextField textField;

    public UseCaseRender(UseCaseModel model) {
        this.model = model;
        this.setOpaque(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textField == null) {
                    textField = new JTextField(model.getScenario());
                    textField.setFont(new Font("Arial", Font.BOLD, 14));
                    textField.setHorizontalAlignment(SwingConstants.CENTER);
                    textField.setBorder(null);
                    textField.setOpaque(false);
                    textField.setForeground(Color.BLACK);

                    int inset = 10;
                    textField.setBounds(inset, getHeight()/2 - 12, getWidth() - 2*inset, 24);

                    textField.addActionListener(evt -> {
                        model.setScenario(textField.getText());
                        textField.setVisible(false);
                        repaint();
                    });

                    setLayout(null);
                    add(textField);
                }
                textField.setText(model.getScenario());
                textField.setVisible(true);
                textField.requestFocusInWindow();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 10;
        int y = 10;
        int width = getWidth() - 20;
        int height = getHeight() - 20;

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillOval(x, y, width, height);

        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawOval(x, y, width, height);

        if (textField == null || !textField.isVisible()) {
            String text = model.getScenario();
            Font font = new Font("Arial", Font.BOLD, 14);
            graphics2D.setFont(font);
            int textWidth = getTextWidth(text);
            FontMetrics fm = graphics2D.getFontMetrics();
            int textHeight = fm.getAscent();

            int textX = (getWidth() - textWidth) / 2;
            int textY = getHeight() / 2 + textHeight / 2 - 4;

            graphics2D.drawString(text, textX, textY);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Géré par le MouseAdapter dans le constructeur
    }
}
