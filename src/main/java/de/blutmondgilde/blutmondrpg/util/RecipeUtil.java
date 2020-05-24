package de.blutmondgilde.blutmondrpg.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RecipeUtil {

    public static <T extends IRecipe<?>> Collection<T> getRecipes(World world, IRecipeType<T> recipeType) {
        Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, world.getRecipeManager(), "field_199522_d");
        if (recipes != null) {
            Map<ResourceLocation, IRecipe<?>> typedRecipes = recipes.get(recipeType);
            if (typedRecipes != null) {
                return (Collection<T>)typedRecipes.values();
            }
        }
        return new ArrayList<>();
    }

    public static Collection<FurnaceRecipe> getCookingRecipes(World world) {
        return getRecipes(world, IRecipeType.SMELTING);
    }

    public static FurnaceRecipe getSmelingRecipeFor(World world, ItemStack stack) {
        return getCookingRecipes(world).stream().filter(furnaceRecipe -> furnaceRecipe.getIngredients().get(0).test(stack)).findFirst().orElse(null);
    }

}

