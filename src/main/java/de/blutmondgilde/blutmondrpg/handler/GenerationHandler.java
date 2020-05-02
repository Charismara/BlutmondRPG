package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class GenerationHandler {
    //veins, size
    //Coal: 20, 17
    //Iron: 20, 9
    //Redstone: 8, 8
    //Gold: 2, 9
    //Diamond: 1, 8
    //Lapis: 1, 7

    @SubscribeEvent
    public void loadComplete(final FMLLoadCompleteEvent e) {
        GenerationHandler.generateOres();
    }

    public static void generateOres() {
        generateOre(BlockList.COPPER_ORE, 20, 10, 30, 200);
        generateOre(BlockList.TIN_ORE, 20, 8, 25, 150);
        generateOre(BlockList.PLATINUM_ORE, 1, 4, 5, 10);
        generateOre(BlockList.MITHRIL_ORE, 5, 8, 5, 50);
        Ref.LOGGER.debug("Registered Ore Generator");
    }


    private static void generateOre(final RegistryObject<Block> oreBlock, final int veinsPerChunk, final int maxVeinSize, final int minY, final int maxY) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            final ConfiguredPlacement orePlacement = Placement.COUNT_RANGE.configure(new CountRangeConfig(veinsPerChunk, minY, 0, maxY));
            biome.addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, oreBlock.get().getDefaultState(), maxVeinSize))
                            .withPlacement(orePlacement)
            );
            Ref.LOGGER.debug("Generated " + oreBlock.get().getRegistryName().getNamespace());
        }
    }
}
