package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.event.OpenOtherPlayerGuiEvent;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenOtherPlayerGuiPacket {
    final String name;
    final BasicClasses playerClass;
    final int level;
    final float hp;
    final float maxHP;

    public OpenOtherPlayerGuiPacket(final String name, final BasicClasses playerClass, final int level, final float hp, final float maxHP) {
        this.name = name;
        this.playerClass = playerClass;
        this.level = level;
        this.hp = hp;
        this.maxHP = maxHP;
    }
    public OpenOtherPlayerGuiPacket(final String name, final int playerClass, final int level, final float hp, final float maxHP) {
        this.name = name;
        this.playerClass = BasicClasses.getById(playerClass);
        this.level = level;
        this.hp = hp;
        this.maxHP = maxHP;
    }

    public static void encode(OpenOtherPlayerGuiPacket msg, PacketBuffer buffer) {
        buffer.writeString(msg.name);
        buffer.writeInt(msg.playerClass.getId());
        buffer.writeInt(msg.level);
        buffer.writeFloat(msg.hp);
        buffer.writeFloat(msg.maxHP);
    }

    public static OpenOtherPlayerGuiPacket decode(PacketBuffer buffer) {
        return new OpenOtherPlayerGuiPacket(
                buffer.readString(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readFloat(),
                buffer.readFloat()
        );
    }

    public static class Handler {
        public static void handle(final OpenOtherPlayerGuiPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> {
                        try {
                            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
                                MinecraftForge.EVENT_BUS.post(new OpenOtherPlayerGuiEvent(msg.name, msg.playerClass, msg.level, msg.hp, msg.maxHP));
                            }
                        } catch (Exception ex) {
                            Ref.LOGGER.error("Exception while handle OpenOtherPlayerGuiPacket");
                            ex.printStackTrace();
                        }
                    }
            );
            context.get().setPacketHandled(true);
        }
    }
}
