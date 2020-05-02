package de.blutmondgilde.blutmondrpg.blocks;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.blocks.templates.MaterialBlock;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class BlockList {
    private static final DeferredRegister<Block> BLOCKS = BlutmondRPG.getBlockRegistry();
    //Ingot Blocks
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block", MaterialBlock::new);
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", MaterialBlock::new);
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", MaterialBlock::new);
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", MaterialBlock::new);
    public static final RegistryObject<Block> PLATINUM_BLOCK = BLOCKS.register("platinum_block", MaterialBlock::new);
    public static final RegistryObject<Block> DARK_STEEL_BLOCK = BLOCKS.register("dark_steel_block", MaterialBlock::new);
    public static final RegistryObject<Block> MITHRIL_BLOCK = BLOCKS.register("mithril_block", MaterialBlock::new);
    public static final RegistryObject<Block> DELDRIMOR_STEEL_BLOCK = BLOCKS.register("deldrimor_steel_block", MaterialBlock::new);
    //Ores
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", MaterialBlock::new);
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", MaterialBlock::new);
    public static final RegistryObject<Block> PLATINUM_ORE = BLOCKS.register("platinum_ore", MaterialBlock::new);
    public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", MaterialBlock::new);

    public BlockList() {
        Ref.LOGGER.debug("Blocks Registered");
    }
}
