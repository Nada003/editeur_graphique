package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextUtils {

    public static int getTextWidth(String text) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        FontMetrics metrics = g2d.getFontMetrics();
        int width = metrics.stringWidth(text);
        g2d.dispose();
        return width;
    }
}
