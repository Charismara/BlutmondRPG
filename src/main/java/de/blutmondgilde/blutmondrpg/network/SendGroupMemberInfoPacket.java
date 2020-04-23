package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SendGroupMemberInfoPacket {
    private final UUID uuid;
    private final String name;
    private final Float hp, maxHp;

    public SendGroupMemberInfoPacket(UUID uuid, String name, float hp, float maxHp) {
        this.uuid = uuid;
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public static void encode(SendGroupMemberInfoPacket msg, PacketBuffer buffer) {
        buffer.writeUniqueId(msg.uuid);
        buffer.writeString(msg.name);
        buffer.writeFloat(msg.hp);
        buffer.writeFloat(msg.maxHp);
    }

    public static SendGroupMemberInfoPacket decode(PacketBuffer buffer) {
        return new SendGroupMemberInfoPacket(buffer.readUniqueId(), buffer.readString(), buffer.readFloat(), buffer.readFloat());
    }

    public static class Handler {
        public static void handle(final SendGroupMemberInfoPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> PlayerHandler.addPlayerInformation(msg.uuid, msg.name, msg.hp, msg.maxHp)
            );

            context.get().setPacketHandled(true);
        }
    }
}
