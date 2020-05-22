package de.blutmondgilde.blutmondrpg.data;


import de.blutmondgilde.blutmondrpg.items.ItemList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraftforge.fml.RegistryObject;

public class DataItemTagProvider extends ItemTagsProvider {

    public DataItemTagProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerTags() {
        register(DataProvider.Items.BRONZE_INGOT, ItemList.BRONZE_INGOT);
        register(DataProvider.Items.COPPER_INGOT, ItemList.COPPER_INGOT);
        register(DataProvider.Items.DARK_STEEL_INGOT, ItemList.DARK_STEEL_INGOT);
        register(DataProvider.Items.DELDRIMOR_STEEL_INGOT, ItemList.DELDRIMOR_STEEL_INGOT);
        register(DataProvider.Items.MITHRIL_INGOT, ItemList.MITHRIL_INGOT);
        register(DataProvider.Items.PLATINUM_INGOT, ItemList.PLATINUM_INGOT);
        register(DataProvider.Items.STEEL_INGOT, ItemList.STEEL_INGOT);
        register(DataProvider.Items.TIN_INGOT, ItemList.TIN_INGOT);

        register(DataProvider.Items.BRONZE_NUGGET, ItemList.BRONZE_NUGGET);
        register(DataProvider.Items.COPPER_NUGGET, ItemList.COPPER_NUGGET);
        register(DataProvider.Items.DARK_STEEL_NUGGET, ItemList.DARK_STEEL_NUGGET);
        register(DataProvider.Items.DELDRIMOR_STEEL_NUGGET, ItemList.DELDRIMOR_STEEL_NUGGET);
        register(DataProvider.Items.MITHRIL_NUGGET, ItemList.MITHRIL_NUGGET);
        register(DataProvider.Items.PLATINUM_NUGGET, ItemList.PLATINUM_NUGGET);
        register(DataProvider.Items.STEEL_NUGGET, ItemList.STEEL_NUGGET);
        register(DataProvider.Items.TIN_NUGGET, ItemList.TIN_NUGGET);

        register(DataProvider.Items.MITHRILIUM_CHUNK, ItemList.MITHRILIUM_CHUNK);

        copyBlocks();
    }

    private void copyBlocks() {
        copy(DataProvider.Blocks.COPPER_BLOCK, DataProvider.Items.COPPER_BLOCK);
        copy(DataProvider.Blocks.TIN_BLOCK, DataProvider.Items.TIN_BLOCK);
        copy(DataProvider.Blocks.BRONZE_BLOCK, DataProvider.Items.BRONZE_BLOCK);
        copy(DataProvider.Blocks.STEEL_BLOCK, DataProvider.Items.STEEL_BLOCK);
        copy(DataProvider.Blocks.PLATINUM_BLOCK, DataProvider.Items.PLATINUM_BLOCK);
        copy(DataProvider.Blocks.DARK_STEEL_BLOCK, DataProvider.Items.DARK_STEEL_BLOCK);
        copy(DataProvider.Blocks.MITHRIL_BLOCK, DataProvider.Items.MITHRIL_BLOCK);
        copy(DataProvider.Blocks.DELDRIMOR_STEEL_BLOCK, DataProvider.Items.DELDRIMOR_STEEL_BLOCK);

        copy(DataProvider.Blocks.COPPER_ORE, DataProvider.Items.COPPER_ORE);
        copy(DataProvider.Blocks.MITHRIL_ORE, DataProvider.Items.MITHRIL_ORE);
        copy(DataProvider.Blocks.PLATINUM_ORE, DataProvider.Items.PLATINUM_ORE);
        copy(DataProvider.Blocks.TIN_ORE, DataProvider.Items.TIN_ORE);
    }

    @SafeVarargs
    private final void register(Tag<Item> tag, RegistryObject<Item>... items) {
        for (RegistryObject<Item> item : items) {
            getBuilder(tag).add(item.get());
        }
    }

}
