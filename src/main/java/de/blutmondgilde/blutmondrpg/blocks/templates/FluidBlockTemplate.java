package de.blutmondgilde.blutmondrpg.blocks.templates;

import de.blutmondgilde.blutmondrpg.fluids.BlutmondFluidTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class FluidBlockTemplate extends FlowingFluidBlock {

    public FluidBlockTemplate(final Supplier<? extends FlowingFluid> supplier) {
        super(
                supplier,
                Block.Properties.create(Material.WATER)
                        .doesNotBlockMovement()
                        .noDrops()
                        .hardnessAndResistance(100F)
        );

    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);

        if (entityIn.getRidingEntity() instanceof BoatEntity) {
            entityIn.inWater = false;
        } else {
            if (entityIn.handleFluidAcceleration(BlutmondFluidTags.CRYSTALLIZER)) {
                entityIn.fallDistance = 0.0F;
                entityIn.extinguish();
                entityIn.inWater = true;
            }
        }
    }
}
