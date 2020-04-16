package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.event.OpenChooseClassGUIEvent;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenChooseGuiPacket {
    private final byte NEEDED_VALUE = 1;

    public static void encode(OpenChooseGuiPacket msg, PacketBuffer buffer) {
        buffer.writeByte(msg.NEEDED_VALUE);
    }

    public static OpenChooseGuiPacket decode(PacketBuffer buffer) {
        byte neededValue = 0;
        try {
            neededValue = buffer.readByte();
        } catch (Exception ex) {
            Ref.LOGGER.error("Exeption while decoding packet.");
            ex.printStackTrace();
        }
        return new OpenChooseGuiPacket();
    }

    public static class Handler {
        public static void handle(final OpenChooseGuiPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> {
                        try {
                            if(context.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
                                MinecraftForge.EVENT_BUS.post(new OpenChooseClassGUIEvent());
                            }
                        } catch (Exception ex) {
                            Ref.LOGGER.error("Exception while handle OpenChooseGuiPacket");
                            ex.printStackTrace();
                        }
                    }
            );
            context.get().setPacketHandled(true);
        }
    }
}
