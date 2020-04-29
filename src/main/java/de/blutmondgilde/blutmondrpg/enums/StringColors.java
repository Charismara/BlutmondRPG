package de.blutmondgilde.blutmondrpg.enums;

public enum StringColors {
    WHITE(16777215),
    GRAY(9868950),
    GREEN(51200),
    RED(13173767);

    private int colorCode;

    StringColors(int color) {
        this.colorCode = color;
    }

    public int getColorCode() {
        return colorCode;
    }
}
