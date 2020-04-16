package de.blutmondgilde.blutmondrpg.enums;

import java.util.List;

public enum BasicClasses {
    WARRIOR(0, 10, 2, ArmorTypes.HEAVY, ClassWeapons.WARRIOR, 0, 2, 0.5F, 1, 0, 0, 0, 0),
    HUNTER(1, 8, 1, ArmorTypes.MIDDLE, ClassWeapons.HUNTER, 0, 1.5F, 1, 0.75F, 0.5F, 0.5F, 2, 1),
    CASTER(2, 5, 0, ArmorTypes.LIGHT, ClassWeapons.CASTER, 5, 1, 2, 0.5F, 2, 2, 0, 0),
    NONE(-1, 2, 0, ArmorTypes.NONE, ClassWeapons.NONE,0,0,0,0,0,0,0,0);

    private int id;
    private float baseHP, baseMeleeDamage, baseMana, hpOnLevelUp, manaOnLevelUp, meleeDamageOnLevelUp, baseMagicalDamage, magicalDamageOnLevelUp, baseBowDamage, bowDamageOnLevelUp;
    private ArmorTypes armorType;
    private List<WeaponTypes> weaponTypes;

    BasicClasses(int id, float baseHP, float baseMeleeDamage, ArmorTypes armorType, ClassWeapons useableWeapons, float baseMana, float hpOnLevelUp, float manaOnLevelUp, float meleeDamageOnLevelUp, float baseMagicalDamage, float magicalDamageOnLevelUp, float baseBowDamage, float bowDamageOnLevelUp) {
        this.id = id;
        this.baseHP = baseHP;
        this.baseMeleeDamage = baseMeleeDamage;
        this.armorType = armorType;
        this.weaponTypes = useableWeapons.getWeapons();
        this.baseMana = baseMana;
        this.hpOnLevelUp = hpOnLevelUp;
        this.manaOnLevelUp = manaOnLevelUp;
        this.meleeDamageOnLevelUp = meleeDamageOnLevelUp;
        this.baseMagicalDamage = baseMagicalDamage;
        this.magicalDamageOnLevelUp = magicalDamageOnLevelUp;
        this.baseBowDamage = baseBowDamage;
        this.bowDamageOnLevelUp = bowDamageOnLevelUp;
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

    public float getBowDamageOnLevelUp() {
        return bowDamageOnLevelUp;
    }

    public float getHpOnLevelUp() {
        return hpOnLevelUp;
    }

    public float getMagicalDamageOnLevelUp() {
        return magicalDamageOnLevelUp;
    }

    public float getManaOnLevelUp() {
        return manaOnLevelUp;
    }

    public float getMeleeDamageOnLevelUp() {
        return meleeDamageOnLevelUp;
    }
}
