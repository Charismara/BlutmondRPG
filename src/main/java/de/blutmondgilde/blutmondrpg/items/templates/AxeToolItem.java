package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.items.BlutmondItemGroups;
import de.blutmondgilde.blutmondrpg.items.tiers.BlutmondTiers;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public class AxeToolItem extends AxeItem {
    public AxeToolItem(BlutmondTiers tier) {
        super(tier, 5.0F, -3.0F, new Item.Properties().group(BlutmondItemGroups.TOOLS).addToolType(ToolType.AXE,tier.getHarvestLevel()));
    }
}
