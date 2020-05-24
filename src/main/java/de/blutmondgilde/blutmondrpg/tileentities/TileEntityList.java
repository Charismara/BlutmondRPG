package de.blutmondgilde.blutmondrpg.tileentities;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace.AlloyFurnaceTileEntity;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityList {
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_REGISTRY = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Ref.MOD_ID);
    public static final RegistryObject<TileEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE = TILE_ENTITY_REGISTRY.register("alloy_furnace_tile_entity", () -> TileEntityType.Builder.create(AlloyFurnaceTileEntity::new, BlockList.ALLOY_FURNACE.get()).build(null));

    public static void register(){
        TILE_ENTITY_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        Ref.LOGGER.debug("Registered TileEntities.");
    }
}
