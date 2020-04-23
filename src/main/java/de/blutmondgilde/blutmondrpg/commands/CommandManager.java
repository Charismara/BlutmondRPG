package de.blutmondgilde.blutmondrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.blutmondgilde.blutmondrpg.event.*;
import de.blutmondgilde.blutmondrpg.handler.GroupHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;

public class CommandManager {
    public static void init(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("brpg")
                        .then(CommandManager.CommandReset.register())
                        .then(CommandGroup.register())
        );
    }

    public static class CommandReset {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("reset")
                    .then(Commands.literal("class")
                            .requires(cs -> cs.hasPermissionLevel(0))
                            .executes(
                                    cs -> {
                                        MinecraftForge.EVENT_BUS.post(new ChangePlayerClassEvent(cs.getSource().asPlayer(), -1));
                                        cs.getSource().sendFeedback(new TranslationTextComponent("blutmondrpg.command.reset.class.feedback"), true);
                                        return 1;
                                    })
                    );
        }
    }

    public static class CommandGroup {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("group")
                    .then(Commands.literal("invite").then(Commands.argument("Player", EntityArgument.players()).requires(cs -> cs.hasPermissionLevel(0)).executes(
                            cs -> {
                                if (cs.getSource().asPlayer().getUniqueID().equals(EntityArgument.getPlayer(cs, "Player").getUniqueID())) {
                                    cs.getSource().sendFeedback(new TranslationTextComponent("blutmondrpg.command.group.invite.self"), false);
                                    return 0;
                                } else {
                                    MinecraftForge.EVENT_BUS.post(new GroupInvitePlayerEvent(cs.getSource().asPlayer(), EntityArgument.getPlayer(cs, "Player")));
                                    return 1;
                                }
                            }
                    )))
                    .then(Commands.literal("kick").then(Commands.argument("Player", EntityArgument.players()).requires(cs -> cs.hasPermissionLevel(0)).executes(
                            cs -> {
                                MinecraftForge.EVENT_BUS.post(new GroupKickPlayerEvent(cs.getSource().asPlayer(), EntityArgument.getPlayer(cs, "Player")));
                                GroupHandler.sendInfosToPlayers(cs.getSource().asPlayer());
                                return 1;
                            }
                    )))
                    .then(Commands.literal("list").requires(cs -> cs.hasPermissionLevel(0)).executes(
                            cs -> {
                                MinecraftForge.EVENT_BUS.post(new GroupInformationEvent(cs.getSource().asPlayer()));
                                return 1;
                            }
                    ))
                    .then(Commands.literal("answer").requires(cs -> cs.hasPermissionLevel(0)).then(Commands.argument("Invitor", EntityArgument.players()).then(Commands.argument("Accepted", BoolArgumentType.bool()).executes(
                            cs -> {
                                MinecraftForge.EVENT_BUS.post(new GroupInviteAnswerEvent(EntityArgument.getPlayer(cs, "Invitor"), cs.getSource().asPlayer(), BoolArgumentType.getBool(cs, "Accepted")));
                                GroupHandler.sendInfosToPlayers(cs.getSource().asPlayer());
                                return 1;
                            }
                    ))))
                    .then(Commands.literal("leave").requires(cs -> cs.hasPermissionLevel(0)).executes(
                            cs -> {
                                MinecraftForge.EVENT_BUS.post(new GroupPlayerLeaveEvent(cs.getSource().asPlayer()));
                                GroupHandler.sendInfosToPlayers(cs.getSource().asPlayer());
                                return 1;
                            }
                    ));
        }
    }
}
