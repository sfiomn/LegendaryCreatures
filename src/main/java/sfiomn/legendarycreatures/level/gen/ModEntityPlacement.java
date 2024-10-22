package sfiomn.legendarycreatures.level.gen;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class ModEntityPlacement {
    public static void spawnPlacement() {
        SpawnPlacements.register(EntityTypeRegistry.DESERT_MOJO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DesertMojoEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.FOREST_MOJO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForestMojoEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.HOUND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HoundEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCARECROW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCORPION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScorpionEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCORPION_BABY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScorpionBabyEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulFlyingCreatureSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.NETHER_WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulFlyingCreatureSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.ENDER_WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulFlyingCreatureSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.CORPSE_EATER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorpseEaterEntity::checkHostileCreatureNoSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.PEACOCK_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PeacockSpiderEntity::checkHostileCreatureDaySpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.BULLFROG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BullfrogEntity::checkHostileCreatureDaySpawnRules);
    }
}
