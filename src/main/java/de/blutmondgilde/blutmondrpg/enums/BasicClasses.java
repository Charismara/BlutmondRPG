package de.blutmondgilde.blutmondrpg.enums;

import java.util.List;

public enum BasicClasses {
    WARRIOR(0, 20, 8, ArmorTypes.HEAVY, ClassWeapons.WARRIOR, 0, 1.0F, 0, 1.05F, 0, 0, 0, 0),
    HUNTER(1, 15, 5, ArmorTypes.MIDDLE, ClassWeapons.HUNTER, 100, 0.75F, 0.55F, 0.7F, 5.0F, 0.6F, 5.0F, 1.0F),
    CASTER(2, 10, 0, ArmorTypes.LIGHT, ClassWeapons.CASTER, 150, 0.95F, 1.0F, 0.0F, 8, 1.45F, 0, 0),
    NONE(-1, 2, 0, ArmorTypes.NONE, ClassWeapons.NONE, 0, 0, 0, 0, 0, 0, 0, 0);

    private final int id;
    private final float baseHP, baseMeleeDamage, baseMana, hpScaling, manaScaling, meleeScaling, baseMagicalDamage, magicDamageScaling, baseBowDamage, bowDamageScaling;
    private final ArmorTypes armorType;
    private final List<WeaponTypes> weaponTypes;

    BasicClasses(int id, float baseHP, float baseMeleeDamage, ArmorTypes armorType, ClassWeapons useableWeapons, float baseMana, float hpScaling, float manaScaling, float meleeScaling, float baseMagicalDamage, float magicDamageScaling, float baseBowDamage, float bowDamageScaling) {
        this.id = id;
        this.baseHP = baseHP;
        this.baseMeleeDamage = baseMeleeDamage;
        this.armorType = armorType;
        this.weaponTypes = useableWeapons.getWeapons();
        this.baseMana = baseMana;
        this.hpScaling = hpScaling;
        this.manaScaling = manaScaling;
        this.meleeScaling = meleeScaling;
        this.baseMagicalDamage = baseMagicalDamage;
        this.magicDamageScaling = magicDamageScaling;
        this.baseBowDamage = baseBowDamage;
        this.bowDamageScaling = bowDamageScaling;
    }

    public int getId() {
        return id;
    }

    public float getBaseHP() {
        return baseHP;
    }

    public ArmorTypes getArmorType() {
        return armorType;
    }

    public float getBaseMeleeDamage() {
        return baseMeleeDamage;
    }

    public List<WeaponTypes> getWeaponTypes() {
        return weaponTypes;
    }

    public static BasicClasses getById(int id) {
        switch (id) {
            case 0:
                return WARRIOR;
            case 1:
                return HUNTER;
            case 2:
                return CASTER;
            case -1:
                return NONE;
            default:
                throw new NullPointerException("There is no Class with ID: " + id);
        }
    }

    public boolean isUsable(ArmorTypes armorType) {
        return this.armorType == armorType;
    }

    public boolean isUsable(WeaponTypes weaponType) {
        return this.weaponTypes.contains(weaponType);
    }

    public float getBaseBowDamage() {
        return baseBowDamage;
    }

    public float getBaseMagicalDamage() {
        return baseMagicalDamage;
    }

    public float getBaseMana() {
        return baseMana;
    }

    public float getBowDamageScaling() {
        return bowDamageScaling;
    }

    public float getHpScaling() {
        return hpScaling;
    }

    public float getMagicDamageScaling() {
        return magicDamageScaling;
    }

    public float getManaScaling() {
        return manaScaling;
    }

    public float getMeleeScaling() {
        return meleeScaling;
    }
}
