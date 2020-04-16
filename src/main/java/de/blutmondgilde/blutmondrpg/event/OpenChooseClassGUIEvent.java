package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.gui.ChooseClassGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.Event;

public class OpenChooseClassGUIEvent extends Event {
    public OpenChooseClassGUIEvent() {
        Minecraft.getInstance().displayGuiScreen(new ChooseClassGUI());
    }
}
