package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.network.OpenChooseGuiPacket;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerHandler {
    @SubscribeEvent
    public void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent e) {
        final IModClass cap = e.getPlayer().getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("ModClass Capability is invalid!"));
        if (cap.getBasicClass().equals(BasicClasses.NONE)) {
            CustomNetworkManager.sendToPlayer(new OpenChooseGuiPacket(), e.getPlayer());
        }
    }
}
