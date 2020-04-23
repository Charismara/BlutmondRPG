package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.OpenChooseGuiPacket;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler {
    private static final UUID HP_MODIFIER_ID = UUID.fromString("2fcc35d1-3f05-40f0-8aa5-2931d714e8b6");
    private static final UUID MELEE_MODIFIER_ID = UUID.fromString("cdff4b2e-a85e-4a91-8ed1-97d1851a509c");
    public static Map<UUID, Float> GroupMaxHP = new HashMap<>();
    private static Map<UUID, String> GroupName = new HashMap<>();
    private static Map<UUID, Float> GroupHP = new HashMap<>();

    @SubscribeEvent
    public void chooseClassOnFirstLogIn(final PlayerEvent.PlayerLoggedInEvent e) {
        final IModClass cap = e.getPlayer().getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("ModClass Capability is invalid!"));
        if (cap.getBasicClass().equals(BasicClasses.NONE)) {
            CustomNetworkManager.sendToPlayer(new OpenChooseGuiPacket(), e.getPlayer());
        }
    }

    private static void applyHP(PlayerEntity player, double value) {
        AttributeModifier modifier = new AttributeModifier(HP_MODIFIER_ID, Ref.MOD_ID + ".health", value, AttributeModifier.Operation.ADDITION);
        IAttributeInstance attribute = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        try {
            attribute.removeModifier(HP_MODIFIER_ID);
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

    public static void applyDmg(PlayerEntity player, double value) {
        AttributeModifier modifier = new AttributeModifier(MELEE_MODIFIER_ID, Ref.MOD_ID + ".damage.melee", value, AttributeModifier.Operation.ADDITION);
        IAttributeInstance attribute = player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        try {
            attribute.removeModifier(MELEE_MODIFIER_ID);
            attribute.applyModifier(modifier);
        } catch (Exception ignore) {

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

    public static float getGroupMemberMaxHP(final UUID uuid) {
        return GroupMaxHP.get(uuid);
    }

    public static float getGroupMemberHP(final UUID uuid) {
        return GroupHP.get(uuid);
    }

    public static String getGroupMemberNames(final UUID uuid) {
        return GroupName.get(uuid);
    }

    public static void addPlayerInformation(final UUID uuid, final String name, final float hp, final float maxHp) {
        if (uuid.equals(Minecraft.getInstance().player.getUniqueID())) return;

        if (GroupMaxHP.containsKey(uuid)) {
            removePlayerInformation(uuid);
        }

        GroupMaxHP.put(uuid, maxHp);
        GroupHP.put(uuid, hp);
        GroupName.put(uuid, name);
    }

    public static void removePlayerInformation(final UUID uuid) {
        if (uuid.equals(Minecraft.getInstance().player.getUniqueID())) return;
        try {
            GroupMaxHP.remove(uuid);
            GroupHP.remove(uuid);
            GroupName.remove(uuid);
        } catch (Exception ignore) {

        }
    }

    public static void resetPlayerInformation() {
        GroupMaxHP = new HashMap<>();
        GroupHP = new HashMap<>();
        GroupName = new HashMap<>();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientLeaveServer(final ClientPlayerNetworkEvent.LoggedOutEvent e) {
        resetPlayerInformation();
    }
}
