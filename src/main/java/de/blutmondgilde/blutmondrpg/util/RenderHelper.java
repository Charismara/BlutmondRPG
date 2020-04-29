package de.blutmondgilde.blutmondrpg.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderHelper {
    public static void drawColoredBackground(final int x, final int y, final int width, final int height, final Color color) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();

        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        builder.pos(x, y + height, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        builder.pos(x + width, y + height, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        builder.pos(x + width, y, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        builder.pos(x, y, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();

        tessellator.draw();
    }

    public static void drawTextureFaintOut(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, float alpha) {
        drawTextureFaintOut(x, y, z, texX, texY, widthX, widthY, texWidthX, texWidthY, alpha, 0.00390625F, 0.00390625F);
    }

    private static void drawTextureFaintOut(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, float alpha, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);

        tessellator.getBuffer()
                .pos(x, y + widthY, z)
                .color(1.0F, 1.0F, 1.0F, alpha)
                .tex(texX * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y + widthY, z)
                .color(1.0F, 1.0F, 1.0F, alpha)
                .tex((texX + texWidthX) * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y, z)
                .color(1.0F, 1.0F, 1.0F, alpha)
                .tex((texX + texWidthX) * f, texY * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x, y, z)
                .color(1.0F, 1.0F, 1.0F, alpha)
                .tex(texX * f, texY * f1)
                .endVertex();

        tessellator.draw();
    }

    public static void drawTexture(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY) {
        drawTexture(x, y, z, texX, texY, widthX, widthY, texWidthX, texWidthY, 0.00390625F, 0.00390625F);
    }

    private static void drawTexture(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        tessellator.getBuffer()
                .pos(x, y + widthY, z)
                .tex(texX * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y + widthY, z)
                .tex((texX + texWidthX) * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y, z)
                .tex((texX + texWidthX) * f, texY * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x, y, z)
                .tex(texX * f, texY * f1)
                .endVertex();

        tessellator.draw();
    }

    public static void drawTextureWithColor(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, Color color) {
        drawTextureWithColor(x, y, z, texX, texY, widthX, widthY, texWidthX, texWidthY, color, 0.00390625F, 0.00390625F);
    }

    private static void drawTextureWithColor(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, Color color, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);

        tessellator.getBuffer()
                .pos(x, y + widthY, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .tex(texX * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y + widthY, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .tex((texX + texWidthX) * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .tex((texX + texWidthX) * f, texY * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x, y, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .tex(texX * f, texY * f1)
                .endVertex();

        tessellator.draw();
    }

    public static void drawTextureWithColorFaintOut(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, Color color, float alpha) {
        drawTextureWithColorFaintOut(x, y, z, texX, texY, widthX, widthY, texWidthX, texWidthY, color, alpha, 0.00390625F, 0.00390625F);
    }

    private static void drawTextureWithColorFaintOut(int x, int y, float z, int texX, int texY, int widthX, int widthY, int texWidthX, int texWidthY, Color color, float alpha, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);

        int colorAlpha = Math.round(255 * alpha);

        tessellator.getBuffer()
                .pos(x, y + widthY, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha)
                .tex(texX * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y + widthY, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha)
                .tex((texX + texWidthX) * f, (texY + texWidthY) * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x + widthX, y, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha)
                .tex((texX + texWidthX) * f, texY * f1)
                .endVertex();
        tessellator.getBuffer()
                .pos(x, y, z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha)
                .tex(texX * f, texY * f1)
                .endVertex();

        tessellator.draw();
    }
}
