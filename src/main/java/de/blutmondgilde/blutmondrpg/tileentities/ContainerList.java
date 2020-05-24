package de.blutmondgilde.blutmondrpg.tileentities;

import de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace.AlloyFurnaceContainer;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerList {
    private static final DeferredRegister<ContainerType<?>> CONTAINER_REGISTRY = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Ref.MOD_ID);
    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE = CONTAINER_REGISTRY.register("alloy_furnace_container", () -> IForgeContainerType.create(AlloyFurnaceContainer::createContainerClientSide));

    public static void register() {
        CONTAINER_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        Ref.LOGGER.debug("Registered Container.");
    }
}
