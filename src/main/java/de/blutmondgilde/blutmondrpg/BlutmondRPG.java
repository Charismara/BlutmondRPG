package de.blutmondgilde.blutmondrpg;

import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ref.MOD_ID)
public class BlutmondRPG {
    public BlutmondRPG() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStop);

    }

    private void setup(final FMLCommonSetupEvent e) {
    }

    private void serverSetup(final FMLServerStartingEvent e) {
    }

    private void serverStop(final FMLServerStoppingEvent e) {
    }
}
