package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.OpenChooseGuiPacket;
import de.blutmondgilde.blutmondrpg.network.SyncClassDataPacket;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class ChangePlayerClassEvent extends Event {
    public ChangePlayerClassEvent(PlayerEntity player, int classId) {
        IModClass cap = player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading Capability in ChangePlayerClassEvent"));
        cap.setBasicClass(classId);

        Ref.LOGGER.debug("Running ChangePlayerClassEvent");

        CustomNetworkManager.sendToPlayer(new SyncClassDataPacket(player, cap, true), player);
        if (classId != -1) return;
        CustomNetworkManager.sendToPlayer(new OpenChooseGuiPacket(), player);
    }
}
