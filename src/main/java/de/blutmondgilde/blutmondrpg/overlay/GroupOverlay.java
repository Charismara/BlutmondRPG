package de.blutmondgilde.blutmondrpg.overlay;

import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class GroupOverlay extends AbstractGui {
    private final Minecraft client;
    private final IGroup cap;

    public GroupOverlay(Minecraft client, IGroup cap) {
        this.client = client;
        this.cap = cap;
    }

    public void render() {
        if (this.cap == null) return;
        int size = cap.getMemberList().size();
        int info = PlayerHandler.GroupMaxHP.size();
        Ref.LOGGER.debug("Group Members: " + size + ", Group Infos: " + info);
    }
}
