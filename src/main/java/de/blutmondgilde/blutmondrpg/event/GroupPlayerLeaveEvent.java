package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class GroupPlayerLeaveEvent extends Event {

    public GroupPlayerLeaveEvent(PlayerEntity player) {
        IGroup playerCap = player.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading player group capabilities"));
        boolean changePartyMaster = false;

        if (playerCap.getPartyMaster().equals(player.getUniqueID())) {
            changePartyMaster = true;
        }

        for (UUID uuid : playerCap.getMemberList()) {
            if (uuid.equals(player.getUniqueID())) continue;

            PlayerEntity groupPlayer = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
            IGroup groupCap = groupPlayer.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading player group capabilities"));
            if (changePartyMaster) groupCap.setPartyMaster(playerCap.getMemberList().get(1));

            groupCap.removeMember(player.getUniqueID());
            CustomNetworkManager.syncPlayerGroup(BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid));
        }

        playerCap.reset(player);
    }
}
