package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.fluids.FluidList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.Tag;
import net.minecraftforge.fml.RegistryObject;

public class DataFluidTagProvider extends FluidTagsProvider {
    public DataFluidTagProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        register(DataProvider.Fluids.CRYSTALLIZER, FluidList.CRYSTALLIZER, FluidList.CRYSTALLIZER_FLOWING);

    }

    @SafeVarargs
    private final void register(Tag<Fluid> tag, RegistryObject<FlowingFluid>... fluids) {
        for (RegistryObject<FlowingFluid> fluid : fluids) {
            getBuilder(tag).add(fluid.get());
        }
    }
}
