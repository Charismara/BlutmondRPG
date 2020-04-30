package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.overlay.ClassOverlay;
import de.blutmondgilde.blutmondrpg.overlay.GroupOverlay;
import de.blutmondgilde.blutmondrpg.util.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    @OnlyIn(Dist.CLIENT)
    private float manaDisplayAlpha;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void cancelVanillaHUD(final RenderGameOverlayEvent.Pre e) {
        switch (e.getType()) {
            case FOOD:
            case HEALTH:
                e.setCanceled(true);
                break;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderClassOverlay(final RenderGameOverlayEvent.Post e) {
        if (!e.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) return;
        final Minecraft minecraft = Minecraft.getInstance();

        try {
            final IModClass capability = minecraft.player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while render overlay"));
            final float mana = capability.getCurrentMana();
            final float maxMana = capability.getMaxMana();

            if (mana < maxMana) {
                manaDisplayAlpha = 1.0F;
            } else {
                if (manaDisplayAlpha > 0.0F) {
                    manaDisplayAlpha -= 0.005F;
                } else {
                    manaDisplayAlpha = 0.0F;
                }
            }

            IModClass cap = Minecraft.getInstance().player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Can't get local player capability"));
            ClassOverlay classOverlay = new ClassOverlay(Minecraft.getInstance(), manaDisplayAlpha, cap);
            classOverlay.render();
        } catch (Exception ignore) {
            manaDisplayAlpha = 1.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderGroupOverlay(final RenderGameOverlayEvent.Post e) {
        if (!e.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) return;
        final Minecraft minecraft = Minecraft.getInstance();

        try {
            IGroup cap = CapabilityHelper.getGroupCapability(minecraft.player);
            GroupOverlay overlay = new GroupOverlay(minecraft, cap);
            overlay.render();
        } catch (Exception ignore) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderInventoryStats(final GuiScreenEvent.BackgroundDrawnEvent e) {
        if (!(e.getGui().getTitle().equals(new TranslationTextComponent("container.crafting")))) return;
        renderInventoryStatsBackground(e);
    }

    @OnlyIn(Dist.CLIENT)
    private void renderInventoryStatsBackground(final GuiScreenEvent.BackgroundDrawnEvent e) {
        final Screen screen = e.getGui();
        final Minecraft client = screen.getMinecraft();
        final int color = 11382189;
        final int fontHeight = client.fontRenderer.FONT_HEIGHT;
        final int y = Math.round(screen.height / 2F - fontHeight * 2.5F);
        final int x = 10;
        final IModClass cap = client.player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exception while loading player capability"));
        final String hp, mana, melee, magic, bow;
        hp = (new TranslationTextComponent("blutmondrpg.gui.stats.hp")).getString();
        mana = (new TranslationTextComponent("blutmondrpg.gui.stats.mana")).getString();
        melee = (new TranslationTextComponent("blutmondrpg.gui.stats.melee")).getString();
        magic = (new TranslationTextComponent("blutmondrpg.gui.stats.magic")).getString();
        bow = (new TranslationTextComponent("blutmondrpg.gui.stats.bow")).getString();


        screen.drawString(client.fontRenderer, hp + ": " + cap.getMaxHP(), x, y, color);
        screen.drawString(client.fontRenderer, mana + ": " + cap.getMaxMana(), x, y + fontHeight + 2, color);
        screen.drawString(client.fontRenderer, melee + ": " + cap.getMeleeDmg(), x, y + fontHeight * 2 + 2, color);
        screen.drawString(client.fontRenderer, magic + ": " + cap.getMagicDmg(), x, y + fontHeight * 3 + 2, color);
        screen.drawString(client.fontRenderer, bow + ": " + cap.getBowDmg(), x, y + fontHeight * 4 + 2, color);
    }
}

