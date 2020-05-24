package de.blutmondgilde.blutmondrpg.recipe.provider;

import de.blutmondgilde.blutmondrpg.recipe.AlloySmeltingRecipe;
import de.blutmondgilde.blutmondrpg.recipe.generator.IJSONGenerator;
import de.blutmondgilde.blutmondrpg.recipe.generator.IJsonFile;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class BlutmondRPGSerializableProvider extends SerializableProvider {

    public BlutmondRPGSerializableProvider(DataGenerator generator) {
        super(generator, Ref.MOD_ID);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> serializables) {
        AlloySmeltingRecipe.RECIPES.forEach(alloySmeltingRecipe -> serializables.put(alloySmeltingRecipe, alloySmeltingRecipe));
    }
}
