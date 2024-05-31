package sfiomn.legendarycreatures.world;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class ModEntityPlacement {
    public static void spawnPlacement() {
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.DESERT_MOJO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DesertMojoEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.FOREST_MOJO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ForestMojoEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.HOUND.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HoundEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCARECROW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCORPION.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCORPION_BABY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionBabyEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.WISP.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkFlyingCreatureSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.CORPSE_EATER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CorpseEaterEntity::checkCreatureNoSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.PEACOCK_SPIDER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PeacockSpiderEntity::checkCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.BULLFROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BullfrogEntity::checkCreatureDaySpawnRules);
    }
}
