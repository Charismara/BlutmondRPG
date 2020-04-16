package de.blutmondgilde.blutmondrpg.enums;

import java.util.Arrays;
import java.util.List;

public enum ClassWeapons {
    NONE(0,WeaponTypes.NONE),
    WARRIOR(1,WeaponTypes.SWORD, WeaponTypes.SHIELD,WeaponTypes.GREATSWORD),
    HUNTER(2,WeaponTypes.SWORD,WeaponTypes.BOW,WeaponTypes.SHORT_BOW,WeaponTypes.KNIFE),
    CASTER(3,WeaponTypes.STAFF);

    private int id;
    private List<WeaponTypes> weapons;

    ClassWeapons(int id, WeaponTypes... weaponType){
        this.id = id;
        this.weapons = Arrays.asList(weaponType);
    }

    public int getId() {
        return id;
    }

    public List<WeaponTypes> getWeapons() {
        return weapons;
    }
}
