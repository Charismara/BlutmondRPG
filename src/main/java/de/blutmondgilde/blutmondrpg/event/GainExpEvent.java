package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class GainExpEvent extends Event {
    public GainExpEvent(PlayerEntity playerEntity, double anoumt) {


        Ref.LOGGER.debug("Player " + playerEntity.getName().getString() + " got " + anoumt + " Exp.");
    }
}
