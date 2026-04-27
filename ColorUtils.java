import java.awt.Color;

public class ColorUtils {
    public static String colorToHex(Color c) {
        if (c == null) return "#000000";
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    public static Color hexToColor(String hex) {
        if (hex == null) return Color.BLACK;
        String s = hex.trim();
        return Color.decode(s);
    }

    public static String rgbToHslString(Color c) {
        float[] hsl = rgbToHsl(c.getRed(), c.getGreen(), c.getBlue());
        return String.format("hsl(%.0f, %.0f%%, %.0f%%)", hsl[0], hsl[1]*100, hsl[2]*100);
    }

    public static float[] rgbToHsl(int r, int g, int b) {
        float rf = r/255f, gf = g/255f, bf = b/255f;
        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float h, s, l = (max + min) / 2f;
        if (max == min) {
            h = s = 0f;
        } else {
            float d = max - min;
            s = l > 0.5f ? d / (2f - max - min) : d / (max + min);
            if (max == rf) h = (gf - bf) / d + (gf < bf ? 6f : 0f);
            else if (max == gf) h = (bf - rf) / d + 2f;
            else h = (rf - gf) / d + 4f;
            h *= 60f;
        }
        return new float[]{h, s, l};
    }
}