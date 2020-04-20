package de.blutmondgilde.blutmondrpg.capabilities.party;

import de.blutmondgilde.blutmondrpg.enums.SharingMethod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.UUID;

public class GroupStorage implements Capability.IStorage<IGroup> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IGroup> capability, IGroup instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        tag.putUniqueId("partyMaster", instance.getPartyMaster());
        tag.putInt("partySharingId", instance.getSharingMethod().getId());
        tag.putInt("partyMemberCount", instance.getMemberList().size());
        int memberCount = 1;

        for (UUID uuid : instance.getMemberList()) {
            tag.putUniqueId("partyMember" + memberCount++, uuid);
        }

        return tag;
    }

    @Override
    public void readNBT(Capability<IGroup> capability, IGroup instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setPartyMaster(tag.getUniqueId("partyMaster"));
        instance.setSharingMethod(SharingMethod.getById(tag.getInt("partySharingId")));
        int memberCount = tag.getInt("partyMemberCount");
        while (memberCount > 0) {
            instance.addMember(tag.getUniqueId("partyMember" + memberCount--));
        }
    }
}
