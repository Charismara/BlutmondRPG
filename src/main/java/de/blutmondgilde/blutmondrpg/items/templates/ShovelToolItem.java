package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.items.BlutmondItemGroups;
import de.blutmondgilde.blutmondrpg.items.tiers.BlutmondTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;

public class ShovelToolItem extends ShovelItem {
    public ShovelToolItem(BlutmondTiers tier) {
        super(tier, 1.5F, -3F, new Item.Properties().group(BlutmondItemGroups.TOOLS).addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    }
}
