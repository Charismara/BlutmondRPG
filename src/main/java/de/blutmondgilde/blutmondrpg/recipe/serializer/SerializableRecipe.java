package de.blutmondgilde.blutmondrpg.recipe.serializer;

import com.google.gson.JsonObject;
import de.blutmondgilde.blutmondrpg.recipe.generator.IJSONGenerator;
import de.blutmondgilde.blutmondrpg.recipe.generator.IJsonFile;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public abstract class SerializableRecipe implements IRecipe<IInventory>, IJsonFile, IJSONGenerator {

    public String type = getSerializer().getRegistryName().toString();
    private transient ResourceLocation resourceLocation;

    public SerializableRecipe(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public ResourceLocation getId() {
        return resourceLocation;
    }

    @Override
    public String getRecipeKey() {
        return resourceLocation.getPath();
    }

    @Override
    public String getRecipeSubfolder() {
        return new ResourceLocation(type).getPath();
    }

    @Override
    public abstract GenericSerializer<? extends SerializableRecipe> getSerializer();

    @Override
    public JsonObject generate() {
        return ((GenericSerializer<SerializableRecipe>) getSerializer()).write(this);
    }
}

