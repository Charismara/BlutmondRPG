package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.items.ItemList;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class DataRecipeProvider extends RecipeProvider {
    public DataRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerFuelRecipes(consumer);
        registerTools(consumer);
        registerNuggets(consumer);
        registerIngots(consumer);
        registerStorageBlocks(consumer);
        registerCrafting(consumer);
    }

    private void registerCrafting(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ItemList.ALLOY_FURNACE.get())
                .patternLine("BBB")
                .patternLine("BFB")
                .patternLine("BBB")
                .key('B', Items.BRICKS)
                .key('F', Items.BLAST_FURNACE)
                .addCriterion("hasItem", hasItem(Items.BLAST_FURNACE))
                .build(consumer, getRecipeResourceLocation("blocks", ItemList.ALLOY_FURNACE));
    }

    private void registerStorageBlocks(Consumer<IFinishedRecipe> consumer) {
        registerStorageBlocks(consumer, DataProvider.Items.BRONZE_INGOT, ItemList.BRONZE_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.COPPER_INGOT, ItemList.COPPER_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.DARK_STEEL_INGOT, ItemList.DARK_STEEL_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.DELDRIMOR_STEEL_INGOT, ItemList.DELDRIMOR_STEEL_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.MITHRIL_INGOT, ItemList.MITHRIL_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.PLATINUM_INGOT, ItemList.PLATINUM_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.STEEL_INGOT, ItemList.STEEL_BLOCK);
        registerStorageBlocks(consumer, DataProvider.Items.TIN_INGOT, ItemList.TIN_BLOCK);
    }

    private void registerStorageBlocks(Consumer<IFinishedRecipe> consumer, Tag<Item> ingot, RegistryObject<Item> block) {
        ShapedRecipeBuilder.shapedRecipe(block.get())
                .patternLine("NNN")
                .patternLine("NNN")
                .patternLine("NNN")
                .key('N', ingot)
                .setGroup(Ref.MOD_ID + "_materials")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("storage_blocks", block));
    }

    private void registerIngots(Consumer<IFinishedRecipe> consumer) {
        registerIngots(consumer, DataProvider.Items.BRONZE_NUGGET, ItemList.BRONZE_INGOT, DataProvider.Items.BRONZE_BLOCK);
        registerIngots(consumer, DataProvider.Items.COPPER_NUGGET, ItemList.COPPER_INGOT, DataProvider.Items.COPPER_BLOCK);
        registerIngots(consumer, DataProvider.Items.DARK_STEEL_NUGGET, ItemList.DARK_STEEL_INGOT, DataProvider.Items.DARK_STEEL_BLOCK);
        registerIngots(consumer, DataProvider.Items.DELDRIMOR_STEEL_NUGGET, ItemList.DELDRIMOR_STEEL_INGOT, DataProvider.Items.DELDRIMOR_STEEL_BLOCK);
        registerIngots(consumer, DataProvider.Items.MITHRIL_NUGGET, ItemList.MITHRIL_INGOT, DataProvider.Items.MITHRIL_BLOCK);
        registerIngots(consumer, DataProvider.Items.PLATINUM_NUGGET, ItemList.PLATINUM_INGOT, DataProvider.Items.PLATINUM_BLOCK);
        registerIngots(consumer, DataProvider.Items.STEEL_NUGGET, ItemList.STEEL_INGOT, DataProvider.Items.STEEL_BLOCK);
        registerIngots(consumer, DataProvider.Items.TIN_NUGGET, ItemList.TIN_INGOT, DataProvider.Items.TIN_BLOCK);
    }

    private void registerIngots(Consumer<IFinishedRecipe> consumer, Tag<Item> nugget, RegistryObject<Item> ingot, Tag<Item> block) {
        registerIngotsFromNuggets(consumer, nugget, ingot);
        registerIngotsFromStorageBlock(consumer, block, ingot);
    }

    private void registerIngotsFromNuggets(Consumer<IFinishedRecipe> consumer, Tag<Item> nugget, RegistryObject<Item> ingot) {
        ShapedRecipeBuilder.shapedRecipe(ingot.get())
                .patternLine("NNN")
                .patternLine("NNN")
                .patternLine("NNN")
                .key('N', nugget)
                .setGroup(Ref.MOD_ID + "_materials")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("ingots/nuggets", ingot));
    }

    private void registerIngotsFromStorageBlock(Consumer<IFinishedRecipe> consumer, Tag<Item> block, RegistryObject<Item> ingot) {
        ShapelessRecipeBuilder.shapelessRecipe(ingot.get(), 9)
                .addIngredient(block)
                .setGroup(Ref.MOD_ID + "_materials")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("ingots/storage_blocks", ingot));
    }

    private void registerNuggets(Consumer<IFinishedRecipe> consumer) {
        registerNuggets(consumer, DataProvider.Items.BRONZE_INGOT, ItemList.BRONZE_NUGGET);
        registerNuggets(consumer, DataProvider.Items.COPPER_INGOT, ItemList.COPPER_NUGGET);
        registerNuggets(consumer, DataProvider.Items.DARK_STEEL_INGOT, ItemList.DARK_STEEL_NUGGET);
        registerNuggets(consumer, DataProvider.Items.DELDRIMOR_STEEL_INGOT, ItemList.DELDRIMOR_STEEL_NUGGET);
        registerNuggets(consumer, DataProvider.Items.MITHRIL_INGOT, ItemList.MITHRIL_NUGGET);
        registerNuggets(consumer, DataProvider.Items.PLATINUM_INGOT, ItemList.PLATINUM_NUGGET);
        registerNuggets(consumer, DataProvider.Items.STEEL_INGOT, ItemList.STEEL_NUGGET);
        registerNuggets(consumer, DataProvider.Items.TIN_INGOT, ItemList.TIN_NUGGET);
    }

    private void registerNuggets(Consumer<IFinishedRecipe> consumer, Tag<Item> ingot, RegistryObject<Item> nugget) {
        ShapelessRecipeBuilder.shapelessRecipe(nugget.get(), 9)
                .addIngredient(ingot)
                .setGroup(Ref.MOD_ID + "_materials")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("nuggets", nugget));
    }

    private void registerTools(Consumer<IFinishedRecipe> consumer) {
        registerTools(consumer, DataProvider.Items.BRONZE_INGOT, ItemList.BRONZE_AXE, ItemList.BRONZE_PICKAXE, ItemList.BRONZE_SHOVEL);
        registerTools(consumer, DataProvider.Items.COPPER_INGOT, ItemList.COPPER_AXE, ItemList.COPPER_PICKAXE, ItemList.COPPER_SHOVEL);
        registerTools(consumer, DataProvider.Items.DARK_STEEL_INGOT, ItemList.DARK_STEEL_AXE, ItemList.DARK_STEEL_PICKAXE, ItemList.DARK_STEEL_SHOVEL);
        registerTools(consumer, DataProvider.Items.DELDRIMOR_STEEL_INGOT, ItemList.DELDRIMOR_STEEL_AXE, ItemList.DELDRIMOR_STEEL_PICKAXE, ItemList.DELDRIMOR_STEEL_SHOVEL);
        registerTools(consumer, DataProvider.Items.MITHRIL_INGOT, ItemList.MITHRIL_AXE, ItemList.MITHRIL_PICKAXE, ItemList.MITHRIL_SHOVEL);
        registerTools(consumer, DataProvider.Items.PLATINUM_INGOT, ItemList.PLATINUM_AXE, ItemList.PLATINUM_PICKAXE, ItemList.PLATINUM_SHOVEL);
        registerTools(consumer, DataProvider.Items.STEEL_INGOT, ItemList.STEEL_AXE, ItemList.STEEL_PICKAXE, ItemList.STEEL_SHOVEL);
        registerTools(consumer, DataProvider.Items.TIN_INGOT, ItemList.TIN_AXE, ItemList.TIN_PICKAXE, ItemList.TIN_SHOVEL);
    }

    private void registerTools(Consumer<IFinishedRecipe> consumer, Tag<Item> material, RegistryObject<Item> axe, RegistryObject<Item> pickaxe, RegistryObject<Item> shovel) {
        registerShovel(consumer, material, shovel);
        registerPickaxe(consumer, material, pickaxe);
        registerAxe(consumer, material, axe);
    }

    private void registerAxe(Consumer<IFinishedRecipe> consumer, Tag<Item> material, RegistryObject<Item> axe) {
        ShapedRecipeBuilder.shapedRecipe(axe.get())
                .patternLine("II ")
                .patternLine("IS ")
                .patternLine(" S ")
                .key('I', material)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup(Ref.MOD_ID + "_tools")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("axes", axe));
        ShapedRecipeBuilder.shapedRecipe(axe.get())
                .patternLine(" II")
                .patternLine(" SI")
                .patternLine(" S ")
                .key('I', material)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup(Ref.MOD_ID + "_tools")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("axes_mirrored", axe));
    }

    private void registerPickaxe(Consumer<IFinishedRecipe> consumer, Tag<Item> material, RegistryObject<Item> pickaxe) {
        ShapedRecipeBuilder.shapedRecipe(pickaxe.get())
                .patternLine("III")
                .patternLine(" S ")
                .patternLine(" S ")
                .key('I', material)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup(Ref.MOD_ID + "_tools")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("pickaxes", pickaxe));
    }

    private void registerShovel(Consumer<IFinishedRecipe> consumer, Tag<Item> material, RegistryObject<Item> shovel) {
        ShapedRecipeBuilder.shapedRecipe(shovel.get())
                .patternLine(" I ")
                .patternLine(" S ")
                .patternLine(" S ")
                .key('I', material)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup(Ref.MOD_ID + "_tools")
                .addCriterion("hasStick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer, getRecipeResourceLocation("shovels", shovel));
    }

    private void registerFuelRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ItemList.PRIMORDIUM.get())
                .patternLine("WWW")
                .patternLine("WCW")
                .patternLine("WWW")
                .key('W', ItemTags.LOGS)
                .key('C', ItemTags.COALS)
                .setGroup(Ref.MOD_ID + "_fuels")
                .addCriterion("hasWood", InventoryChangeTrigger.Instance.forItems(Items.OAK_LOG))
                .build(consumer, getRecipeResourceLocation("fuels", ItemList.PRIMORDIUM));
        ShapedRecipeBuilder.shapedRecipe(ItemList.THERMOCATALYTIC_REAGENT.get())
                .patternLine("PPP")
                .patternLine("PCP")
                .patternLine("PBP")
                .key('P', ItemList.PRIMORDIUM.get())
                .key('C', ItemTags.COALS)
                .key('B', Items.BLAZE_ROD)
                .setGroup(Ref.MOD_ID + "_fuels")
                .addCriterion("hasPrimordium", InventoryChangeTrigger.Instance.forItems(ItemList.PRIMORDIUM.get()))
                .build(consumer, getRecipeResourceLocation("fuels", ItemList.THERMOCATALYTIC_REAGENT));
    }

    private ResourceLocation getRecipeResourceLocation(String path, RegistryObject<Item> item) {
        return new ResourceLocation(Ref.MOD_ID, "crafting/" + path + "/" + item.get().getRegistryName().getPath());
    }
}
