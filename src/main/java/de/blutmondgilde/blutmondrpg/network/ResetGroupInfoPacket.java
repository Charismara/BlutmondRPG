package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetGroupInfoPacket {
    private final byte NEEDED_VALUE = 1;


    public static void encode(ResetGroupInfoPacket msg, PacketBuffer buffer) {
        buffer.writeByte(msg.NEEDED_VALUE);
    }

    public static ResetGroupInfoPacket decode(PacketBuffer buffer) {
        return new ResetGroupInfoPacket();
    }

    public static class Handler {
        public static void handle(final ResetGroupInfoPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    PlayerHandler::resetPlayerInformation
            );

            context.get().setPacketHandled(true);
        }
    }
}
