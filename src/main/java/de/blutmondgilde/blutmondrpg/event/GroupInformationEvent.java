package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class GroupInformationEvent extends Event {
    public GroupInformationEvent(PlayerEntity sender) {
        final IGroup groupCap = sender.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Can't load group information from " + sender.getClass()));


        ITextComponent message = new StringTextComponent("----- ");
        message.appendSibling(new TranslationTextComponent("blutmondrpg.command.group.information.header"));
        message.appendText(" -----");

        sender.sendMessage(message); //----- Information -----

        message = new TranslationTextComponent("blutmondrpg.command.group.information.partyleader");
        message.appendText(": ");
        message.appendText(BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(groupCap.getPartyMaster()).getName().getString());

        sender.sendMessage(message); // Partyleader: %player_name%

        message = new TranslationTextComponent("blutmondrpg.command.group.information.member");
        message.appendText(":");

        sender.sendMessage(message);

        for (UUID uuid : groupCap.getMemberList()) {
            PlayerEntity player = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);

            if (player == null) return;

            message = new StringTextComponent(player.getName().getString());

            sender.sendMessage(message);
        }
    }
}
