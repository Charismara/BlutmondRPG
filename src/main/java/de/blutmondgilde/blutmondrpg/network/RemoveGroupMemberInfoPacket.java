package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class RemoveGroupMemberInfoPacket {
    private final UUID uuid;

    public RemoveGroupMemberInfoPacket(UUID uuid) {
        this.uuid = uuid;

    }

    public static void encode(RemoveGroupMemberInfoPacket msg, PacketBuffer buffer) {
        buffer.writeUniqueId(msg.uuid);
    }

    public static RemoveGroupMemberInfoPacket decode(PacketBuffer buffer) {
        return new RemoveGroupMemberInfoPacket(buffer.readUniqueId());
    }

    public static class Handler {
        public static void handle(final RemoveGroupMemberInfoPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> PlayerHandler.removePlayerInformation(msg.uuid)
            );

            context.get().setPacketHandled(true);
        }
    }
}
