package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.OpenChooseGuiPacket;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class PlayerHandler {
    private static final UUID MODIFIER_ID = UUID.fromString("2fcc35d1-3f05-40f0-8aa5-2931d714e8b6");

    @SubscribeEvent
    public void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent e) {
        final IModClass cap = e.getPlayer().getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("ModClass Capability is invalid!"));
        if (cap.getBasicClass().equals(BasicClasses.NONE)) {
            CustomNetworkManager.sendToPlayer(new OpenChooseGuiPacket(), e.getPlayer());
        }
    }

    private static void applyHP(PlayerEntity player, double value) {
        AttributeModifier modifier = new AttributeModifier(MODIFIER_ID, Ref.MOD_ID + ".health", value, AttributeModifier.Operation.ADDITION);
        IAttributeInstance attribute = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        try {
            attribute.removeModifier(MODIFIER_ID);
            attribute.applyModifier(modifier);
        } catch (Exception ignore) {
        }

        if (player.getHealth() > attribute.getBaseValue() + value) {
            player.setHealth((float) (attribute.getBaseValue() + value));
        }
    }

    public static void applyHP(PlayerEntity player, double value, boolean heal) {
        applyHP(player, value);
        if (heal) {
            player.setHealth(player.getMaxHealth());
        }
    }
}
