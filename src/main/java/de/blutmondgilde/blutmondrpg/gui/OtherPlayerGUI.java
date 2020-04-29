package de.blutmondgilde.blutmondrpg.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.enums.StringColors;
import de.blutmondgilde.blutmondrpg.util.Ref;
import de.blutmondgilde.blutmondrpg.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class OtherPlayerGUI extends Screen {
    private final String playerName;
    private final BasicClasses playerClass;
    private final int playerLevel;
    private final float hp, maxHp;
    private final Minecraft client;
    private Button inviteButton;

    public OtherPlayerGUI(final String name, final BasicClasses playerClass, final int level, final float hp, final float maxHP, final Minecraft minecraft) {
        super(new TranslationTextComponent("blutmondrpg.gui.otherplayer.title"));
        this.playerName = name;
        this.playerClass = playerClass;
        this.playerLevel = level;
        this.hp = hp;
        this.maxHp = maxHP;
        this.client = minecraft;
    }

    @Override
    protected void init() {
        super.init();
        inviteButton = new Button(this.width / 2 - 120 / 2, this.height / 4 * 3, 120, 20, (new TranslationTextComponent("blutmondrpg.gui.otherplayer.button.invite")).getString(), (p) -> onInviteButtonPressed());
        children.add(inviteButton);
    }

    @Override
    public void render(int mouseX, int mouseY, float f) {
        super.render(mouseX, mouseY, f);
        try {
            renderScreenBackground();
            renderName();
            renderClass();
            renderLevel();
            renderHp();
            inviteButton.render(mouseX, mouseY, f);
        } catch (Exception ignore) {
        }
    }

    private void renderName() {
        drawCenteredString(client.fontRenderer, playerName, this.width / 2, this.height / 4, StringColors.WHITE.getColorCode());
    }

    private void renderClass() {
        final ResourceLocation icon;
        final int width, height;
        width = 64;
        height = 64;

        switch (this.playerClass) {
            case CASTER:
                icon = new ResourceLocation(Ref.MOD_ID, "textures/icons/mage.png");
                break;
            case HUNTER:
                icon = new ResourceLocation(Ref.MOD_ID, "textures/icons/scout.png");
                break;
            case WARRIOR:
                icon = new ResourceLocation(Ref.MOD_ID, "textures/icons/warrior.png");
                break;
            default:
                icon = new ResourceLocation(Ref.MOD_ID, "textures/icons/none.png");
        }


        this.client.getTextureManager().bindTexture(icon);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(icon).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureWithColor((this.width - width) / 2, (this.height + height / 2) / 2, 0, 0, 0, width, height, 256, 256, new Color(255, 255, 255, 192));
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);

    }

    private void renderLevel() {
        final String levelString = "Level: " + this.playerLevel;

        drawCenteredString(client.fontRenderer, levelString, this.width / 2, (this.height) / 4 + (client.fontRenderer.FONT_HEIGHT * 4) / 2, StringColors.GREEN.getColorCode());
    }

    private void renderHp() {
        final int playerHP = Math.round(this.hp);
        final int playerMaxHP = Math.round(this.maxHp);
        final String hpString = "HP: " + playerHP + "/" + playerMaxHP;

        drawCenteredString(client.fontRenderer, hpString, this.width / 2, (this.height) / 4 + (client.fontRenderer.FONT_HEIGHT * 2) / 2, StringColors.RED.getColorCode());
    }

    private void onInviteButtonPressed() {
        client.player.sendChatMessage("/brpg group invite " + this.playerName);
        TextComponent message = new TranslationTextComponent("blutmondrpg.gui.otherplayer.button.invited.1");
        message.appendText(" " + this.playerName + " ");
        message.appendSibling(new TranslationTextComponent("blutmondrpg.gui.otherplayer.button.invited.2"));

        client.player.sendMessage(message);
        client.player.closeScreen();
    }

    private void renderScreenBackground() {
        final ResourceLocation backgrond = new ResourceLocation(Ref.MOD_ID, "textures/gui/screenbg.png");
        final int width, height;
        width = this.width / 4;
        height = (this.height / 4 * 3 - this.height / 4) + 32;

        this.client.getTextureManager().bindTexture(backgrond);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(backgrond).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureWithColor((this.width - width - client.fontRenderer.FONT_HEIGHT) / 2, (this.height - height + 20) / 2, 0, 0, 0, width, height, 174, 165, new Color(255, 255, 255, 255));
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }
}
