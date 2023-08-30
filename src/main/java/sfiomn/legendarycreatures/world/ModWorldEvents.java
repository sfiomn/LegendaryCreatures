package sfiomn.legendarycreatures.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.world.gen.ModEntityGeneration;

@Mod.EventBusSubscriber(modid = LegendaryCreatures.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModEntityGeneration.onBiomeLoading(event);
    }
}
