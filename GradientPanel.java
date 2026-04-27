import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color startColor = Color.WHITE;
    private Color endColor = Color.BLACK;

    public void setStartColor(Color c) { if (c != null) this.startColor = c; repaint(); }
    public void setEndColor(Color c) { if (c != null) this.endColor = c; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startColor != null && endColor != null) {
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth();
            GradientPaint gp = new GradientPaint(0, 0, startColor, w, 0, endColor);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}