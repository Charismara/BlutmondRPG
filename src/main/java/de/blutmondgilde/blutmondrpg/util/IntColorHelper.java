package de.blutmondgilde.blutmondrpg.util;

import java.awt.*;

public class IntColorHelper {

    public static int ColorToInt(final Color color) {
        return RGBAToInt(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int RGBAToInt(int red, int green, int blue, int alpha) {
        red &= 255;
        green &= 255;
        blue &= 255;
        alpha &= 255;

        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int RGBToInt(final int red, final int green, final int blue) {
        return RGBAToInt(red, green, blue, 255);
    }
}
