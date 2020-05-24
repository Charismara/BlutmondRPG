package de.blutmondgilde.blutmondrpg.recipe.serializer;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;

public interface IRecipeSerializerReversed<T extends IRecipe<?>> {
    JsonObject write(T recipe);
}
