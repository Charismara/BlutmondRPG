package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.items.BlutmondItemGroups;
import de.blutmondgilde.blutmondrpg.items.tiers.BlutmondTiers;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class PickaxeToolItem extends PickaxeItem {
    public PickaxeToolItem(BlutmondTiers tier) {
        super(tier, 1, -2.8F, new Item.Properties().group(BlutmondItemGroups.TOOLS));
    }
}
