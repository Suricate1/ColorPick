import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMagnifierPanel extends JPanel {
    private int centerX = -1, centerY = -1;
    private BufferedImage source = null;
    private int zoom = 12;

    public void setCenter(int ox, int oy, BufferedImage src) {
        this.centerX = ox;
        this.centerY = oy;
        this.source = src;
    }
    public void setZoom(int z) { this.zoom = Math.max(2, z); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0,0,w,h);

        if (source == null || centerX < 0 || centerY < 0) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("Load an image and hover to magnify", 12, 20);
            g2.dispose();
            return;
        }

        // choose sample grid size (odd)
        int samplePixels = Math.max(9, Math.min(33, Math.min(w,h) / Math.max(6, zoom/2)));
        if (samplePixels % 2 == 0) samplePixels++;
        int half = samplePixels/2;

        int sx = centerX - half;
        int sy = centerY - half;
        // clamp
        if (sx < 0) sx = 0;
        if (sy < 0) sy = 0;
        if (sx + samplePixels > source.getWidth()) sx = Math.max(0, source.getWidth() - samplePixels);
        if (sy + samplePixels > source.getHeight()) sy = Math.max(0, source.getHeight() - samplePixels);

        // draw blocks
        int pixelSize = zoom;
        int drawSize = samplePixels * pixelSize;
        int drawX = (w - drawSize)/2;
        int drawY = (h - drawSize)/2;

        for (int yy = 0; yy < samplePixels; yy++) {
            for (int xx = 0; xx < samplePixels; xx++) {
                int rgb = source.getRGB(sx + xx, sy + yy);
                g2.setColor(new Color(rgb, true));
                g2.fillRect(drawX + xx*pixelSize, drawY + yy*pixelSize, pixelSize, pixelSize);
            }
        }

        // grid lines
        g2.setColor(new Color(0,0,0,100));
        for (int i = 0; i <= samplePixels; i++) {
            int gx = drawX + i*pixelSize;
            g2.drawLine(gx, drawY, gx, drawY + drawSize);
            int gy = drawY + i*pixelSize;
            g2.drawLine(drawX, gy, drawX + drawSize, gy);
        }

        // center crosshair
        int cx = drawX + (samplePixels/2)*pixelSize + pixelSize/2;
        int cy = drawY + (samplePixels/2)*pixelSize + pixelSize/2;
        g2.setColor(Color.WHITE);
        g2.drawLine(cx-12, cy, cx+12, cy);
        g2.drawLine(cx, cy-12, cx, cy+12);
        g2.setColor(Color.BLACK);
        g2.drawOval(cx - (pixelSize/2), cy - (pixelSize/2), pixelSize, pixelSize);

        // swatch + hex at bottom-left
        int sw = 36;
        Color centerColor = new Color(source.getRGB(centerX, centerY), true);
        int swX = 12, swY = h - sw - 12;
        g2.setColor(centerColor);
        g2.fillRect(swX, swY, sw, sw);
        g2.setColor(Color.WHITE);
        g2.drawRect(swX, swY, sw, sw);
        String hex = String.format("#%02X%02X%02X", centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue());
        g2.drawString(hex, swX + sw + 8, swY + sw/2 + 6);

        g2.dispose();
    }
}