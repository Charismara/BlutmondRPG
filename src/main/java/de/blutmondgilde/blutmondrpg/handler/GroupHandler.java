package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.event.GroupPlayerLeaveEvent;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.util.CapabilityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class GroupHandler {
    @SubscribeEvent
    public void onPlayerHeal(final LivingHealEvent e) {
        final LivingEntity entity = e.getEntityLiving();
        if (!isPlayer(entity)) return;
        sendInfosToPlayers((PlayerEntity) entity);
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
        if (entity instanceof FakePlayer) return false;
        return true;
    }

    @SubscribeEvent
    public void onPlayerLogOut(final PlayerEvent.PlayerLoggedOutEvent e) {
        if (!isPlayer(e.getEntity())) return;
        final PlayerEntity player = e.getPlayer();
        IGroup cap = CapabilityHelper.getGroupCapability(player);

        for (UUID uuid : cap.getMemberList()) {
            CustomNetworkManager.removeGroupInfo(BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid), player.getUniqueID());
        }

        MinecraftForge.EVENT_BUS.post(new GroupPlayerLeaveEvent(player));
    }
}
