package ui.custom_graphics.uml_components.text_and_comments;

import ui.custom_graphics.uml_components.UMLComponent;
import utils.CommentClickedHandel;
import utils.SerializableStroke;
import utils.TextUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CommentRender extends UMLComponent {
    private Color textColor = Color.BLACK;
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    private String fontFamily = "Arial";
    private int currentFontSize = 14;
    private final String value;
    private boolean isSelected = false;
    private CommentClickedHandel mouseAdapter ;


    public CommentRender(String value){
        this.value = value;
        super.setHeight(30);
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

        g2.drawString(value,10, 20);
        if (isUnderline) {
            g2.drawLine(13, 22, TextUtils.getTextWidth(g,value,font)+6,  22);
        }

        super.setWidth(TextUtils.getTextWidth(g,value,font) + 20);

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

    public void setClickListener(CommentClickedHandel mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            float[] dashPattern = {10, 5}; // 10px dash, 5px gap
            Border dashedBorder = BorderFactory.createStrokeBorder(
                    new SerializableStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, dashPattern, 0),
                    Color.BLACK
            );
            this.setBorder(dashedBorder);
        }else {
            this.setBorder(null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        mouseAdapter.mouseClicked(e);
    }

    public CommentClickedHandel getMouseAdapter() {
        return mouseAdapter;
    }
}
