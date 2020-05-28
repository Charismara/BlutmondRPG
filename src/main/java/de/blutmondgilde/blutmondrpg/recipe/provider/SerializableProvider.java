package de.blutmondgilde.blutmondrpg.recipe.provider;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.blutmondgilde.blutmondrpg.recipe.generator.IJSONGenerator;
import de.blutmondgilde.blutmondrpg.recipe.generator.IJsonFile;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class SerializableProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    protected final DataGenerator generator;
    private final Map<IJsonFile, IJSONGenerator> serializables;
    private final String modid;

    public SerializableProvider(DataGenerator generatorIn, String modid) {
        this.generator = generatorIn;
        this.modid = modid;
        this.serializables = new HashMap<>();
    }

    @Override
    public void act(DirectoryCache cache) {
        add(serializables);
        Path path = this.generator.getOutputFolder();
        Set<Path> set = Sets.newHashSet();
        serializables.forEach((iJsonFile, ijsonGenerator) -> {
            Path outputFile = path.resolve("data/" + modid + "/recipes/" + (iJsonFile.getRecipeSubfolder() != null ? iJsonFile.getRecipeSubfolder() + "/" : "") + iJsonFile.getRecipeKey() + ".json");
            if (!set.add(outputFile)) {
                throw new IllegalStateException("Duplicate recipe " + iJsonFile.getRecipeKey());
            } else {
                this.saveRecipe(cache, ijsonGenerator.generate(), outputFile);
            }
        });
    }

    protected void saveRecipe(DirectoryCache cache, JsonObject recipeJson, Path output) {
        try {
            String s = GSON.toJson((JsonElement) recipeJson);
            String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
            if (!Objects.equals(cache.getPreviousHash(output), s1) || !Files.exists(output)) {
                Files.createDirectories(output.getParent());
                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(output)) {
                    bufferedwriter.write(s);
                }
            }
            cache.recordHash(output, s1);
        } catch (IOException ioexception) {
            Ref.LOGGER.error("Couldn't save recipe {}", output, ioexception);
        }
    }

    public abstract void add(Map<IJsonFile, IJSONGenerator> serializables);

    @Override
    public String getName() {
        return "BlutmondRPG Serializable";
    }

}
