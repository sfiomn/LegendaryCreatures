package sfiomn.legendarycreatures.world;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class ModEntityPlacement {
    public static void spawnPlacement() {
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.DESERT_MOJO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DesertMojoEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.FOREST_MOJO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ForestMojoEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.HOUND.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HoundEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCARECROW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCORPION.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.SCORPION_BABY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScorpionBabyEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.WISP.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulFlyingCreatureSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.NETHER_WISP.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulFlyingCreatureSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.ENDER_WISP.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulFlyingCreatureSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.CORPSE_EATER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CorpseEaterEntity::checkHostileCreatureNoSpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.PEACOCK_SPIDER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PeacockSpiderEntity::checkHostileCreatureDaySpawnRules);
        EntitySpawnPlacementRegistry.register(EntityTypeRegistry.BULLFROG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BullfrogEntity::checkHostileCreatureDaySpawnRules);
    }
}
