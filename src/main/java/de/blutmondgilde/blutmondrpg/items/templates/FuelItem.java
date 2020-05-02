package de.blutmondgilde.blutmondrpg.items.templates;

import net.minecraft.item.ItemStack;

public class FuelItem extends MaterialItem {
    private final int burnTime;

    public FuelItem(int burnTime) {
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }

}
