package sfiomn.legendarycreatures.level.gen;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class ModEntityPlacement {
    public static void spawnPlacement() {
        SpawnPlacements.register(EntityTypeRegistry.DESERT_MOJO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DesertMojoEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.FOREST_MOJO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForestMojoEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.HOUND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HoundEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCARECROW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCORPION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScorpionEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCORPION_BABY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScorpionBabyEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.NETHER_WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.ENDER_WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.CORPSE_EATER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorpseEaterEntity::checkHostileCreatureNoSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.PEACOCK_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PeacockSpiderEntity::checkHostileCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.BULLFROG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BullfrogEntity::checkHostileCreatureOnSurfaceSpawnRules);

        SpawnPlacements.register(EntityTypeRegistry.BUTTERFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.DRAGONFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.LADYBUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.SCARAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.MANTIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.HERMIT_CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkPeacefulCreatureOnSurfaceSpawnRules);
    }
}
