package ui.custom_graphics.uml_components.text_and_comments;

import ui.custom_graphics.uml_components.UMLComponent;
import utils.TextUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;

public class CommentRender extends UMLComponent {
    private Color textColor = Color.BLACK;
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    private String fontFamily = "Arial";
    private int currentFontSize = 14;
    private String value;


    public CommentRender(String value){
        this.value = value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int fontStyle = Font.PLAIN;
        if (isBold) fontStyle |= Font.BOLD;
        if (isItalic) fontStyle |= Font.ITALIC;

        Font font = new Font(fontFamily, fontStyle, currentFontSize);
        g2.setFont(font);
        g2.setColor(textColor);

        g2.drawString(value,2, 20);
        if (isUnderline) {
            g2.drawLine(2, 22, TextUtils.getTextWidth(g,value,font),  22);
        }

        super.setWidth((int) TextUtils.getTextWidth(g,value,font)+4);

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

    public void setClickListener(MouseAdapter mouseAdapter) {
        this.addMouseListener(mouseAdapter);

    }
}
