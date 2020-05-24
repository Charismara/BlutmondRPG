package de.blutmondgilde.blutmondrpg.blocks;

import de.blutmondgilde.blutmondrpg.blocks.templates.DefaultOreBlock;
import de.blutmondgilde.blutmondrpg.blocks.templates.FluidBlockTemplate;
import de.blutmondgilde.blutmondrpg.blocks.templates.MaterialBlock;
import de.blutmondgilde.blutmondrpg.fluids.FluidList;
import de.blutmondgilde.blutmondrpg.items.tiers.BlutmondTiers;
import de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace.AlloyFurnaceBlockInventory;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList {
    private static final DeferredRegister<Block> BLOCK_REGISTRY = new DeferredRegister<>(ForgeRegistries.BLOCKS, Ref.MOD_ID);
    //Ingot Blocks
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCK_REGISTRY.register("copper_block", () -> new MaterialBlock(BlutmondTiers.COPPER.getHarvestLevel() - 1));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCK_REGISTRY.register("tin_block", () -> new MaterialBlock(BlutmondTiers.TIN.getHarvestLevel() - 1));
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCK_REGISTRY.register("bronze_block", () -> new MaterialBlock(BlutmondTiers.BRONZE.getHarvestLevel() - 1));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCK_REGISTRY.register("steel_block", () -> new MaterialBlock(BlutmondTiers.STEEL.getHarvestLevel() - 1));
    public static final RegistryObject<Block> PLATINUM_BLOCK = BLOCK_REGISTRY.register("platinum_block", () -> new MaterialBlock(BlutmondTiers.PLATINUM.getHarvestLevel() - 1));
    public static final RegistryObject<Block> DARK_STEEL_BLOCK = BLOCK_REGISTRY.register("dark_steel_block", () -> new MaterialBlock(BlutmondTiers.DARK_STEEL.getHarvestLevel() - 1));
    public static final RegistryObject<Block> MITHRIL_BLOCK = BLOCK_REGISTRY.register("mithril_block", () -> new MaterialBlock(BlutmondTiers.MITHRIL.getHarvestLevel() - 1));
    public static final RegistryObject<Block> DELDRIMOR_STEEL_BLOCK = BLOCK_REGISTRY.register("deldrimor_steel_block", () -> new MaterialBlock(BlutmondTiers.DELDRIMOR_STEEL.getHarvestLevel() - 1));
    //Ores
    public static final RegistryObject<Block> COPPER_ORE = BLOCK_REGISTRY.register("copper_ore", () -> new DefaultOreBlock(BlutmondTiers.COPPER.getHarvestLevel() - 1));
    public static final RegistryObject<Block> TIN_ORE = BLOCK_REGISTRY.register("tin_ore", () -> new DefaultOreBlock(BlutmondTiers.TIN.getHarvestLevel() - 1));
    public static final RegistryObject<Block> PLATINUM_ORE = BLOCK_REGISTRY.register("platinum_ore", () -> new DefaultOreBlock(BlutmondTiers.PLATINUM.getHarvestLevel() - 1));
    public static final RegistryObject<Block> MITHRIL_ORE = BLOCK_REGISTRY.register("mithril_ore", () -> new DefaultOreBlock(BlutmondTiers.MITHRIL.getHarvestLevel() - 1));
    //FluidBlocks
    public static final RegistryObject<FlowingFluidBlock> CRYSTALLIZER_FLUID = BLOCK_REGISTRY.register("crystallizer", () -> new FluidBlockTemplate(FluidList.CRYSTALLIZER));
    //TileEntity
    public static final RegistryObject<ContainerBlock> ALLOY_FURNACE = BLOCK_REGISTRY.register("alloy_furnace", AlloyFurnaceBlockInventory::new);

    public static void register(){
        BLOCK_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        Ref.LOGGER.debug("Registered Blocks.");
    }
}
