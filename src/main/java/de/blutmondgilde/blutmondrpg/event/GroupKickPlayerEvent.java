package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.ResetGroupInfoPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class GroupKickPlayerEvent extends Event {
    /**
     * Server-Side event which informs player about the kicked player
     *
     * @param sender Group master
     * @param target Kicked Group member
     */

    public GroupKickPlayerEvent(PlayerEntity sender, PlayerEntity target) {
        final PlayerEntity master = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(sender.getUniqueID());
        IGroup masterCap = master.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load group master capability"));

        final PlayerEntity kickedPlayer = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(target.getUniqueID());
        IGroup targetCap = kickedPlayer.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load group target capability"));


        masterCap.removeMember(kickedPlayer.getUniqueID());
        CustomNetworkManager.syncPlayerGroup(master);

        targetCap.reset(kickedPlayer);
        CustomNetworkManager.syncPlayerGroup(kickedPlayer);
        CustomNetworkManager.sendToPlayer(new ResetGroupInfoPacket(), kickedPlayer);

        for (UUID uuid : masterCap.getMemberList()) {
            final PlayerEntity member = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
            IGroup memberCap = member.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load member capability"));
            memberCap.setMemberList(masterCap.getMemberList());
            memberCap.setPartyMaster(masterCap.getPartyMaster());
            memberCap.setSharingMethod(masterCap.getSharingMethod());

            CustomNetworkManager.syncPlayerGroup(member);
            CustomNetworkManager.removeGroupInfo(member, kickedPlayer.getUniqueID());
        }

        ITextComponent message;
        message = kickedPlayer.getDisplayName();
        message.appendText(" ");
        message.appendSibling(new TranslationTextComponent("blutmondrpg.command.group.kicked.successful"));
        master.sendMessage(message);

        message = new TranslationTextComponent("blutmondrpg.command.group.kicked");
        kickedPlayer.sendMessage(message);
    }
}
