package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.Event;

public class GroupInviteAnswerEvent extends Event {
    public GroupInviteAnswerEvent(PlayerEntity groupMember, PlayerEntity target, boolean accepted) {
        if (!BlutmondRPG.isPendingGroupRequest(groupMember, target)) return;

        ITextComponent message, subMessage;

        if (!accepted) {
            message = new TranslationTextComponent("blutmondrpg.command.group.answer.no.target");
            message.getStyle().setColor(TextFormatting.DARK_RED);
            target.sendMessage(message);
            message = target.getDisplayName();
            message.appendText(" ");
            message.appendSibling(new TranslationTextComponent("blutmondrpg.command.group.answer.no.sender"));
            message.getStyle().setColor(TextFormatting.DARK_RED);
            groupMember.sendMessage(message);
        } else {
            IGroup groupMemberCap = groupMember.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load invitor group capability"));
            IGroup targetCap = target.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load target group capability"));
            targetCap.setPartyMaster(groupMemberCap.getPartyMaster());
            targetCap.setSharingMethod(groupMemberCap.getSharingMethod());
            groupMemberCap.addMember(target);
            targetCap.setMemberList(groupMemberCap.getMemberList());

            message = new TranslationTextComponent("blutmondrpg.command.group.answer.yes.target");
            message.getStyle().setColor(TextFormatting.DARK_GREEN);
            target.sendMessage(message);

            subMessage = target.getDisplayName();
            subMessage.getStyle().setColor(TextFormatting.DARK_GREEN);
            message = subMessage;
            message.appendText(" ");
            message.appendSibling(new TranslationTextComponent("blutmondrpg.command.group.answer.yes.sender"));
            groupMember.sendMessage(message);
        }
        BlutmondRPG.removePendingGroupRequest(groupMember, target);
    }
}
