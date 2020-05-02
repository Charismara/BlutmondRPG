package de.blutmondgilde.blutmondrpg.interfaces;

import de.blutmondgilde.blutmondrpg.enums.ItemRarities;
import de.blutmondgilde.blutmondrpg.enums.WeaponAttributeTypes;
import de.blutmondgilde.blutmondrpg.enums.WeaponTypes;

import java.util.Map;

public interface IClassWeapon {
    ItemRarities getRarity();

    WeaponTypes getWeaponType();

    Map<WeaponAttributeTypes, Float> getAttributeTypes();

    float getDefaultStrength();

    int getItemLevel();
}
