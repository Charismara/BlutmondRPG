package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.recipe.AlloySmeltingRecipe;
import de.blutmondgilde.blutmondrpg.tileentities.ContainerList;
import de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace.AlloyFurnaceScreen;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Ref.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class UtilHandler {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent e) {
        ScreenManager.registerFactory(ContainerList.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        Ref.LOGGER.debug("Screens Registered");
    }

    @SubscribeEvent
    public static void recipeRegister(final RegistryEvent.Register e) {
        if (!e.getGenericType().equals(IRecipeSerializer.class)) return;
        e.getRegistry().registerAll(AlloySmeltingRecipe.SERIALIZER);

        Ref.LOGGER.debug("Recipe Serializers Registered");
    }
}
