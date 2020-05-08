package de.blutmondgilde.blutmondrpg.blocks.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class DefaultOreBlock extends OreBlock {
    private final int harvestLevel;

    public DefaultOreBlock() {
        this(1);
    }

    public DefaultOreBlock(final int harvestLevel) {
        super(
                Block.Properties
                        .create(Material.ROCK)
                        .sound(SoundType.STONE)
                        .hardnessAndResistance(4.0F, 3.0F)
        );

        this.harvestLevel = harvestLevel;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }
}
