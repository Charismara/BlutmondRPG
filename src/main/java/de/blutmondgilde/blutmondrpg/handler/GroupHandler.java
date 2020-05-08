package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.event.GroupPlayerLeaveEvent;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.OpenOtherPlayerGuiPacket;
import de.blutmondgilde.blutmondrpg.util.CapabilityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class GroupHandler {
    @SubscribeEvent
    public void onPlayerHeal(final LivingHealEvent e) {
        final LivingEntity entity = e.getEntityLiving();
        if (!isPlayer(entity)) return;
        entity.setHealth(entity.getHealth() + e.getAmount());
        sendInfosToPlayers((PlayerEntity) entity);
        entity.setHealth(entity.getHealth() - e.getAmount());
    }

    @SubscribeEvent
    public void onPlayerHurt(final LivingDamageEvent e) {
        final LivingEntity entity = e.getEntityLiving();
        if (!isPlayer(entity)) return;
        sendInfosToPlayers((PlayerEntity) entity);
    }

    public static void sendInfosToPlayers(final PlayerEntity sender) {
        final IGroup cap = CapabilityHelper.getGroupCapability(sender);
        for (UUID uuid : cap.getMemberList()) {
            final PlayerEntity receiver = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
            CustomNetworkManager.sendGroupMemberInfo(receiver, sender.getUniqueID());
        }
    }

    private static boolean isPlayer(Entity entity) {
        if (!(entity instanceof PlayerEntity)) return false;
        return !(entity instanceof FakePlayer);
    }

    @SubscribeEvent
    public void onPlayerLogOut(final PlayerEvent.PlayerLoggedOutEvent e) {
        if (!isPlayer(e.getEntity())) return;
        final PlayerEntity player = e.getPlayer();
        IGroup cap = CapabilityHelper.getGroupCapability(player);

        for (UUID uuid : cap.getMemberList()) {
            try {
                CustomNetworkManager.removeGroupInfo(BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid), player.getUniqueID());
            } catch (Exception ignore) {
            }

        }

        MinecraftForge.EVENT_BUS.post(new GroupPlayerLeaveEvent(player));
    }

    @SubscribeEvent
    public void onPlayerRClickPlayer(final PlayerInteractEvent.EntityInteractSpecific e) {
        if (e.getWorld().isRemote()) return;
        if (!isPlayer(e.getEntity())) return;
        final PlayerEntity target = (PlayerEntity) e.getTarget();
        final PlayerEntity clicker = e.getPlayer();
        final IModClass targetCap = CapabilityHelper.getClassCapability(target, "Exception while loading Capabilities from clicked Player");

        CustomNetworkManager.sendToPlayer(
                new OpenOtherPlayerGuiPacket(
                        target.getDisplayName().getString(),
                        targetCap.getBasicClass(),
                        targetCap.getClassLevel().getId(),
                        target.getHealth(),
                        target.getMaxHealth()
                ),
                clicker
        );
    }
}
