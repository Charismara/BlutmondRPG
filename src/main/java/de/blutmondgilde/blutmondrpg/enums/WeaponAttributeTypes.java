package de.blutmondgilde.blutmondrpg.enums;

public enum WeaponAttributeTypes {
    NONE(-1),
    ARMOR(0),
    HEALTH(1),
    CRIT_CHANCE(2),
    CRIT_DAMAGE(3),
    DAMAGE(4),
    LUCK(5);

    private final int id;

    WeaponAttributeTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static WeaponAttributeTypes getById(int id) {
        switch (id) {
            case 0:
                return ARMOR;
            case 1:
                return HEALTH;
            case 2:
                return CRIT_CHANCE;
            case 3:
                return CRIT_DAMAGE;
            case 4:
                return DAMAGE;
            case 5:
                return LUCK;
            default:
                return NONE;
        }
    }
}
