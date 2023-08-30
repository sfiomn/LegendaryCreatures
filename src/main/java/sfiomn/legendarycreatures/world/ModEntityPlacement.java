package sfiomn.legendarycreatures.world;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class ModEntityPlacement {
    public static void spawnPlacement() {
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.DESERT_MOJO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MojoEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.FOREST_MOJO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MojoEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.HOUND.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HoundEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCARECROW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCORPION.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCORPION_BABY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionBabyEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.WISP.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkMobSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.CORPSE_EATER.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkMobSpawnRules);
    }
}
