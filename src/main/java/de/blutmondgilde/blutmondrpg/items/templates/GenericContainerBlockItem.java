package de.blutmondgilde.blutmondrpg.items.templates;

import de.blutmondgilde.blutmondrpg.items.BlutmondItemGroups;
import net.minecraft.block.ContainerBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class GenericContainerBlockItem extends BlockItem {
    public GenericContainerBlockItem(RegistryObject<ContainerBlock> containerBlock) {
        super(containerBlock.get().getBlock(), new Item.Properties().group(BlutmondItemGroups.MATERIALS));
    }
}
