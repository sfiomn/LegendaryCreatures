package sfiomn.legendarycreatures.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.LegendaryCreatures;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LegendaryCreatures.MOD_ID);
/*
    public static final RegistryObject<EntityType<GeonachEntity>> GEONACH = ENTITY_TYPES.register("geonach",
            () -> EntityType.Builder.of(GeonachEntity::new, EntityClassification.MONSTER)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "geonach").toString()));*/

    public static final RegistryObject<EntityType<DesertMojoEntity>> DESERT_MOJO = ENTITY_TYPES.register("desert_mojo",
            () -> EntityType.Builder.of(DesertMojoEntity::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "desert_mojo").toString()));
    public static final RegistryObject<EntityType<ForestMojoEntity>> FOREST_MOJO = ENTITY_TYPES.register("forest_mojo",
            () -> EntityType.Builder.of(ForestMojoEntity::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "forest_mojo").toString()));
    public static final RegistryObject<EntityType<HoundEntity>> HOUND = ENTITY_TYPES.register("hound",
            () -> EntityType.Builder.of(HoundEntity::new, EntityClassification.CREATURE)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "hound").toString()));
    public static final RegistryObject<EntityType<ScarecrowEntity>> SCARECROW = ENTITY_TYPES.register("scarecrow",
            () -> EntityType.Builder.of(ScarecrowEntity::new, EntityClassification.CREATURE)
                    .sized(1.0F, 2.9F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scarecrow").toString()));
    public static final RegistryObject<EntityType<ScorpionEntity>> SCORPION = ENTITY_TYPES.register("scorpion",
            () -> EntityType.Builder.of(ScorpionEntity::new, EntityClassification.CREATURE)
                    .sized(1.8F, 0.8F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scorpion").toString()));
    public static final RegistryObject<EntityType<ScorpionBabyEntity>> SCORPION_BABY = ENTITY_TYPES.register("scorpion_baby",
            () -> EntityType.Builder.of(ScorpionBabyEntity::new, EntityClassification.CREATURE)
                    .sized(0.4F, 0.2F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scorpion_baby").toString()));
    public static final RegistryObject<EntityType<WispEntity>> WISP = ENTITY_TYPES.register("wisp",
            () -> EntityType.Builder.of(WispEntity::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.2F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "wisp").toString()));
    public static final RegistryObject<EntityType<WispPurseEntity>> WISP_PURSE = ENTITY_TYPES.register("wisp_purse",
            () -> EntityType.Builder.<WispPurseEntity>of(WispPurseEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "wisp_purse").toString()));

    public static final RegistryObject<EntityType<CorpseEaterEntity>> CORPSE_EATER = ENTITY_TYPES.register("corpse_eater",
            () -> EntityType.Builder.of(CorpseEaterEntity::new, EntityClassification.MONSTER)
                    .sized(0.79F, 1.6F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "corpse_eater").toString()));

    public static final RegistryObject<EntityType<PeacockSpiderEntity>> PEACOCK_SPIDER = ENTITY_TYPES.register("peacock_spider",
            () -> EntityType.Builder.of(PeacockSpiderEntity::new, EntityClassification.MONSTER)
                    .sized(0.9F, 0.5F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "peacock_spider").toString()));

    public static final RegistryObject<EntityType<BullfrogEntity>> BULLFROG = ENTITY_TYPES.register("bullfrog",
            () -> EntityType.Builder.of(BullfrogEntity::new, EntityClassification.MONSTER)
                    .sized(1.2F, 1.0F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "bullfrog").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
