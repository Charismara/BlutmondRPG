package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class GroupKickPlayerEvent extends Event {
    public GroupKickPlayerEvent(PlayerEntity sender, PlayerEntity target) {
        final PlayerEntity master = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(sender.getUniqueID());
        IGroup masterCap = master.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load group master capability"));

        final PlayerEntity salve = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(target.getUniqueID());
        IGroup targetCap = salve.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load group target capability"));


        masterCap.removeMember(salve.getUniqueID());
        CustomNetworkManager.syncPlayerGroup(master);

        targetCap.reset(salve);
        CustomNetworkManager.syncPlayerGroup(salve);

        for (UUID uuid : masterCap.getMemberList()) {
            final PlayerEntity member = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
            IGroup memberCap = member.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load member capability"));
            memberCap.setMemberList(masterCap.getMemberList());
            memberCap.setPartyMaster(masterCap.getPartyMaster());
            memberCap.setSharingMethod(masterCap.getSharingMethod());

            CustomNetworkManager.syncPlayerGroup(member);
        }

        ITextComponent message;
        message = salve.getDisplayName();
        message.appendText(" ");
        message.appendSibling(new TranslationTextComponent("blutmondrpg.command.group.kicked.successful"));
        master.sendMessage(message);

        message = new TranslationTextComponent("blutmondrpg.command.group.kicked");
        salve.sendMessage(message);
    }
}
