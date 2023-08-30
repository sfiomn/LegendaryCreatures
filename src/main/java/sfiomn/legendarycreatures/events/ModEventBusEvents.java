package sfiomn.legendarycreatures.events;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

@Mod.EventBusSubscriber(modid = LegendaryCreatures.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {

        event.put(EntityTypeRegistry.DESERT_MOJO.get(), MojoEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.FOREST_MOJO.get(), ForestMojoEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.HOUND.get(), HoundEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.SCARECROW.get(), ScarecrowEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.SCORPION.get(), ScorpionEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.SCORPION_BABY.get(), ScorpionBabyEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.WISP.get(), WispEntity.setCustomAttributes().build());
        event.put(EntityTypeRegistry.CORPSE_EATER.get(), CorpseEaterEntity.setCustomAttributes().build());
    }
}
