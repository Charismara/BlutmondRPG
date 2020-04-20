package de.blutmondgilde.blutmondrpg.capabilities.party;

import de.blutmondgilde.blutmondrpg.enums.SharingMethod;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.UUID;

public interface IGroup {
    void setPartyMaster(UUID partyMaster);

    UUID getPartyMaster();

    List<UUID> getMemberList();

    SharingMethod getSharingMethod();

    void setMemberList(List<UUID> memberList);

    void setSharingMethod(SharingMethod sharingMethod);

    void addMember(PlayerEntity playerEntity);

    void addMember(UUID uuid);

    void removeMember(UUID uuid);

    void reset();

    void reset(PlayerEntity player);
}
