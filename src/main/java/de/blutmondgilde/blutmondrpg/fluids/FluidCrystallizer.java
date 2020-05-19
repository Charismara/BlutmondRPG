package de.blutmondgilde.blutmondrpg.fluids;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import de.blutmondgilde.blutmondrpg.items.ItemList;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Rarity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

public abstract class FluidCrystallizer extends ForgeFlowingFluid {
    protected FluidCrystallizer() {
        this(
                FluidList.CRYSTALLIZER,
                FluidList.CRYSTALLIZER_FLOWING,
                FluidAttributes.builder(
                        new ResourceLocation(Ref.MOD_ID, "blocks/crystallizer_still"),
                        new ResourceLocation(Ref.MOD_ID, "blocks/crystallizer_flow")
                )
                        .sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
                        .density(1001)
                        .viscosity(300)
                        .temperature(40)
                        .rarity(Rarity.EPIC)
        );
    }

    protected FluidCrystallizer(final Supplier<? extends Fluid> still, final Supplier<? extends Fluid> flowing, final FluidAttributes.Builder attributes) {
        super(new ForgeFlowingFluid.Properties(still, flowing, attributes)
                .bucket(ItemList.CRYSTALLIZER_BUCKET)
                .block(BlockList.CRYSTALLIZER_FLUID)
                .tickRate(4)
                .explosionResistance(100.0F)
                .levelDecreasePerBlock(1)
                .slopeFindDistance(4)
        );
    }


    @Override
    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn == FluidList.CRYSTALLIZER.get() || fluidIn == FluidList.CRYSTALLIZER_FLOWING.get();
    }

    public static class Source extends FluidCrystallizer {
        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return 8;
        }
    }

    public static class Flowing extends FluidCrystallizer {

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }

        @Override
        public int getLevel(IFluidState fluidState) {
            return fluidState.get(FluidCrystallizer.LEVEL_1_8);
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }
    }
}
