package de.blutmondgilde.blutmondrpg.fluids;

import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class BlutmondFluidTags {
    public static final Tag<Fluid> CRYSTALLIZER = makeWrapperTag("crystallizer");

    private static Tag<Fluid> makeWrapperTag(String name) {
        Tag<Fluid> tag = new FluidTags.Wrapper(new ResourceLocation(Ref.MOD_ID, name));
        return tag;
    }
}
