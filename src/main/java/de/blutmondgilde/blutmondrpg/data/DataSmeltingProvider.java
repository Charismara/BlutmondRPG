package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.items.ItemList;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class DataSmeltingProvider extends RecipeProvider {
    public DataSmeltingProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerOres(consumer);
    }

    private void registerOres(Consumer<IFinishedRecipe> consumer) {
        registerOreSmelting(consumer, DataProvider.Items.COPPER_ORE, ItemList.COPPER_INGOT);
        registerOreSmelting(consumer, DataProvider.Items.MITHRIL_ORE, ItemList.MITHRIL_INGOT);
        registerOreSmelting(consumer, DataProvider.Items.PLATINUM_ORE, ItemList.PLATINUM_INGOT);
        registerOreSmelting(consumer, DataProvider.Items.TIN_ORE, ItemList.TIN_INGOT);
    }

    private void registerOreSmelting(Consumer<IFinishedRecipe> consumer, Tag<Item> ore, RegistryObject<Item> ingot) {
        CookingRecipeBuilder
                .smeltingRecipe(Ingredient.fromTag(ore), ingot.get(), 0.5f, 200)
                .addCriterion("hasItem", hasItem(ore))
                .build(consumer, new ResourceLocation(Ref.MOD_ID, "smelting/ores/" + ingot.get().getRegistryName().getPath().substring(0, ingot.get().getRegistryName().getPath().length() - 6)));
    }
}
