package de.blutmondgilde.blutmondrpg.enums;

public enum WeaponTypes {
    NONE(0),
    SWORD(1),
    SHIELD(2),
    GREATSWORD(3),
    BOW(4),
    SHORT_BOW(5),
    KNIFE(6),
    STAFF(7);

    private final int id;

    WeaponTypes(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
