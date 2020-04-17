package de.blutmondgilde.blutmondrpg.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.util.Ref;
import de.blutmondgilde.blutmondrpg.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ClassOverlay extends AbstractGui {
    private int screenWidth;
    private int screenHeight;
    private Minecraft client;
    private static final ResourceLocation CLASS_ICON_ATLAS = new ResourceLocation(Ref.MOD_ID, "textures/atlas/classiconatlas.png");
    private static final ResourceLocation CLASS_ICON_WARRIOR = new ResourceLocation(Ref.MOD_ID, "textures/icons/warrior.png");
    private static final ResourceLocation CLASS_ICON_MAGE = new ResourceLocation(Ref.MOD_ID, "textures/icons/mage.png");
    private static final ResourceLocation CLASS_ICON_SCOUT = new ResourceLocation(Ref.MOD_ID, "textures/icons/scout.png");
    private static final ResourceLocation CLASS_ICON_PRIEST = new ResourceLocation(Ref.MOD_ID, "textures/icons/priest.png");
    private static final ResourceLocation CLASS_ICON_WARRIOR_LOW = new ResourceLocation(Ref.MOD_ID, "textures/icons/128/warrior.png");
    private static final ResourceLocation CLASS_ICON_MAGE_LOW = new ResourceLocation(Ref.MOD_ID, "textures/icons/128/mage.png");
    private static final ResourceLocation CLASS_ICON_SCOUT_LOW = new ResourceLocation(Ref.MOD_ID, "textures/icons/128/scout.png");
    private static final ResourceLocation CLASS_ICON_PRIEST_LOW = new ResourceLocation(Ref.MOD_ID, "textures/icons/128/priest.png");
    private static final ResourceLocation BAR_ATLAS = new ResourceLocation(Ref.MOD_ID, "textures/gui/bars.png");
    private IModClass capability;
    private BasicClasses userClass;
    private int satBgPos;
    private float manaAlpha;
    private int manaBgPos;

    public ClassOverlay(Minecraft minecraft, float manaAlpha) {
        this.client = minecraft;
        try {
            this.capability = this.client.player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading Capability"));
            this.manaAlpha = manaAlpha;

        } catch (Exception ignore) {
        }
    }

    public void setManaAlpha(float manaAlpha) {
        this.manaAlpha = manaAlpha;
    }

    public void render() {
        if (capability == null) return;

        this.screenHeight = client.getMainWindow().getScaledHeight();
        this.screenWidth = client.getMainWindow().getScaledWidth();

        try {
            if (!capability.getBasicClass().equals(BasicClasses.NONE)) {
                //Render Class Icon
            }
            renderHPBarBackground();
            renderHPBar();
            renderHPNum();

            renderSaturationBarBackground();
            renderSaturationBar();

            renderManaBarBackground();
            renderManaBar();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void renderManaBar() {
        final float mana = capability.getCurrentMana();
        final float maxMana = capability.getMaxMana();
        final float manaPercent = 100 / maxMana * mana;

        final int w = getManaBarWidth(manaPercent);
        final int h = 8;
        final int x = this.manaBgPos + 1;
        final int y = this.screenHeight - h - 42;
        final float tw = 200.0F / 100.0F * manaPercent;
        final int th = 20;

        this.client.getTextureManager().bindTexture(BAR_ATLAS);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(BAR_ATLAS).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureWithColorFaintOut(x, y, 0, 0, 24, w, h, Math.round(tw), th, new Color(0, 154, 176), this.manaAlpha);
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }

    private void renderManaBarBackground() {
        final int w = 182;
        final int h = 10;
        final int x = this.screenWidth / 2 - w / 2;
        final int y = this.screenHeight - h - 41;
        final int tw = 204;
        final int th = 24;
        this.client.getTextureManager().bindTexture(BAR_ATLAS);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(BAR_ATLAS).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureFaintOut(x, y, 0, 0, 0, w, h, tw, th, this.manaAlpha);
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);

        this.manaBgPos = x;
    }

    private void renderSaturationBar() {
        final float foodLevel = client.player.getFoodStats().getFoodLevel();
        final float maxFoodLevel = 20;
        final int foodPercent = Math.round(100 / maxFoodLevel * foodLevel);

        final int w = getFoodBarWidth(foodPercent);
        final int h = 8;
        final int x = this.satBgPos + 1;
        final int y = this.screenHeight - 31 - h;
        final int tw = 200 / 100 * foodPercent;
        final int th = 20;

        this.client.getTextureManager().bindTexture(BAR_ATLAS);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(BAR_ATLAS).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureWithColor(x, y, 0, 0, 24, w, h, tw, th, new Color(115, 70, 1, 255));
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }

    private int getFoodBarWidth(float hpPercent) {
        float width = 85.0F / 100.0F;
        float result = width * hpPercent;
        return Math.round(result);
    }

    private void renderSaturationBarBackground() {
        final int w = 87;
        final int h = 10;
        final int x = this.screenWidth / 2 + 91 - w;
        final int y = this.screenHeight - 30 - h;
        final int tw = 204;
        final int th = 24;

        this.satBgPos = x;

        this.client.getTextureManager().bindTexture(BAR_ATLAS);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(BAR_ATLAS).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTexture(x, y, 0, 0, 0, w, h, tw, th);
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }

    private void renderHPNum() {
        final int hp = Math.round(client.player.getHealth());
        final int maxHp = Math.round(client.player.getMaxHealth());
        this.drawCenteredString(client.fontRenderer, hp + " /" + maxHp, this.screenWidth / 2 - 48, this.screenHeight - 39, 8553090);
    }

    private void renderHPBar() {
        final float health = client.player.getHealth();
        final float maxHealth = client.player.getMaxHealth();
        final int healthPercent = Math.round(100 / maxHealth * health);

        final int w = getHPBarWidth(healthPercent);
        final int h = 8;
        final int x = this.screenWidth / 2 - 90;
        final int y = this.screenHeight - 31 - h;
        final int tw = 200 / 100 * healthPercent;
        final int th = 20;

        this.client.getTextureManager().bindTexture(BAR_ATLAS);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(BAR_ATLAS).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTextureWithColor(x, y, 0, 0, 24, w, h, tw, th, getHPBarColor(healthPercent));
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }

    private Color getHPBarColor(int hpPercent) {
        try {
            int red;
            int green;

            if (hpPercent > 50) {
                green = 156;
                red = 156 - (156 / 100 * hpPercent);
            } else {
                red = 156;
                green = 156 / 100 * hpPercent;
            }
            return new Color(red, green, 0, 255);
        } catch (Exception ignore) {
            return new Color(0, 156, 0, 255);
        }
    }

    private int getHPBarWidth(float hpPercent) {
        float width = 84.0F / 100.0F;
        float result = width * hpPercent;
        return Math.round(result);
    }

    private int getManaBarWidth(float manaPercent) {
        float width = 180.0F / 100.0F;
        float result = width * manaPercent;
        return Math.round(result);
    }

    private void renderHPBarBackground() {
        final int w = 86;
        final int h = 10;
        final int x = this.screenWidth / 2 - w - 5;
        final int y = this.screenHeight - 30 - h;
        final int tw = 204;
        final int th = 24;

        this.client.getTextureManager().bindTexture(BAR_ATLAS);
        GlStateManager.bindTexture(this.client.textureManager.getTexture(BAR_ATLAS).getGlTextureId());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.drawTexture(x, y, 0, 0, 0, w, h, tw, th);
        GlStateManager.disableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
    }
}