package de.blutmondgilde.blutmondrpg.fluids;

import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidList {
    private static final DeferredRegister<Fluid> FLUID_REGISTRY = new DeferredRegister<>(ForgeRegistries.FLUIDS, Ref.MOD_ID);
    public static final RegistryObject<FlowingFluid> CRYSTALLIZER = FLUID_REGISTRY.register("crystallizer", FluidCrystallizer.Source::new);
    public static final RegistryObject<FlowingFluid> CRYSTALLIZER_FLOWING = FLUID_REGISTRY.register("crystallizer_flowing", FluidCrystallizer.Flowing::new);

    public static void register(){
        FLUID_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        Ref.LOGGER.debug("Registered Fluids.");
    }
}
