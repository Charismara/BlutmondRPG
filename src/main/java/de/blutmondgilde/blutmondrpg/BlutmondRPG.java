package de.blutmondgilde.blutmondrpg;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import de.blutmondgilde.blutmondrpg.capabilities.CustomCapabilityManager;
import de.blutmondgilde.blutmondrpg.commands.CommandManager;
import de.blutmondgilde.blutmondrpg.event.GroupPlayerLeaveEvent;
import de.blutmondgilde.blutmondrpg.fluids.FluidList;
import de.blutmondgilde.blutmondrpg.handler.GenerationHandler;
import de.blutmondgilde.blutmondrpg.handler.GroupHandler;
import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import de.blutmondgilde.blutmondrpg.handler.RenderHandler;
import de.blutmondgilde.blutmondrpg.items.ItemList;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.recipe.AlloySmeltingRecipe;
import de.blutmondgilde.blutmondrpg.tileentities.ContainerList;
import de.blutmondgilde.blutmondrpg.tileentities.TileEntityList;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(Ref.MOD_ID)
public class BlutmondRPG {
    private static MinecraftServer minecraftServer;
    private static final Map<PlayerEntity, PlayerEntity> pendingGroupRequest = new HashMap<>();

    public BlutmondRPG() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStop);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CustomCapabilityManager());
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new GroupHandler());

        ItemList.register();
        BlockList.register();
        FluidList.register();
        TileEntityList.register();
        ContainerList.register();
    }

    private void setup(final FMLCommonSetupEvent e) {
        DeferredWorkQueue.runLater(() -> {
            CustomCapabilityManager.registerCapabilities();
            Ref.LOGGER.debug("Capabilities Registered");
            CustomNetworkManager.register();
            Ref.LOGGER.debug("Network Manager Registered");
            GenerationHandler.generateOres();
            Ref.LOGGER.debug("Ore Generator Registered");

            IRecipeType.register(AlloySmeltingRecipe.SERIALIZER.getRegistryName().toString());
            Ref.LOGGER.debug("Alloy Smelting Registered");
        });
    }

    private void serverSetup(final FMLServerStartingEvent e) {
        CommandManager.init(e.getCommandDispatcher());
        Ref.LOGGER.debug("Commands Registered");
        minecraftServer = e.getServer();
        Ref.LOGGER.debug("Server found Registered");
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
}
