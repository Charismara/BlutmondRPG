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
    public void chooseClassOnFirstLogIn(final PlayerEvent.PlayerLoggedInEvent e) {
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

    @SubscribeEvent
    public void syncOnLogIn(final PlayerEvent.PlayerLoggedInEvent e) {
        CustomNetworkManager.syncPlayerClass(e.getPlayer());
    }

    @SubscribeEvent
    public void reAttachOldDataOnRespawn(final PlayerEvent.Clone e) {
        if (!e.isWasDeath()) return;
        final PlayerEntity oldEntity = e.getOriginal();
        final IModClass oldCapability = oldEntity.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading the original Capabilities of " + oldEntity.getDisplayName().getString()));
        final PlayerEntity newEntity = e.getPlayer();
        final IModClass newCapability = newEntity.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading the new Capabilities of " + newEntity.getDisplayName().getString()));

        newCapability.setBasicClass(oldCapability.getBasicClass());
        newCapability.setMaxHP(oldCapability.getMaxHP());
        newCapability.setMaxMana(oldCapability.getMaxMana());
        newCapability.setClassLevel(oldCapability.getClassLevel());
        newCapability.setClassExp(oldCapability.getClassExp());
        newCapability.setCurrentMana(0);
        newEntity.setHealth(newCapability.getMaxHP());

        CustomNetworkManager.syncPlayerClass(newEntity);
    }
}
