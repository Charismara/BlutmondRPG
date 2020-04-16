package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.event.ChangePlayerClassEvent;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeClassPacket {
    private final int CLASS_ID;

    public ChangeClassPacket(int classId) {
        this.CLASS_ID = classId;
    }

    public static void encode(ChangeClassPacket msg, PacketBuffer buffer) {
        buffer.writeInt(msg.CLASS_ID);
        Ref.LOGGER.debug("Encoding a ChangeClassPacket.");
    }

    public static ChangeClassPacket decode(PacketBuffer buffer) {
        int classId = -1;
        try {
            classId = buffer.readInt();
        } catch (Exception ex) {
            Ref.LOGGER.error("Exeption while decoding packet.");
            ex.printStackTrace();
        }

        Ref.LOGGER.debug("Decoding a ChangeClassPacket.");
        return new ChangeClassPacket(classId);
    }

    public static class Handler {
        public static void handle(final ChangeClassPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> {
                        try {
                            MinecraftForge.EVENT_BUS.post(new ChangePlayerClassEvent(context.get().getSender(), msg.CLASS_ID));
                        } catch (Exception ex) {
                            Ref.LOGGER.error("Exception while handle OpenChooseGuiPacket");
                            ex.printStackTrace();
                        }
                    }
            );

            Ref.LOGGER.debug("Handled a ChangeClassPacket.");
            context.get().setPacketHandled(true);
        }
    }
}
