package de.blutmondgilde.blutmondrpg.items.templates;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.function.Supplier;

public class BucketTemplate extends BucketItem {
    public BucketTemplate(Supplier<? extends Fluid> supplier) {
        super(supplier, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1));
    }
}
