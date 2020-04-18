package de.blutmondgilde.blutmondrpg.enums;

public enum ClassLevel {

    L1(1, 100),
    L2(2, L1.getExp() * L1.getScaling()),
    L3(3, L2.getExp() * L1.getScaling()),
    L4(4, L3.getExp() * L1.getScaling()),
    L5(5, L4.getExp() * L1.getScaling()),
    L6(6, L5.getExp() * L1.getScaling()),
    L7(7, L6.getExp() * L1.getScaling()),
    L8(8, L7.getExp() * L1.getScaling()),
    L9(9, L8.getExp() * L1.getScaling()),
    L10(10, L9.getExp() * L1.getScaling()),
    L11(11, L10.getExp() * L1.getScaling()),
    L12(12, L11.getExp() * L1.getScaling()),
    L13(13, L12.getExp() * L1.getScaling()),
    L14(14, L13.getExp() * L1.getScaling()),
    L15(15, L14.getExp() * L1.getScaling()),
    L16(16, L15.getExp() * L1.getScaling()),
    L17(17, L16.getExp() * L1.getScaling()),
    L18(18, L17.getExp() * L1.getScaling()),
    L19(19, L18.getExp() * L1.getScaling()),
    L20(20, L19.getExp() * L1.getScaling()); //6000 Exp

    private int id;
    private double exp;


    ClassLevel(int id, double exp) {
        this.id = id;
        this.exp = exp;
    }

    private double getScaling() {
        return 3;
    }

    public int getId() {
        return id;
    }

    public double getExp() {
        return exp;
    }

    public static ClassLevel getMaxLevel() {
        return L20;
    }

    public static ClassLevel getLevelFromId(int id) {
        switch (id) {
            case 1:
                return L1;
            case 2:
                return L2;
            case 3:
                return L3;
            case 4:
                return L4;
            case 5:
                return L5;
            case 6:
                return L6;
            case 7:
                return L7;
            case 8:
                return L8;
            case 9:
                return L9;
            case 10:
                return L10;
            case 11:
                return L11;
            case 12:
                return L12;
            case 13:
                return L13;
            case 14:
                return L14;
            case 15:
                return L15;
            case 16:
                return L16;
            case 17:
                return L17;
            case 18:
                return L18;
            case 19:
                return L19;
            case 20:
                return L20;
            default:
                throw new IllegalArgumentException("There is no Level with the ID: " + id);
        }
    }
}
