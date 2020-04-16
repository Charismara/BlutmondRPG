package de.blutmondgilde.blutmondrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.blutmondgilde.blutmondrpg.event.ChangePlayerClassEvent;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;

public class CommandManager {
    public static void init(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("brpg")
                        .then(CommandManager.CommandReset.register())
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
                                        cs.getSource().sendFeedback(new TranslationTextComponent("blutmondrpg.command.reset.class"), true);
                                        return 1;
                                    })
                    );
        }
    }
}
