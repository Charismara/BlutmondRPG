package de.blutmondgilde.blutmondrpg.capabilities.mobscaling;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MobScalingProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IMobScaling.class)
    public static final Capability<IMobScaling> MOB_SCLAING_CAPABILITY = null;

    private LazyOptional<IMobScaling> instance = LazyOptional.of(MOB_SCLAING_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == MOB_SCLAING_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return MOB_SCLAING_CAPABILITY.getStorage().writeNBT(MOB_SCLAING_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MOB_SCLAING_CAPABILITY.getStorage().readNBT(MOB_SCLAING_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
