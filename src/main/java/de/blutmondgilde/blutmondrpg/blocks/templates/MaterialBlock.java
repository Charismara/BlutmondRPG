package de.blutmondgilde.blutmondrpg.blocks.templates;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MaterialBlock extends Block {
    public MaterialBlock() {
        this(0);
    }


    public MaterialBlock(int harvestLevel) {
        super(
                Block.Properties
                        .create(Material.IRON)
                        .harvestTool(ToolType.PICKAXE)
                        .harvestLevel(harvestLevel)
                        .hardnessAndResistance(3.0F)
                        .sound(SoundType.METAL)
        );
    }
}
