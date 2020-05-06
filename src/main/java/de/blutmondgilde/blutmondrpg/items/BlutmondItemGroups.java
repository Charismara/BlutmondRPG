package de.blutmondgilde.blutmondrpg.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BlutmondItemGroups {
    public static final ItemGroup MATERIALS = new Materials();
    public static final ItemGroup WEAPONS = new Weapons();
    public static final ItemGroup ARMOR = new Armor();
    public static final ItemGroup TOOLS = new Tools();

    private static class Materials extends ItemGroup {
        public Materials() {
            super(ItemGroup.GROUPS.length, "blutmondrpg.materials");
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.THERMOCATALYTIC_REAGENT.get());
        }
    }

    private static class Weapons extends ItemGroup {
        public Weapons() {
            super(ItemGroup.GROUPS.length, "blutmondrpg.weapons");
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.STICK);
        }
    }

    private static class Armor extends ItemGroup {
        public Armor() {
            super(ItemGroup.GROUPS.length, "blutmondrpg.armor");
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.STICK);
        }
    }

    private static class Tools extends ItemGroup {
        public Tools() {
            super(ItemGroup.GROUPS.length, "blutmondrpg.tools");
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.DELDRIMOR_STEEL_PICKAXE.get());
        }
    }
}
