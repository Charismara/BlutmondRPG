package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.eventbus.api.Event;

public class GroupInvitePlayerEvent extends Event {
    public GroupInvitePlayerEvent(PlayerEntity sender, PlayerEntity target) {
        BlutmondRPG.addPendingGroupRequest(sender, target);
        ITextComponent message, subMessage;
        message = new TranslationTextComponent("blutmondrpg.command.group.invite.request");
        message.appendSibling(sender.getDisplayName());
        target.sendMessage(message);

        subMessage = new TranslationTextComponent("blutmondrpg.command.group.invite.request.accept");
        subMessage.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("blutmondrpg.command.group.invite.request.accept.hover")));
        subMessage.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/brpg group answer " + sender.getName().getString() + " true"));
        subMessage.getStyle().setColor(TextFormatting.DARK_GREEN);
        message = subMessage;
        message.appendText("  ");
        subMessage = new TranslationTextComponent("blutmondrpg.command.group.invite.request.deny");
        subMessage.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("blutmondrpg.command.group.invite.request.deny.hover")));
        subMessage.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/brpg group answer " + sender.getName().getString() + " false"));
        subMessage.getStyle().setColor(TextFormatting.DARK_RED);
        message.appendSibling(subMessage);

        target.sendMessage(message);
    }
}
