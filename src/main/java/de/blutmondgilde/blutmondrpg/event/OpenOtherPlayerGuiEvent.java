package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.gui.OtherPlayerGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.Event;

public class OpenOtherPlayerGuiEvent extends Event {
    public OpenOtherPlayerGuiEvent(final String name, final BasicClasses playerClass, final int level, final float hp, final float maxHP) {
        Minecraft.getInstance().displayGuiScreen(new OtherPlayerGUI(name, playerClass, level, hp, maxHP, Minecraft.getInstance()));
    }
}
