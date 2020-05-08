package de.blutmondgilde.blutmondrpg.blocks.materials;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlutmondMaterials extends Material.Builder {
    public static final Material STORAGE_BLOCK_MATERIAL = (new BlutmondMaterials(MaterialColor.IRON)).requiresTool().build();


    public BlutmondMaterials(MaterialColor color) {
        super(color);
    }
}
