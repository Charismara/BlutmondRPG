package de.blutmondgilde.blutmondrpg.capabilities;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassStorage;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CustomCapabilityManager {
    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IModClass.class, new ModClassStorage(), ModClass::new);
    }

    @SubscribeEvent
    public void onAttachClassCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof PlayerEntity)) return;

        e.addCapability(new ResourceLocation(Ref.MOD_ID, "modclassdata"), new ModClassProvider());
        PlayerEntity player = (PlayerEntity) e.getObject();

        Ref.LOGGER.debug("Added Class Information to Player: " + e.getObject().getUniqueID());
    }
}
