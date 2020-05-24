package de.blutmondgilde.blutmondrpg.blocks.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class StoneBlock extends Block {
    private final int harvestLevel;

    public StoneBlock(int harvestLevel) {
        super(
                Block.Properties.create(Material.ROCK)
                        .hardnessAndResistance(3.0F)
                        .harvestLevel(harvestLevel)
                        .harvestTool(ToolType.PICKAXE)
                        .sound(SoundType.STONE)
        );

        this.harvestLevel = harvestLevel;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }
}
