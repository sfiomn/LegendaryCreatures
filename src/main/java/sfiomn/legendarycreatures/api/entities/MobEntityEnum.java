package sfiomn.legendarycreatures.api.entities;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.RegistryObject;
import sfiomn.legendarycreatures.config.Config;
import sfiomn.legendarycreatures.entities.*;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public enum MobEntityEnum {
    DESERT_MOJO("desert_mojo", EntityTypeRegistry.DESERT_MOJO, DesertMojoEntity.class, Config.Baked.desertMojoNaturalSpawn, Config.Baked.desertMojoBreakingBlockSpawn, Config.Baked.desertMojoKillingEntitySpawn),
    FOREST_MOJO("forest_mojo", EntityTypeRegistry.FOREST_MOJO, ForestMojoEntity.class, Config.Baked.forestMojoNaturalSpawn, Config.Baked.forestMojoBreakingBlockSpawn, Config.Baked.forestMojoKillingEntitySpawn),
    HOUND("hound", EntityTypeRegistry.HOUND, HoundEntity.class, Config.Baked.houndNaturalSpawn, Config.Baked.houndBreakingBlockSpawn, Config.Baked.houndKillingEntitySpawn),
    SCARECROW("scarecrow", EntityTypeRegistry.SCARECROW, ScarecrowEntity.class, Config.Baked.scarecrowNaturalSpawn, Config.Baked.scarecrowBreakingBlockSpawn, Config.Baked.scarecrowKillingEntitySpawn),
    SCORPION("scorpion", EntityTypeRegistry.SCORPION, ScorpionEntity.class, Config.Baked.scorpionNaturalSpawn, Config.Baked.scorpionBreakingBlockSpawn, Config.Baked.scorpionKillingEntitySpawn),
    SCORPION_BABY("scorpion_baby", EntityTypeRegistry.SCORPION_BABY, ScorpionBabyEntity.class, Config.Baked.scorpionNaturalSpawn, Config.Baked.scorpionBreakingBlockSpawn, Config.Baked.scorpionBabyKillingEntitySpawn),
    WISP("wisp", EntityTypeRegistry.WISP, WispEntity.class, Config.Baked.wispNaturalSpawn, Config.Baked.wispBreakingBlockSpawn, Config.Baked.wispKillingEntitySpawn),
    CORPSE_EATER("corpse_eater", EntityTypeRegistry.CORPSE_EATER, CorpseEaterEntity.class, Config.Baked.corpseEaterNaturalSpawn, Config.Baked.corpseEaterBreakingBlockSpawn, Config.Baked.corpseEaterKillingEntitySpawn),
    PEACOCK_SPIDER("peacock_spider", EntityTypeRegistry.PEACOCK_SPIDER, PeacockSpiderEntity.class, Config.Baked.peacockSpiderNaturalSpawn, Config.Baked.peacockSpiderBreakingBlockSpawn, Config.Baked.peacockSpiderKillingEntitySpawn),
    BULLFROG("bullfrog", EntityTypeRegistry.BULLFROG, BullfrogEntity.class, Config.Baked.peacockSpiderNaturalSpawn, Config.Baked.peacockSpiderBreakingBlockSpawn, Config.Baked.peacockSpiderKillingEntitySpawn);

    public final String mobId;
    public final RegistryObject<?> entityRegistry;
    public final Class<? extends AnimatedCreatureEntity> entityConstructor;
    public final boolean naturalSpawn;
    public final boolean breakingBlockSpawn;
    public final boolean killingEntitySpawn;

    private MobEntityEnum(String mobId, RegistryObject<?> entityRegistry, Class<? extends AnimatedCreatureEntity> entityConstructor, boolean naturalSpawn, boolean breakingBlockSpawn, boolean killingEntitySpawn) {
        this.mobId = mobId;
        this.entityRegistry = entityRegistry;
        this.entityConstructor = entityConstructor;
        this.naturalSpawn = naturalSpawn;
        this.breakingBlockSpawn = breakingBlockSpawn;
        this.killingEntitySpawn = killingEntitySpawn;
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
