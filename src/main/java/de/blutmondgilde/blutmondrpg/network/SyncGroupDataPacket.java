package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.enums.SharingMethod;
import de.blutmondgilde.blutmondrpg.event.GroupSyncInformationEvent;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncGroupDataPacket {
    private List<UUID> memberList;
    private SharingMethod sharingMethod;
    private UUID leader;

    public SyncGroupDataPacket(List<UUID> memberList, SharingMethod sharingMethod, UUID leader) {
        this.sharingMethod = sharingMethod;
        this.leader = leader;
        this.memberList = memberList;
    }

    public SyncGroupDataPacket(IGroup cap) {
        this.memberList = cap.getMemberList();
        this.leader = cap.getPartyMaster();
        this.sharingMethod = cap.getSharingMethod();
    }

    public static void encode(SyncGroupDataPacket msg, PacketBuffer buffer) {
        buffer.writeUniqueId(msg.leader);
        buffer.writeInt(msg.sharingMethod.getId());
        buffer.writeInt(msg.memberList.size());
        for (UUID uuid : msg.memberList) {
            buffer.writeUniqueId(uuid);
        }
    }

    public static SyncGroupDataPacket decode(PacketBuffer buffer) {
        UUID leader = buffer.readUniqueId();
        SharingMethod sharingMethod = SharingMethod.getById(buffer.readInt());
        List<UUID> memberList = new ArrayList<>();
        int members = buffer.readInt();
        while (members > 0) {
            memberList.add(buffer.readUniqueId());
            members--;
        }

        return new SyncGroupDataPacket(memberList, sharingMethod, leader);
    }

    public static class Handler {
        public static void handle(final SyncGroupDataPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> {
                        MinecraftForge.EVENT_BUS.post(new GroupSyncInformationEvent(msg.memberList, msg.sharingMethod, msg.leader));
                    }
            );
            context.get().setPacketHandled(true);
        }
    }
}
