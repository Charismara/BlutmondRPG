package de.blutmondgilde.blutmondrpg.enums;

public enum ClassLevel {

    L1(1, 2000),
    L2(2, 4110),
    L3(3, 6489),
    L4(4, 9189),
    L5(5, 12162),
    L6(6, 15505),
    L7(7, 19224),
    L8(8, 23353),
    L9(9, 27920),
    L10(10, 32964),
    L11(11, 38517),
    L12(12, 44628),
    L13(13, 51333),
    L14(14, 58687),
    L15(15, 66729),
    L16(16, 75519),
    L17(17, 84789),
    L18(18, 94559),
    L19(19, 104859),
    L20(20, 115759),
    L21(21, 127159),
    L22(22, 139259),
    L23(23, 151959),
    L24(24, 165359),
    L25(25, 179459),
    L26(26, 194359),
    L27(27, 210059),
    L28(28, 226559),
    L29(29, 243959),
    L30(30, 262259),
    L31(31, 281559),
    L32(32, 301959),
    L33(33, 323459),
    L34(34, 346059),
    L35(35, 369959),
    L36(36, 395059),
    L37(37, 421559),
    L38(38, 449459),
    L39(39, 478859),
    L40(40, 509859),
    L41(41, 542559),
    L42(42, 577059),
    L43(43, 613359),
    L44(44, 651659),
    L45(45, 691959),
    L46(46, 734459),
    L47(47, 779259),
    L48(48, 826459),
    L49(49, 876259),
    L50(50, 928759),
    L51(51, 984059),
    L52(52, 1042359),
    L53(53, 1103759),
    L54(54, 1168459),
    L55(55, 1236659),
    L56(56, 1308559),
    L57(57, 1384359),
    L58(58, 1464159),
    L59(59, 1548259),
    L60(60, 1636959),
    L61(61, 1730459),
    L62(62, 1828959),
    L63(63, 1933359),
    L64(64, 2042359),
    L65(65, 2157359),
    L66(66, 2279359),
    L67(67, 2407359),
    L68(68, 2542359),
    L69(69, 2684359),
    L70(70, 2834359),
    L71(71, 2992359),
    L72(72, 3159359),
    L73(73, 3335359),
    L74(74, 3520359),
    L75(75, 3715359),
    L76(76, 3920359),
    L77(77, 417359),
    L78(78, 4365359),
    L79(79, 4606359),
    L80(80, 4860359);

    private final int id;
    private final double exp;


    ClassLevel(final int id, final double exp) {
        this.id = id;
        this.exp = exp;
    }

    public int getId() {
        return id;
    }

    public double getExp() {
        return exp;
    }

    public static ClassLevel getMaxLevel() {
        return L80;
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
