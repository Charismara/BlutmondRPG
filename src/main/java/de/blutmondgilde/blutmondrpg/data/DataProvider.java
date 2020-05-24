package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class DataProvider {
    public static class Items {
        public static final Tag<Item> COPPER_INGOT = makeForgeTag("ingots/copper");
        public static final Tag<Item> TIN_INGOT = makeForgeTag("ingots/tin");
        public static final Tag<Item> BRONZE_INGOT = makeForgeTag("ingots/bronze");
        public static final Tag<Item> STEEL_INGOT = makeForgeTag("ingots/steel");
        public static final Tag<Item> PLATINUM_INGOT = makeForgeTag("ingots/platinum");
        public static final Tag<Item> DARK_STEEL_INGOT = makeForgeTag("ingots/dark_steel");
        public static final Tag<Item> MITHRIL_INGOT = makeForgeTag("ingots/mithril");
        public static final Tag<Item> DELDRIMOR_STEEL_INGOT = makeForgeTag("ingots/deldrimor_steel");

        public static final Tag<Item> COPPER_NUGGET = makeForgeTag("nuggets/copper");
        public static final Tag<Item> TIN_NUGGET = makeForgeTag("nuggets/tin");
        public static final Tag<Item> BRONZE_NUGGET = makeForgeTag("nuggets/bronze");
        public static final Tag<Item> STEEL_NUGGET = makeForgeTag("nuggets/steel");
        public static final Tag<Item> PLATINUM_NUGGET = makeForgeTag("nuggets/platinum");
        public static final Tag<Item> DARK_STEEL_NUGGET = makeForgeTag("nuggets/dark_steel");
        public static final Tag<Item> MITHRIL_NUGGET = makeForgeTag("nuggets/mithril");
        public static final Tag<Item> DELDRIMOR_STEEL_NUGGET = makeForgeTag("nuggets/deldrimor_steel");

        public static final Tag<Item> MITHRILIUM_CHUNK = makeModTag("mithrilium_chunk");

        public static final Tag<Item> COPPER_BLOCK = makeForgeTag("storage_blocks/copper");
        public static final Tag<Item> TIN_BLOCK = makeForgeTag("storage_blocks/tin");
        public static final Tag<Item> BRONZE_BLOCK = makeForgeTag("storage_blocks/bronze");
        public static final Tag<Item> STEEL_BLOCK = makeForgeTag("storage_blocks/steel");
        public static final Tag<Item> PLATINUM_BLOCK = makeForgeTag("storage_blocks/platinum");
        public static final Tag<Item> DARK_STEEL_BLOCK = makeForgeTag("storage_blocks/dark_steel");
        public static final Tag<Item> MITHRIL_BLOCK = makeForgeTag("storage_blocks/mithril");
        public static final Tag<Item> DELDRIMOR_STEEL_BLOCK = makeForgeTag("storage_blocks/deldrimor_steel");

        public static final Tag<Item> COPPER_ORE = makeForgeTag("ores/copper");
        public static final Tag<Item> TIN_ORE = makeForgeTag("ores/tin");
        public static final Tag<Item> PLATINUM_ORE = makeForgeTag("ores/platinum");
        public static final Tag<Item> MITHRIL_ORE = makeForgeTag("ores/mithril");

        public static final Tag<Item> PRIMORDIUM = makeModTag("fuels/primordium");
        public static final Tag<Item> THERMOCATALYTIC_REAGENT = makeModTag("fuels/thermocatalytic_reagent");

        private static Tag<Item> makeModTag(String tag) {
            return new ItemTags.Wrapper(new ResourceLocation(Ref.MOD_ID, tag));
        }

        private static Tag<Item> makeForgeTag(String tag) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", tag));
        }
    }

    public static class Blocks {
        public static final Tag<Block> COPPER_BLOCK = makeForgeTag("storage_blocks/copper");
        public static final Tag<Block> TIN_BLOCK = makeForgeTag("storage_blocks/tin");
        public static final Tag<Block> BRONZE_BLOCK = makeForgeTag("storage_blocks/bronze");
        public static final Tag<Block> STEEL_BLOCK = makeForgeTag("storage_blocks/steel");
        public static final Tag<Block> PLATINUM_BLOCK = makeForgeTag("storage_blocks/platinum");
        public static final Tag<Block> DARK_STEEL_BLOCK = makeForgeTag("storage_blocks/dark_steel");
        public static final Tag<Block> MITHRIL_BLOCK = makeForgeTag("storage_blocks/mithril");
        public static final Tag<Block> DELDRIMOR_STEEL_BLOCK = makeForgeTag("storage_blocks/deldrimor_steel");

        public static final Tag<Block> COPPER_ORE = makeForgeTag("ores/copper");
        public static final Tag<Block> TIN_ORE = makeForgeTag("ores/tin");
        public static final Tag<Block> PLATINUM_ORE = makeForgeTag("ores/platinum");
        public static final Tag<Block> MITHRIL_ORE = makeForgeTag("ores/mithril");

        private static Tag<Block> makeForgeTag(String tag) {
            return new BlockTags.Wrapper(new ResourceLocation("forge", tag));
        }
    }

    public static class Fluids {
        public static final Tag<Fluid> CRYSTALLIZER = makeModTag("crystallizer");

        private static Tag<Fluid> makeModTag(String tag) {
            return new FluidTags.Wrapper(new ResourceLocation(Ref.MOD_ID, tag));
        }
    }
}
