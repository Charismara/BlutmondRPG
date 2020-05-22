package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.Tag;
import net.minecraftforge.fml.RegistryObject;

public class DataBlockTagProvider extends BlockTagsProvider {
    public DataBlockTagProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerTags() {
        register(DataProvider.Blocks.COPPER_BLOCK, BlockList.COPPER_BLOCK);
        register(DataProvider.Blocks.TIN_BLOCK, BlockList.TIN_BLOCK);
        register(DataProvider.Blocks.BRONZE_BLOCK, BlockList.BRONZE_BLOCK);
        register(DataProvider.Blocks.STEEL_BLOCK, BlockList.STEEL_BLOCK);
        register(DataProvider.Blocks.PLATINUM_BLOCK, BlockList.PLATINUM_BLOCK);
        register(DataProvider.Blocks.DARK_STEEL_BLOCK, BlockList.DARK_STEEL_BLOCK);
        register(DataProvider.Blocks.MITHRIL_BLOCK, BlockList.MITHRIL_BLOCK);
        register(DataProvider.Blocks.DELDRIMOR_STEEL_BLOCK, BlockList.DELDRIMOR_STEEL_BLOCK);

        register(DataProvider.Blocks.COPPER_ORE, BlockList.COPPER_ORE);
        register(DataProvider.Blocks.MITHRIL_ORE, BlockList.MITHRIL_ORE);
        register(DataProvider.Blocks.PLATINUM_ORE, BlockList.PLATINUM_ORE);
        register(DataProvider.Blocks.TIN_ORE, BlockList.TIN_ORE);
    }

    @SafeVarargs
    private final void register(Tag<Block> tag, RegistryObject<Block>... blocks) {
        for (RegistryObject<Block> block : blocks) {
            getBuilder(tag).add(block.get());
        }
    }
}
