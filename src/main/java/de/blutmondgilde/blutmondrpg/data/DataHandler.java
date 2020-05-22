package de.blutmondgilde.blutmondrpg.data;

import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = Ref.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void onGeneration(final GatherDataEvent e) {
        if (!e.includeServer()) return;

        final DataGenerator generator = e.getGenerator();
        //Generate Tags
        generator.addProvider(new DataBlockTagProvider(generator));
        generator.addProvider(new DataItemTagProvider(generator));
        generator.addProvider(new DataFluidTagProvider(generator));
        //Generate Recipes
        generator.addProvider(new DataRecipeProvider(generator));
    }
}
