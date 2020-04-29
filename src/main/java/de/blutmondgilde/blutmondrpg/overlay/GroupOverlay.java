package de.blutmondgilde.blutmondrpg.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import de.blutmondgilde.blutmondrpg.util.Ref;
import de.blutmondgilde.blutmondrpg.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.UUID;

public class GroupOverlay extends AbstractGui {
    private final Minecraft client;
    private final IGroup cap;
    private int screenHeight, screenWidth;
    private static final int Y_OFFSET_VALUE = 12;

    public GroupOverlay(Minecraft client, IGroup cap) {
        this.client = client;
        this.cap = cap;
    }

    public void render() {
        if (this.cap == null) return;
        this.screenHeight = client.getMainWindow().getScaledHeight();
        this.screenWidth = client.getMainWindow().getScaledWidth();

        int yOffset = 0;
        if (cap.getMemberList().isEmpty()) throw new IllegalStateException("MemberList can't be empty");

        for (UUID uuid : cap.getMemberList()) {
            if (uuid.equals(Minecraft.getInstance().player.getUniqueID())) continue;
            try {
                final float hp = PlayerHandler.getGroupMemberHP(uuid);
                final float maxHP = PlayerHandler.getGroupMemberMaxHP(uuid);
                final String name = PlayerHandler.getGroupMemberNames(uuid);

                renderBackground(yOffset);
                renderMemberName(name, yOffset);
                renderHPNum(hp, maxHP, yOffset);

                yOffset += Y_OFFSET_VALUE;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void renderMemberName(final String name, final int yOffset) {
        final int posY = this.screenHeight / 2 - this.client.fontRenderer.FONT_HEIGHT - yOffset;
        drawString(this.client.fontRenderer, name, this.screenWidth - 149, posY, 9868950);
    }

    private void renderHPNum(float hp, float maxHP, int yOffset) {
        final int posY = this.screenHeight / 2 - this.client.fontRenderer.FONT_HEIGHT - yOffset;
        drawRightAlignedString(client.fontRenderer, hp + "/" + maxHP, this.screenWidth, posY, 9868950);
    }


    private void renderBackground(final int yOffset) {
        final int width = 150;
        final ResourceLocation resourceLocation = new ResourceLocation(Ref.MOD_ID, "gui/bars.png");

        this.client.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(resourceLocation).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureWithColor(this.screenWidth - width, this.screenHeight / 2 - this.client.fontRenderer.FONT_HEIGHT - 2 + yOffset, 0, 0, 44, width, this.client.fontRenderer.FONT_HEIGHT + 2, 10, 10, new Color(0, 0, 0, 52));
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }
}
