package de.blutmondgilde.blutmondrpg.enums;

public enum ArmorTypes {
    NONE(0),
    LIGHT(1),
    MIDDLE(2),
    HEAVY(3);

    private int id;
    ArmorTypes(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
