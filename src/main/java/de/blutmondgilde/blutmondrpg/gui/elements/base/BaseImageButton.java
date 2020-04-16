package de.blutmondgilde.blutmondrpg.gui.elements.base;

import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class BaseImageButton extends ImageButton {
    public BaseImageButton(int posWidth, int posHeight, int width, int height, final int textureXStart, final int textureYStart, final ResourceLocation resourceLocation, IPressable buttonFunc) {
        super(posWidth, posHeight, width, height, textureXStart, textureYStart, 16, resourceLocation, buttonFunc);
    }

    public BaseImageButton(int posWidth, int posHeight, int width, int height, final int textureXStart, final int textureYStart, final int textureYonHover, final ResourceLocation resourceLocation, IPressable buttonFunc) {
        super(posWidth, posHeight, width, height, textureXStart, textureYStart, textureYonHover, resourceLocation, buttonFunc);
    }


}
