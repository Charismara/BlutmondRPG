package de.blutmondgilde.blutmondrpg.capabilities.party;

import de.blutmondgilde.blutmondrpg.enums.SharingMethod;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group implements IGroup {
    private List<UUID> memberList;
    private SharingMethod sharingMethod;
    private UUID partyMaster;

    public Group() {
        this.memberList = new ArrayList<>();
        this.sharingMethod = SharingMethod.Fair;
        this.partyMaster = Ref.FAKE_PLAYER.getId();
    }

    public void setPartyMaster(UUID partyMaster) {
        this.partyMaster = partyMaster;
    }

    public UUID getPartyMaster() {
        return partyMaster;
    }

    public List<UUID> getMemberList() {
        return memberList;
    }

    public SharingMethod getSharingMethod() {
        return sharingMethod;
    }

    public void setMemberList(List<UUID> memberList) {
        this.memberList = memberList;
    }

    public void setSharingMethod(SharingMethod sharingMethod) {
        this.sharingMethod = sharingMethod;
    }

    public void addMember(PlayerEntity playerEntity) {
        this.memberList.add(playerEntity.getUniqueID());
    }

    public void addMember(UUID uuid) {
        this.memberList.add(uuid);
    }

    public void removeMember(UUID uuid) {
        this.memberList.remove(uuid);
    }

    public void reset() {
        this.memberList = new ArrayList<>();
        this.sharingMethod = SharingMethod.Fair;
        this.partyMaster = Ref.FAKE_PLAYER.getId();
    }

    public void reset(PlayerEntity player) {
        reset();
        this.partyMaster = player.getUniqueID();
        this.memberList.add(this.partyMaster);
        this.memberList.remove(Ref.FAKE_PLAYER.getId());
    }
}
