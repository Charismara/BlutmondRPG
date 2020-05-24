package de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class AlloyFurnaceBlockInventory extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public AlloyFurnaceBlockInventory() {
        super(Block.Properties.create(Material.ROCK));

        BlockState defaultBlockState = this.stateContainer.getBaseState().with(BURNING_SIDES_COUNT, 0);
        this.setDefaultState(defaultBlockState.with(FACING, Direction.NORTH).with(LIT, Boolean.FALSE));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }


    // --- The block changes its appearance depending on how many of the furnace slots have burning fuel in them
    //  In order to do that, we add a blockstate for each state (0 -> 4), each with a corresponding model.  We also change the blockLight emitted.

    final static int MAX_NUMBER_OF_BURNING_SIDES = 1;
    public static final IntegerProperty BURNING_SIDES_COUNT = IntegerProperty.create("burning_sides_count", 0, MAX_NUMBER_OF_BURNING_SIDES);

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BURNING_SIDES_COUNT);
        builder.add(FACING);
        builder.add(LIT);
    }

    // change the furnace emitted light ("block light") depending on how many slots are burning
    private static final int ALL_SIDES_LIGHT_VALUE = 15; // light value for four sides burning

    /**
     * Amount of block light emitted by the furnace
     */
    public int getLightValue(BlockState state) {
        int lightValue = 0;
        Integer burningSidesCount = state.get(BURNING_SIDES_COUNT);

        if (burningSidesCount != 0) {
            lightValue = ALL_SIDES_LIGHT_VALUE;
        }
        lightValue = MathHelper.clamp(lightValue, 0, ALL_SIDES_LIGHT_VALUE);
        return lightValue;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return createNewTileEntity(world);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new AlloyFurnaceTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (world.isRemote) return ActionResultType.SUCCESS;

        INamedContainerProvider namedContainerProvider = this.getContainer(state, world, pos);
        if (namedContainerProvider != null) {
            if (!(player instanceof ServerPlayerEntity)) return ActionResultType.FAIL;

            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer) -> {
            });
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockPos);
            if (tileentity instanceof AlloyFurnaceTileEntity) {
                AlloyFurnaceTileEntity tileEntityFurnace = (AlloyFurnaceTileEntity) tileentity;
                tileEntityFurnace.dropAllContents(world, blockPos);
            }
            super.onReplaced(state, world, blockPos, newState, isMoving);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY();
        double d2 = (double) pos.getZ() + 0.5D;
        if (rand.nextDouble() < 0.1D) {
            worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        Direction direction = stateIn.get(FACING);
        Direction.Axis direction$axis = direction.getAxis();
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
        double d6 = rand.nextDouble() * 9.0D / 16.0D;
        double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;
        worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
    }
}
