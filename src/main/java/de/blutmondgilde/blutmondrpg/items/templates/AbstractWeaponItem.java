package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.enums.ItemRarities;
import de.blutmondgilde.blutmondrpg.enums.WeaponAttributeTypes;
import de.blutmondgilde.blutmondrpg.enums.WeaponTypes;
import de.blutmondgilde.blutmondrpg.interfaces.IClassWeapon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWeaponItem extends AbstractBlutmondRPGItem implements IClassWeapon {
    private ItemRarities rarity;
    private WeaponTypes weaponType;
    private final Map<WeaponAttributeTypes, Float> attributeTypes;
    private float defaultStrength;
    private int itemLevel;

    public AbstractWeaponItem(String name) {
        super((new Item.Properties().rarity(Rarity.COMMON).group(ItemGroup.COMBAT)), name);
        attributeTypes = new HashMap<>();
    }

    public ItemRarities getRarity() {
        return this.rarity;
    }

    public WeaponTypes getWeaponType() {
        return this.weaponType;
    }

    public Map<WeaponAttributeTypes, Float> getAttributeTypes() {
        return attributeTypes;
    }

    public float getDefaultStrength() {
        return this.defaultStrength;
    }

    public int getItemLevel() {
        return this.itemLevel;
    }

    public void setRarity(ItemRarities rarity) {
        this.rarity = rarity;
    }

    public void setWeaponType(WeaponTypes weaponType) {
        this.weaponType = weaponType;
    }

    public void setDefaultStrength(float defaultStrength) {
        this.defaultStrength = defaultStrength;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }


    public void addMainAttribute(WeaponAttributeTypes attributeType) {
        this.attributeTypes.put(attributeType, 1.0F);
    }

    public void addSecondaryAttribute(WeaponAttributeTypes attributeType) {

    }
}
