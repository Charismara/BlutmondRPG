package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.items.BlutmondItemGroups;
import net.minecraft.item.Item;

public class MaterialItem extends Item {
    public MaterialItem() {
        super(new Item.Properties().group(BlutmondItemGroups.MATERIALS));
    }

}
