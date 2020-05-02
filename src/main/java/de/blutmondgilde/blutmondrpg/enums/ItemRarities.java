package de.blutmondgilde.blutmondrpg.enums;

import java.awt.*;

public enum ItemRarities {
    Normal(0, 1, 2.0F, 0, 0.7F, new Color(255, 255, 255)),
    Good(1, 1, 2.0F, 1, 0.7F, new Color(25, 146, 0)),
    Masterwork(2, 1, 2.0F, 2, 0.7F, new Color(0, 114, 226, 255)),
    Rare(3, 1, 2.0F, 3, 0.7F, new Color(231, 198, 0)),
    Exotic(4, 2, 2.0F, 2, 0.7F, new Color(208, 151, 0)),
    Ascended(5, 2, 2.0F, 3, 0.7F, new Color(216, 0, 224)),
    Legendary(6, 3, 2.0F, 2, 0.7F, new Color(119, 0, 191));

    private final int id, mainAttributeNumber, secondAttritubeNumber;
    private final float mainAttributeFactor, secondAttributeFaktor;
    private final Color color;

    ItemRarities(int id, int mainAttributeNumber, float mainAttributeFactor, int secondAttritubeNumber, float secondAttributeFaktor, Color color) {
        this.id = id;
        this.mainAttributeNumber = mainAttributeNumber;
        this.mainAttributeFactor = mainAttributeFactor;
        this.secondAttritubeNumber = secondAttritubeNumber;
        this.secondAttributeFaktor = secondAttributeFaktor;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public float getMainAttributeFactor() {
        return mainAttributeFactor;
    }

    public int getMainAttributeNumber() {
        return mainAttributeNumber;
    }

    public float getSecondAttributeFaktor() {
        return secondAttributeFaktor;
    }

    public int getSecondAttritubeNumber() {
        return secondAttritubeNumber;
    }

    public static ItemRarities getById(int id) {
        switch (id) {
            case 1:
                return Good;
            case 2:
                return Masterwork;
            case 3:
                return Rare;
            case 4:
                return Exotic;
            case 5:
                return Ascended;
            case 6:
                return Legendary;
            default:
                return Normal;
        }
    }
}
