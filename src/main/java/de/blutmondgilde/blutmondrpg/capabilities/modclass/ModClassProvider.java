package de.blutmondgilde.blutmondrpg.capabilities.modclass;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModClassProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IModClass.class)
    public static final Capability<IModClass> MOD_CLASS_CAPABILITY = null;

    private LazyOptional<IModClass> instance = LazyOptional.of(MOD_CLASS_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == MOD_CLASS_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return MOD_CLASS_CAPABILITY.getStorage().writeNBT(MOD_CLASS_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MOD_CLASS_CAPABILITY.getStorage().readNBT(MOD_CLASS_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
