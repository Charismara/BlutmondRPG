package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.ResetGroupInfoPacket;
import de.blutmondgilde.blutmondrpg.util.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class GroupPlayerLeaveEvent extends Event {
    /**
     * Server-Side event which informs group member that a player left the group
     *
     * @param player Player which left the Group
     */
    public GroupPlayerLeaveEvent(PlayerEntity player) {
        IGroup playerCap = CapabilityHelper.getGroupCapability(player);
        boolean changePartyMaster = false;

        if (playerCap.getPartyMaster().equals(player.getUniqueID())) {
            changePartyMaster = true;
        }

        for (UUID uuid : playerCap.getMemberList()) {
            if (uuid.equals(player.getUniqueID())) continue;

            final PlayerEntity groupPlayer = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
            IGroup groupCap = CapabilityHelper.getGroupCapability(groupPlayer);
            if (changePartyMaster) groupCap.setPartyMaster(playerCap.getMemberList().get(1));

            groupCap.removeMember(player.getUniqueID());
            CustomNetworkManager.syncPlayerGroup(groupPlayer);
            CustomNetworkManager.removeGroupInfo(groupPlayer, player.getUniqueID());

        }

        playerCap.reset(player);
        CustomNetworkManager.sendToPlayer(new ResetGroupInfoPacket(), player);
    }
}
