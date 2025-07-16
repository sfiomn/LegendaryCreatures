package sfiomn.legendarycreatures.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.LegendaryCreatures;

import static sfiomn.legendarycreatures.LegendaryCreatures.LEGENDARY_HOSTILE_CREATURE;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LegendaryCreatures.MOD_ID);
/*
    public static final RegistryObject<EntityType<GeonachEntity>> GEONACH = ENTITY_TYPES.register("geonach",
            () -> EntityType.Builder.of(GeonachEntity::new, EntityClassification.MONSTER)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "geonach").toString()));*/

    public static final RegistryObject<EntityType<BullfrogEntity>> BULLFROG = ENTITY_TYPES.register("bullfrog",
            () -> EntityType.Builder.of(BullfrogEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(1.2F, 1.0F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "bullfrog").toString()));

    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = ENTITY_TYPES.register("butterfly",
            () -> EntityType.Builder.of(ButterflyEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.4F)
                    .setShouldReceiveVelocityUpdates(true)
                    .clientTrackingRange(5)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "butterfly").toString()));

    public static final RegistryObject<EntityType<DesertMojoEntity>> DESERT_MOJO = ENTITY_TYPES.register("desert_mojo",
            () -> EntityType.Builder.of(DesertMojoEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "desert_mojo").toString()));

    public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ENTITY_TYPES.register("dragonfly",
            () -> EntityType.Builder.of(DragonflyEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.4F)
                    .setShouldReceiveVelocityUpdates(true)
                    .clientTrackingRange(5)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "dragonfly").toString()));

    public static final RegistryObject<EntityType<ForestMojoEntity>> FOREST_MOJO = ENTITY_TYPES.register("forest_mojo",
            () -> EntityType.Builder.of(ForestMojoEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "forest_mojo").toString()));

    public static final RegistryObject<EntityType<HermitCrabEntity>> HERMIT_CRAB = ENTITY_TYPES.register("hermit_crab",
            () -> EntityType.Builder.of(HermitCrabEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.4F)
                    .setShouldReceiveVelocityUpdates(true)
                    .clientTrackingRange(5)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "hermit_crab").toString()));

    public static final RegistryObject<EntityType<HoundEntity>> HOUND = ENTITY_TYPES.register("hound",
            () -> EntityType.Builder.of(HoundEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(1.3F, 1.3F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "hound").toString()));

    public static final RegistryObject<EntityType<MantisEntity>> MANTIS = ENTITY_TYPES.register("mantis",
            () -> EntityType.Builder.of(MantisEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.4F)
                    .setShouldReceiveVelocityUpdates(true)
                    .clientTrackingRange(5)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "mantis").toString()));

    public static final RegistryObject<EntityType<LadybugEntity>> LADYBUG = ENTITY_TYPES.register("ladybug",
            () -> EntityType.Builder.of(LadybugEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.4F)
                    .setShouldReceiveVelocityUpdates(true)
                    .clientTrackingRange(5)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "ladybug").toString()));

    public static final RegistryObject<EntityType<ScarabEntity>> SCARAB = ENTITY_TYPES.register("scarab",
            () -> EntityType.Builder.of(ScarabEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.4F)
                    .setShouldReceiveVelocityUpdates(true)
                    .clientTrackingRange(5)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scarab").toString()));

    public static final RegistryObject<EntityType<ScarecrowEntity>> SCARECROW = ENTITY_TYPES.register("scarecrow",
            () -> EntityType.Builder.of(ScarecrowEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.8F, 1.95F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scarecrow").toString()));

    public static final RegistryObject<EntityType<ScorpionEntity>> SCORPION = ENTITY_TYPES.register("scorpion",
            () -> EntityType.Builder.of(ScorpionEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(1.8F, 0.8F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scorpion").toString()));

    public static final RegistryObject<EntityType<ScorpionBabyEntity>> SCORPION_BABY = ENTITY_TYPES.register("scorpion_baby",
            () -> EntityType.Builder.of(ScorpionBabyEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.4F, 0.2F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "scorpion_baby").toString()));

    public static final RegistryObject<EntityType<WispEntity>> WISP = ENTITY_TYPES.register("wisp",
            () -> EntityType.Builder.of(WispEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "wisp").toString()));

    public static final RegistryObject<EntityType<WispPurseEntity>> WISP_PURSE = ENTITY_TYPES.register("wisp_purse",
            () -> EntityType.Builder.<WispPurseEntity>of(WispPurseEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(64)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "wisp_purse").toString()));

    public static final RegistryObject<EntityType<NetherWispEntity>> NETHER_WISP = ENTITY_TYPES.register("nether_wisp",
            () -> EntityType.Builder.of(NetherWispEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "nether_wisp").toString()));

    public static final RegistryObject<EntityType<NetherWispPurseEntity>> NETHER_WISP_PURSE = ENTITY_TYPES.register("nether_wisp_purse",
            () -> EntityType.Builder.<NetherWispPurseEntity>of(NetherWispPurseEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(64)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "nether_wisp_purse").toString()));

    public static final RegistryObject<EntityType<EnderWispEntity>> ENDER_WISP = ENTITY_TYPES.register("ender_wisp",
            () -> EntityType.Builder.of(EnderWispEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "ender_wisp").toString()));

    public static final RegistryObject<EntityType<EnderWispPurseEntity>> ENDER_WISP_PURSE = ENTITY_TYPES.register("ender_wisp_purse",
            () -> EntityType.Builder.<EnderWispPurseEntity>of(EnderWispPurseEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(64)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "ender_wisp_purse").toString()));

    public static final RegistryObject<EntityType<CorpseEaterEntity>> CORPSE_EATER = ENTITY_TYPES.register("corpse_eater",
            () -> EntityType.Builder.of(CorpseEaterEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.6F, 1.2F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "corpse_eater").toString()));

    public static final RegistryObject<EntityType<PeacockSpiderEntity>> PEACOCK_SPIDER = ENTITY_TYPES.register("peacock_spider",
            () -> EntityType.Builder.of(PeacockSpiderEntity::new, LEGENDARY_HOSTILE_CREATURE)
                    .sized(0.9F, 0.5F)
                    .clientTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(LegendaryCreatures.MOD_ID, "peacock_spider").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
