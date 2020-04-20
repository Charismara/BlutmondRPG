package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.event.SyncClassDataEvent;
import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SyncClassDataPacket {
    private final UUID uuid;
    private final int basicClassId;
    private final int classLevelId;
    private final double classExp;
    private final float maxHP, maxMana, mana, melee, magic, bow;

    public SyncClassDataPacket(PlayerEntity player, IModClass cap, boolean heal) {
        this(player.getUniqueID(), cap.getBasicClass().getId(), cap.getClassLevel().getId(), cap.getClassExp(), cap.getMaxHP(), cap.getMaxMana(), cap.getCurrentMana(), cap.getMeleeDmg(), cap.getMagicDmg(), cap.getBowDmg());
        PlayerHandler.applyHP(player, cap.getMaxHP() - player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue(), heal);
        PlayerHandler.applyDmg(player, cap.getMeleeDmg() - player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
    }

    public SyncClassDataPacket(PlayerEntity playerEntity, IModClass cap) {
        this(playerEntity, cap, false);
    }

    private SyncClassDataPacket(final UUID uuid, final int classId, final int levelId, final double classExp, final float maxHP, final float maxMana, final float mana, final float melee, final float magic, final float bow) {
        this.uuid = uuid;
        this.basicClassId = classId;
        this.classLevelId = levelId;
        this.classExp = classExp;
        this.maxHP = maxHP;
        this.maxMana = maxMana;
        this.mana = mana;
        this.bow = bow;
        this.magic = magic;
        this.melee = melee;
    }

    public static void encode(SyncClassDataPacket msg, PacketBuffer buffer) {
        buffer.writeUniqueId(msg.uuid);
        buffer.writeInt(msg.basicClassId);
        buffer.writeInt(msg.classLevelId);
        buffer.writeDouble(msg.classExp);
        buffer.writeFloat(msg.maxHP);
        buffer.writeFloat(msg.maxMana);
        buffer.writeFloat(msg.mana);
        buffer.writeFloat(msg.melee);
        buffer.writeFloat(msg.magic);
        buffer.writeFloat(msg.bow);
    }

    public static SyncClassDataPacket decode(PacketBuffer buffer) {
        Ref.LOGGER.debug("Decoded Sync Packet");
        return new SyncClassDataPacket(
                buffer.readUniqueId(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readDouble(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat()
        );
    }

    public static class Handler {
        public static void handle(final SyncClassDataPacket msg, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(
                    () -> {
                        MinecraftForge.EVENT_BUS.post(new SyncClassDataEvent(msg.basicClassId, msg.classLevelId, msg.classExp, msg.maxHP, msg.maxMana, msg.mana, msg.melee, msg.magic, msg.bow));
                    }
            );

            Ref.LOGGER.debug("Handled Sync Packet");
            context.get().setPacketHandled(true);
        }
    }
}
