package de.blutmondgilde.blutmondrpg.gui.elements.base;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class BaseImageButton extends ImageButton {
    private static final int resourceFileWidth = 256;
    private static final int resourceFileHeight = 256;

    private ResourceLocation resourceLocation;
    private int yTexStart, yOnHover, xTexStart;
    private int textureWidth, textureHeight;

    public BaseImageButton(int posWidth, int posHeight, int width, int height, final int textureXStart, final int textureYStart, final ResourceLocation resourceLocation, IPressable buttonFunc) {
        super(posWidth, posHeight, width, height, textureXStart, textureYStart, 16, resourceLocation, buttonFunc);
    }

    public BaseImageButton(int posWidth, int posHeight, int width, int height, final int textureXStart, final int textureYStart, final int textureYonHover, final ResourceLocation resourceLocation, IPressable buttonFunc) {
        super(posWidth, posHeight, width, height, textureXStart, textureYStart, textureYonHover, resourceLocation, buttonFunc);
        this.resourceLocation = resourceLocation;
        this.yTexStart = textureYStart;
        this.yOnHover = textureYonHover;
        this.xTexStart = textureXStart;
        this.textureHeight = 128;
        this.textureWidth = 128;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int yStart = this.yTexStart;
        if (this.isHovered()) {
            yStart = this.yOnHover;
        }

        blit(this.x, this.y, this.width, this.height, this.xTexStart, yStart, textureWidth, textureHeight, resourceFileWidth, resourceFileHeight);

        RenderSystem.enableDepthTest();
    }
}
