package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;

public class DataBlockLootProvider extends BaseDataBlockLootProvider {
    public DataBlockLootProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables() {
        register(BlockList.BRONZE_BLOCK);
        register(BlockList.DELDRIMOR_STEEL_BLOCK);
        register(BlockList.MITHRIL_BLOCK);
        register(BlockList.DARK_STEEL_BLOCK);
        register(BlockList.PLATINUM_BLOCK);
        register(BlockList.STEEL_BLOCK);
        register(BlockList.TIN_BLOCK);
        register(BlockList.COPPER_BLOCK);

        register(BlockList.MITHRIL_ORE);
        register(BlockList.PLATINUM_ORE);
        register(BlockList.TIN_ORE);
        register(BlockList.COPPER_ORE);
    }

    private void register(RegistryObject<Block> block) {
        lootTables.put(block.get(), createDefaultTable(block.get().getRegistryName().getPath(), block.get()));
    }
}
