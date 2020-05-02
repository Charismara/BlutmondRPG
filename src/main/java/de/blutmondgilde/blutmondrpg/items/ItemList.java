package de.blutmondgilde.blutmondrpg.items;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.items.weapons.WeaponSword;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ItemList {
    private static final DeferredRegister<Item> ITEM_REGISTRY = BlutmondRPG.getItemManager();
    public static final RegistryObject<Item> SWORD = ITEM_REGISTRY.register("sword", () -> new WeaponSword("sword"));
}
