package de.blutmondgilde.blutmondrpg.enums;

public enum SharingMethod {
    Fair(0),
    Killer(1),
    Groupmaster(2);

    private int id;

    SharingMethod(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SharingMethod getById(int id) {
        switch (id) {
            case 0:
                return Fair;
            case 1:
                return Killer;
            case 2:
                return Groupmaster;
            default:
                throw new IllegalStateException("Tried to get a Sharing Method which don't exist");
        }
    }
}
