package de.blutmondgilde.blutmondrpg.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraft.world.storage.loot.functions.SetContents;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDataBlockLootProvider extends LootTableProvider {
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public BaseDataBlockLootProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    protected abstract void addTables();

    protected LootTable.Builder createDefaultTable(String name, Block block) {
        LootPool.Builder builder = LootPool.builder()
                .name(name)
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block))
                .acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
                .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                        .addOperation("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
                        .addOperation("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE))
                .acceptFunction(SetContents.func_215920_b()
                        .func_216075_a(DynamicLootEntry.func_216162_a(new ResourceLocation("minecraft", "contents")))
                )
                .acceptCondition(SurvivesExplosion.builder());

        return LootTable.builder().addLootPool(builder);
    }

    @Override
    public void act(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                Ref.LOGGER.error("Couldn't write loot table.\n" + path + "\n" + e);
            }
        });
    }

    @Override
    public String getName() {
        return "BlutmondRPG LootTables";
    }
}
