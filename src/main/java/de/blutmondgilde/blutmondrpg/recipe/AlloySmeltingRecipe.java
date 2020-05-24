package de.blutmondgilde.blutmondrpg.recipe;

import de.blutmondgilde.blutmondrpg.data.DataProvider;
import de.blutmondgilde.blutmondrpg.items.ItemList;
import de.blutmondgilde.blutmondrpg.recipe.serializer.GenericSerializer;
import de.blutmondgilde.blutmondrpg.recipe.serializer.SerializableRecipe;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class AlloySmeltingRecipe extends SerializableRecipe {
    public static GenericSerializer<AlloySmeltingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Ref.MOD_ID, "alloy_smelting"), AlloySmeltingRecipe.class);
    public static List<AlloySmeltingRecipe> RECIPES = new ArrayList<>();


    static {
        new AlloySmeltingRecipe(
                new ResourceLocation(Ref.MOD_ID, "bronze_ingot"),
                new Ingredient.IItemList[]{
                        new Ingredient.TagList(DataProvider.Items.COPPER_INGOT),
                        new Ingredient.TagList(DataProvider.Items.TIN_INGOT)
                },
                300,
                new ItemStack(ItemList.BRONZE_INGOT.get(), 2)
        );
        new AlloySmeltingRecipe(
                new ResourceLocation(Ref.MOD_ID, "steel_ingot"),
                new Ingredient.IItemList[]{
                        new Ingredient.TagList(ItemTags.COALS),
                        new Ingredient.TagList(Tags.Items.INGOTS_IRON)
                },
                450,
                new ItemStack(ItemList.STEEL_INGOT.get())
        );
        new AlloySmeltingRecipe(
                new ResourceLocation(Ref.MOD_ID, "dark_steel_ingot"),
                new Ingredient.IItemList[]{
                        new Ingredient.TagList(DataProvider.Items.PLATINUM_INGOT),
                        new Ingredient.TagList(DataProvider.Items.PRIMORDIUM)
                },
                600,
                new ItemStack(ItemList.DARK_STEEL_INGOT.get())
        );
        new AlloySmeltingRecipe(
                new ResourceLocation(Ref.MOD_ID, "deldrimor_steel_ingot"),
                new Ingredient.IItemList[]{
                        new Ingredient.TagList(DataProvider.Items.MITHRIL_INGOT),
                        new Ingredient.TagList(DataProvider.Items.THERMOCATALYTIC_REAGENT)
                },
                800,
                new ItemStack(ItemList.DELDRIMOR_STEEL_NUGGET.get())
        );
    }

    public Ingredient.IItemList[] input;
    public int processingTime;
    public ItemStack output;

    public AlloySmeltingRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public AlloySmeltingRecipe(ResourceLocation resourceLocation, Ingredient.IItemList[] input, int processingTime, ItemStack output) {
        super(resourceLocation);
        this.input = input;
        this.processingTime = processingTime;
        this.output = output;
        RECIPES.add(this);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    public boolean matches(IItemHandler handler) {
        if (input == null) return false;

        List<ItemStack> handlerItems = new ArrayList<>();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) handlerItems.add(handler.getStackInSlot(i).copy());
        }
        for (Ingredient.IItemList iItemList : input) {
            boolean found = false;
            for (ItemStack stack : iItemList.getStacks()) {
                int i = 0;
                for (; i < handlerItems.size(); i++) {
                    if (handlerItems.get(i).isItemEqual(stack)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    handlerItems.remove(i);
                    break;
                }
            }
            if (!found) return false;
        }
        return handlerItems.size() == 0;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return SERIALIZER.getRecipeType();
    }

    public int getProcessingTime() {
        return processingTime;
    }
}
