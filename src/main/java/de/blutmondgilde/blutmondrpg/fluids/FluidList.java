package de.blutmondgilde.blutmondrpg.fluids;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class FluidList {
    private static final DeferredRegister<Fluid> FLUID_REGISTRY = BlutmondRPG.getFluidRegistry();
    public static final RegistryObject<FlowingFluid> CRYSTALLIZER = FLUID_REGISTRY.register("crystallizer", FluidCrystallizer.Source::new);
    public static final RegistryObject<FlowingFluid> CRYSTALLIZER_FLOWING = FLUID_REGISTRY.register("crystallizer_flowing", FluidCrystallizer.Flowing::new);

    public static class Tags {
        public static final Tag<Fluid> CRYSTALLIZER = new FluidTags.Wrapper(new ResourceLocation(Ref.MOD_ID, "crystallizer"));
    }
}
