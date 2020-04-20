package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.enums.SharingMethod;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;
import java.util.UUID;

public class GroupSyncInformationEvent extends Event {

    /**
     * This Event applies the given Server Information to the
     * Client gettable player.
     * <p>
     * This Event only runs on the Client side
     *
     * @param memberList    List of Members
     * @param sharingMethod Method of Exp and Loot sharing
     * @param leader        Leader of the Group
     */
    public GroupSyncInformationEvent(List<UUID> memberList, SharingMethod sharingMethod, UUID leader) {
        IGroup cap = Minecraft.getInstance().player.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load group capability from Player"));
        cap.setMemberList(memberList);
        cap.setSharingMethod(sharingMethod);
        cap.setPartyMaster(leader);

        Ref.LOGGER.debug("Group Information Synced");
    }
}
