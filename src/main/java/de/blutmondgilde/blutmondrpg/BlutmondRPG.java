package de.blutmondgilde.blutmondrpg;

import de.blutmondgilde.blutmondrpg.capabilities.CustomCapabilityManager;
import de.blutmondgilde.blutmondrpg.commands.CommandManager;
import de.blutmondgilde.blutmondrpg.event.GroupPlayerLeaveEvent;
import de.blutmondgilde.blutmondrpg.handler.GroupHandler;
import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import de.blutmondgilde.blutmondrpg.handler.RenderHandler;
import de.blutmondgilde.blutmondrpg.items.ItemList;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(Ref.MOD_ID)
public class BlutmondRPG {
    private static MinecraftServer minecraftServer;
    private static final Map<PlayerEntity, PlayerEntity> pendingGroupRequest = new HashMap<>();
    private static final DeferredRegister<Item> ITEM_REGISTRY = new DeferredRegister<>(ForgeRegistries.ITEMS, Ref.MOD_ID);

    public BlutmondRPG() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStop);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CustomCapabilityManager());
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new GroupHandler());

        //Registers Items
        new ItemList();
        ITEM_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent e) {
        //Register Capabilities
        CustomCapabilityManager.registerCapabilities();
        //Register Network Channel
        CustomNetworkManager.register();
    }

    private void serverSetup(final FMLServerStartingEvent e) {
        CommandManager.init(e.getCommandDispatcher());
        minecraftServer = e.getServer();
    }

    private void serverStop(final FMLServerStoppingEvent e) {
        //run group leave event for each player to prevent group bugs
        List<ServerPlayerEntity> playerList = e.getServer().getPlayerList().getPlayers();
        for (ServerPlayerEntity player : playerList) {
            MinecraftForge.EVENT_BUS.post(new GroupPlayerLeaveEvent(player));
        }
    }

    public static MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }

    public static boolean isPendingGroupRequest(PlayerEntity invitor, PlayerEntity invited) {
        if (!pendingGroupRequest.containsKey(invitor)) return false;
        return pendingGroupRequest.get(invitor).equals(invited);
    }

    public static void addPendingGroupRequest(PlayerEntity invitor, PlayerEntity invited) {
        pendingGroupRequest.put(invitor, invited);
    }

    public static void removePendingGroupRequest(PlayerEntity invitor, PlayerEntity invited) {
        pendingGroupRequest.remove(invitor, invited);
    }

    public static DeferredRegister<Item> getItemManager() {
        return ITEM_REGISTRY;
    }
}
