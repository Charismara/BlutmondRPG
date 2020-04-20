package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.overlay.ClassOverlay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
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
        } catch (Exception ignore) {
            manaDisplayAlpha = 1.0F;
        }
        IModClass cap = Minecraft.getInstance().player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Can't get local player capability"));
        ClassOverlay classOverlay = new ClassOverlay(Minecraft.getInstance(), manaDisplayAlpha, cap);
        classOverlay.render();
    }
}
