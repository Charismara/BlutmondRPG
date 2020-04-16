package de.blutmondgilde.blutmondrpg;

import de.blutmondgilde.blutmondrpg.capabilities.CustomCapabilityManager;
import de.blutmondgilde.blutmondrpg.commands.CommandManager;
import de.blutmondgilde.blutmondrpg.handler.PlayerHandler;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ref.MOD_ID)
public class BlutmondRPG {

    public BlutmondRPG() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CustomCapabilityManager());
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
    }

    private void setup(final FMLCommonSetupEvent e) {
        //Register Capabilities
        CustomCapabilityManager.registerCapabilities();
        //Register Network Channel
        CustomNetworkManager.register();
    }

    private void serverSetup(final FMLServerStartingEvent e) {
        CommandManager.init(e.getCommandDispatcher());
    }
}
