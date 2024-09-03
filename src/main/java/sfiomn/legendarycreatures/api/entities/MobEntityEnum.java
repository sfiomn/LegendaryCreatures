package sfiomn.legendarycreatures.api.entities;

import net.minecraftforge.registries.RegistryObject;
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

    MobEntityEnum(String mobId, RegistryObject<?> entityRegistry, Class<? extends AnimatedCreatureEntity> entityConstructor) {
        this.mobId = mobId;
        this.entityRegistry = entityRegistry;
        this.entityConstructor = entityConstructor;
    }

    public boolean canSpawnNaturally() {
        return switch (this) {
            case DESERT_MOJO -> Config.Baked.desertMojoNaturalSpawn;
            case FOREST_MOJO -> Config.Baked.forestMojoNaturalSpawn;
            case HOUND -> Config.Baked.houndNaturalSpawn;
            case SCARECROW -> Config.Baked.scarecrowNaturalSpawn;
            case SCORPION -> Config.Baked.scorpionNaturalSpawn;
            case SCORPION_BABY -> Config.Baked.scorpionBabyNaturalSpawn;
            case WISP -> Config.Baked.wispNaturalSpawn;
            case NETHER_WISP -> Config.Baked.netherWispNaturalSpawn;
            case ENDER_WISP -> Config.Baked.enderWispNaturalSpawn;
            case CORPSE_EATER -> Config.Baked.corpseEaterNaturalSpawn;
            case PEACOCK_SPIDER -> Config.Baked.peacockSpiderNaturalSpawn;
            case BULLFROG -> Config.Baked.bullfrogNaturalSpawn;
        };
    }

    public boolean canSpawnByBreaking() {
        return switch (this) {
            case DESERT_MOJO -> Config.Baked.desertMojoBreakingBlockSpawn;
            case FOREST_MOJO -> Config.Baked.forestMojoBreakingBlockSpawn;
            case HOUND -> Config.Baked.houndBreakingBlockSpawn;
            case SCARECROW -> Config.Baked.scarecrowBreakingBlockSpawn;
            case SCORPION -> Config.Baked.scorpionBreakingBlockSpawn;
            case SCORPION_BABY -> Config.Baked.scorpionBabyBreakingBlockSpawn;
            case WISP -> Config.Baked.wispBreakingBlockSpawn;
            case NETHER_WISP -> Config.Baked.netherWispBreakingBlockSpawn;
            case ENDER_WISP -> Config.Baked.enderWispBreakingBlockSpawn;
            case CORPSE_EATER -> Config.Baked.corpseEaterBreakingBlockSpawn;
            case PEACOCK_SPIDER -> Config.Baked.peacockSpiderBreakingBlockSpawn;
            case BULLFROG -> Config.Baked.bullfrogBreakingBlockSpawn;
        };
    }

    public boolean canSpawnByKilling() {
        return switch (this) {
            case DESERT_MOJO -> Config.Baked.desertMojoKillingEntitySpawn;
            case FOREST_MOJO -> Config.Baked.forestMojoKillingEntitySpawn;
            case HOUND -> Config.Baked.houndKillingEntitySpawn;
            case SCARECROW -> Config.Baked.scarecrowKillingEntitySpawn;
            case SCORPION -> Config.Baked.scorpionKillingEntitySpawn;
            case SCORPION_BABY -> Config.Baked.scorpionBabyKillingEntitySpawn;
            case WISP -> Config.Baked.wispKillingEntitySpawn;
            case NETHER_WISP -> Config.Baked.netherWispKillingEntitySpawn;
            case ENDER_WISP -> Config.Baked.enderWispKillingEntitySpawn;
            case CORPSE_EATER -> Config.Baked.corpseEaterKillingEntitySpawn;
            case PEACOCK_SPIDER -> Config.Baked.peacockSpiderKillingEntitySpawn;
            case BULLFROG -> Config.Baked.bullfrogKillingEntitySpawn;
        };
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
