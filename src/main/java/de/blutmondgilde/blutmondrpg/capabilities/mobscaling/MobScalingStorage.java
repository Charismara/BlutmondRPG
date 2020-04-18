package de.blutmondgilde.blutmondrpg.capabilities.mobscaling;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MobScalingStorage implements Capability.IStorage<IMobScaling> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMobScaling> capability, IMobScaling instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        tag.putUniqueId("scaleClosestPlayer", instance.getClosestPlayer().getUniqueID());

        return tag;
    }

    @Override
    public void readNBT(Capability<IMobScaling> capability, IMobScaling instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;

        instance.setClosestPlayer(BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(((CompoundNBT) nbt).getUniqueId("scaleClosestPlayer")));

    }
}
