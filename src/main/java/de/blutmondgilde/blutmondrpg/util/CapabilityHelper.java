package de.blutmondgilde.blutmondrpg.util;

import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.IMobScaling;
import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.MobScalingProvider;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import net.minecraft.entity.Entity;

public class CapabilityHelper {

    public static IGroup getGroupCapability(final Entity entity) {
        return getGroupCapability(entity, "Exception while loading Group Capability");
    }

    public static IGroup getGroupCapability(final Entity entity, final String exceptionMessage) {
        return entity.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException(exceptionMessage));
    }

    public static IMobScaling getMobScalingCapability(final Entity entity, final String exceptionMessage) {
        return entity.getCapability(MobScalingProvider.MOB_SCLAING_CAPABILITY).orElseThrow(() -> new IllegalStateException(exceptionMessage));
    }

    public static IModClass getClassCapability(final Entity entity, final String exceptionMessage) {
        return entity.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException(exceptionMessage));
    }
}
