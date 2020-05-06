package de.blutmondgilde.blutmondrpg.items.tiers;

import de.blutmondgilde.blutmondrpg.items.ItemList;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public enum BlutmondTiers implements IItemTier {
    COPPER(1, 190, 5.0F, 1.5F, 10, getRepairItem(ItemList.COPPER_INGOT)),
    TIN(1, 190, 6.5F, 1.7F, 12, getRepairItem(ItemList.TIN_INGOT)),
    BRONZE(3, 1000, 9.0F, 3.5F, 12, getRepairItem(ItemList.BRONZE_INGOT)),
    STEEL(4, 1600, 9.5F, 4F, 10, getRepairItem(ItemList.STEEL_INGOT)),
    DARK_STEEL(5, 2400, 10F, 4.5F, 12, getRepairItem(ItemList.DARK_STEEL_INGOT)),
    PLATINUM(6, 500, 16F, 5.0F, 32, getRepairItem(ItemList.PLATINUM_INGOT)),
    MITHRIL(7, 2800, 11F, 6.0F, 12, getRepairItem(ItemList.MITHRIL_INGOT)),
    DELDRIMOR_STEEL(8, -1, 12F, 8F, 22, getRepairItem(ItemList.DELDRIMOR_STEEL_INGOT));


    private final int harvestLevel, maxUses, enchantability;
    private final float efficiency, attackDamage;
    private final LazyValue<Ingredient> repairMaterial;

    BlutmondTiers(final int harvestLevel, final int maxUses, final float efficiency, final float attackDamage, final int enchantability, final Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    private static Supplier<Ingredient> getRepairItem(final RegistryObject<Item> item) {
        return () -> Ingredient.fromItems(item.get());
    }
}
