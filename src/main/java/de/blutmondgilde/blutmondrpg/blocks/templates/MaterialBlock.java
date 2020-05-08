package de.blutmondgilde.blutmondrpg.blocks.templates;

import de.blutmondgilde.blutmondrpg.blocks.materials.BlutmondMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;

public class MaterialBlock extends Block {
    private final int harvestLevel;

    public MaterialBlock() {
        this(0);
    }

    public MaterialBlock(int harvestLevel) {
        super(
                Block.Properties
                        .create(BlutmondMaterials.STORAGE_BLOCK_MATERIAL)
                        .harvestTool(ToolType.PICKAXE)
                        .harvestLevel(harvestLevel)
                        .hardnessAndResistance(3.0F)
                        .sound(SoundType.METAL)
        );

        this.harvestLevel = harvestLevel;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.harvestLevel;
    }
}
