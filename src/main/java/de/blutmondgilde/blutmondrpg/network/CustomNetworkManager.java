package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.UUID;

public class CustomNetworkManager {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Ref.MOD_ID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        int disc = 0;

        HANDLER.registerMessage(disc++, OpenChooseGuiPacket.class, OpenChooseGuiPacket::encode, OpenChooseGuiPacket::decode, OpenChooseGuiPacket.Handler::handle);
        HANDLER.registerMessage(disc++, ChangeClassPacket.class, ChangeClassPacket::encode, ChangeClassPacket::decode, ChangeClassPacket.Handler::handle);
        HANDLER.registerMessage(disc++, SyncClassDataPacket.class, SyncClassDataPacket::encode, SyncClassDataPacket::decode, SyncClassDataPacket.Handler::handle);
        HANDLER.registerMessage(disc++, SyncGroupDataPacket.class, SyncGroupDataPacket::encode, SyncGroupDataPacket::decode, SyncGroupDataPacket.Handler::handle);
        HANDLER.registerMessage(disc++, SendGroupMemberInfoPacket.class, SendGroupMemberInfoPacket::encode, SendGroupMemberInfoPacket::decode, SendGroupMemberInfoPacket.Handler::handle);
        HANDLER.registerMessage(disc++, RemoveGroupMemberInfoPacket.class, RemoveGroupMemberInfoPacket::encode, RemoveGroupMemberInfoPacket::decode, RemoveGroupMemberInfoPacket.Handler::handle);
        HANDLER.registerMessage(disc++, ResetGroupInfoPacket.class, ResetGroupInfoPacket::encode, ResetGroupInfoPacket::decode, ResetGroupInfoPacket.Handler::handle);

        Ref.LOGGER.debug("Registered " + disc + " network packets.");
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        HANDLER.send(target, message);
    }

    @OnlyIn(Dist.CLIENT)
    public static <MSG> void sendToServer(MSG message) {
        HANDLER.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, final PlayerEntity player) {
        send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), message);
    }

    private static void syncPlayer(final PlayerEntity player, final IModClass capability) {
        sendToPlayer(new SyncClassDataPacket(player, capability), player);
    }

    public static void syncPlayerClass(final PlayerEntity player) {
        syncPlayer(player, player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exception while syncing Playerdata")));
    }

    public static void syncPlayerGroup(final PlayerEntity player) {
        sendToPlayer(new SyncGroupDataPacket(player.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exception while syncing Groupdata"))), player);
    }

    public static void sendGroupMemberInfo(final PlayerEntity receiver, final UUID member) {
        PlayerEntity player = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(member);
        sendToPlayer(new SendGroupMemberInfoPacket(member, player.getDisplayName().getString(), player.getHealth(), player.getMaxHealth()), receiver);
    }

    public static void removeGroupInfo(final PlayerEntity receiver, final UUID member) {
        sendToPlayer(new RemoveGroupMemberInfoPacket(member), receiver);
    }
}
