package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.items.BlutmondItemGroups;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class MaterialBlockItem extends BlockItem {
    public MaterialBlockItem(RegistryObject<Block> block) {
        super(block.get(), new Item.Properties().group(BlutmondItemGroups.MATERIALS));
    }

    public MaterialBlockItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties.group(BlutmondItemGroups.MATERIALS));
    }
}
