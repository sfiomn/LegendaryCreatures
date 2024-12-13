package sfiomn.legendarycreatures.api.entities;

import net.minecraftforge.fml.RegistryObject;
import sfiomn.legendarycreatures.config.Config;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public enum MobEntityEnum {
    DESERT_MOJO("desert_mojo", EntityTypeRegistry.DESERT_MOJO, DesertMojoEntity.class),
    FOREST_MOJO("forest_mojo", EntityTypeRegistry.FOREST_MOJO, ForestMojoEntity.class),
    HOUND("hound", EntityTypeRegistry.HOUND, HoundEntity.class),
    SCARECROW("scarecrow", EntityTypeRegistry.SCARECROW, ScarecrowEntity.class),
    SCORPION("scorpion", EntityTypeRegistry.SCORPION, ScorpionEntity.class),
    SCORPION_BABY("scorpion_baby", EntityTypeRegistry.SCORPION_BABY, ScorpionBabyEntity.class),
    WISP("wisp", EntityTypeRegistry.WISP, WispEntity.class),
    NETHER_WISP("nether_wisp", EntityTypeRegistry.NETHER_WISP, NetherWispEntity.class),
    ENDER_WISP("ender_wisp", EntityTypeRegistry.ENDER_WISP, EnderWispEntity.class),
    CORPSE_EATER("corpse_eater", EntityTypeRegistry.CORPSE_EATER, CorpseEaterEntity.class),
    PEACOCK_SPIDER("peacock_spider", EntityTypeRegistry.PEACOCK_SPIDER, PeacockSpiderEntity.class),
    BULLFROG("bullfrog", EntityTypeRegistry.BULLFROG, BullfrogEntity.class);

    public final String mobId;
    public final RegistryObject<?> entityRegistry;
    public final Class<? extends AnimatedCreatureEntity> entityConstructor;

    private MobEntityEnum(String mobId, RegistryObject<?> entityRegistry, Class<? extends AnimatedCreatureEntity> entityConstructor) {
        this.mobId = mobId;
        this.entityRegistry = entityRegistry;
        this.entityConstructor = entityConstructor;
    }

    public boolean canSpawnNaturally() {
        switch (this) {
            case DESERT_MOJO:
                return Config.Baked.desertMojoNaturalSpawn;
            case FOREST_MOJO:
                return Config.Baked.forestMojoNaturalSpawn;
            case HOUND:
                return Config.Baked.houndNaturalSpawn;
            case SCARECROW:
                return Config.Baked.scarecrowNaturalSpawn;
            case SCORPION:
                return Config.Baked.scorpionNaturalSpawn;
            case SCORPION_BABY:
                return Config.Baked.scorpionBabyNaturalSpawn;
            case WISP:
                return Config.Baked.wispNaturalSpawn;
            case NETHER_WISP:
                return Config.Baked.netherWispNaturalSpawn;
            case ENDER_WISP:
                return Config.Baked.enderWispNaturalSpawn;
            case CORPSE_EATER:
                return Config.Baked.corpseEaterNaturalSpawn;
            case PEACOCK_SPIDER:
                return Config.Baked.peacockSpiderNaturalSpawn;
            case BULLFROG:
                return Config.Baked.bullfrogNaturalSpawn;
            default:
                return false;
        }
    }

    public boolean canSpawnByBreaking() {
        switch (this) {
            case DESERT_MOJO:
                return Config.Baked.desertMojoBreakingBlockSpawn;
            case FOREST_MOJO:
                return Config.Baked.forestMojoBreakingBlockSpawn;
            case HOUND:
                return Config.Baked.houndBreakingBlockSpawn;
            case SCARECROW:
                return Config.Baked.scarecrowBreakingBlockSpawn;
            case SCORPION:
                return Config.Baked.scorpionBreakingBlockSpawn;
            case SCORPION_BABY:
                return Config.Baked.scorpionBabyBreakingBlockSpawn;
            case WISP:
                return Config.Baked.wispBreakingBlockSpawn;
            case NETHER_WISP:
                return Config.Baked.netherWispBreakingBlockSpawn;
            case ENDER_WISP:
                return Config.Baked.enderWispBreakingBlockSpawn;
            case CORPSE_EATER:
                return Config.Baked.corpseEaterBreakingBlockSpawn;
            case PEACOCK_SPIDER:
                return Config.Baked.peacockSpiderBreakingBlockSpawn;
            case BULLFROG:
                return Config.Baked.bullfrogBreakingBlockSpawn;
            default:
                return false;
        }
    }

    public boolean canSpawnByKilling() {
        switch (this) {
            case DESERT_MOJO:
                return Config.Baked.desertMojoKillingEntitySpawn;
            case FOREST_MOJO:
                return Config.Baked.forestMojoKillingEntitySpawn;
            case HOUND:
                return Config.Baked.houndKillingEntitySpawn;
            case SCARECROW:
                return Config.Baked.scarecrowKillingEntitySpawn;
            case SCORPION:
                return Config.Baked.scorpionKillingEntitySpawn;
            case SCORPION_BABY:
                return Config.Baked.scorpionBabyKillingEntitySpawn;
            case WISP:
                return Config.Baked.wispKillingEntitySpawn;
            case NETHER_WISP:
                return Config.Baked.netherWispKillingEntitySpawn;
            case ENDER_WISP:
                return Config.Baked.enderWispKillingEntitySpawn;
            case CORPSE_EATER:
                return Config.Baked.corpseEaterKillingEntitySpawn;
            case PEACOCK_SPIDER:
                return Config.Baked.peacockSpiderKillingEntitySpawn;
            case BULLFROG:
                return Config.Baked.bullfrogKillingEntitySpawn;
            default:
                return false;
        }
    }

    public static MobEntityEnum valueOfMobId(String mobId) {
        for (MobEntityEnum entityEnum: MobEntityEnum.values()) {
            if (entityEnum.mobId.equals(mobId)) {
                return entityEnum;
            }
        }
        return null;
    }
}
