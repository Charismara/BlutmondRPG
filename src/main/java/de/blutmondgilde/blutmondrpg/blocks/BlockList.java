package de.blutmondgilde.blutmondrpg.blocks;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.blocks.templates.DefaultOreBlock;
import de.blutmondgilde.blutmondrpg.blocks.templates.MaterialBlock;
import de.blutmondgilde.blutmondrpg.items.tiers.BlutmondTiers;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class BlockList {
    private static final DeferredRegister<Block> BLOCKS = BlutmondRPG.getBlockRegistry();
    //Ingot Blocks
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block", () -> new MaterialBlock(BlutmondTiers.COPPER.getHarvestLevel() - 1));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", () -> new MaterialBlock(BlutmondTiers.TIN.getHarvestLevel() - 1));
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new MaterialBlock(BlutmondTiers.BRONZE.getHarvestLevel() - 1));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new MaterialBlock(BlutmondTiers.STEEL.getHarvestLevel() - 1));
    public static final RegistryObject<Block> PLATINUM_BLOCK = BLOCKS.register("platinum_block", () -> new MaterialBlock(BlutmondTiers.PLATINUM.getHarvestLevel() - 1));
    public static final RegistryObject<Block> DARK_STEEL_BLOCK = BLOCKS.register("dark_steel_block", () -> new MaterialBlock(BlutmondTiers.DARK_STEEL.getHarvestLevel() - 1));
    public static final RegistryObject<Block> MITHRIL_BLOCK = BLOCKS.register("mithril_block", () -> new MaterialBlock(BlutmondTiers.MITHRIL.getHarvestLevel() - 1));
    public static final RegistryObject<Block> DELDRIMOR_STEEL_BLOCK = BLOCKS.register("deldrimor_steel_block", () -> new MaterialBlock(BlutmondTiers.DELDRIMOR_STEEL.getHarvestLevel() - 1));
    //Ores
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new DefaultOreBlock(BlutmondTiers.COPPER.getHarvestLevel() - 1));
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", () -> new DefaultOreBlock(BlutmondTiers.TIN.getHarvestLevel() - 1));
    public static final RegistryObject<Block> PLATINUM_ORE = BLOCKS.register("platinum_ore", () -> new DefaultOreBlock(BlutmondTiers.PLATINUM.getHarvestLevel() - 1));
    public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", () -> new DefaultOreBlock(BlutmondTiers.MITHRIL.getHarvestLevel() - 1));

    public BlockList() {
        Ref.LOGGER.debug("Blocks Registered");
    }
}
