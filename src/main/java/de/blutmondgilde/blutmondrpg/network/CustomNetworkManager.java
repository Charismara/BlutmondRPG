package de.blutmondgilde.blutmondrpg.network;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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

    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        HANDLER.send(target, message);
    }

    @OnlyIn(Dist.CLIENT)
    public static <MSG> void sendToServer(MSG message) {
        HANDLER.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, PlayerEntity player) {
        send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), message);
    }

    private static void syncPlayer(PlayerEntity player, IModClass capability) {
        //sendToPlayer(new SyncPlayerUserClassPacket(player, capability), player);
    }

    public static void syncPlayer(PlayerEntity player) {
        syncPlayer(player, player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while syncing Playerdata")));
    }
}
