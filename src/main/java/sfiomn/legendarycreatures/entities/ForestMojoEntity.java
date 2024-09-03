package sfiomn.legendarycreatures.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class ForestMojoEntity extends MojoEntity {
    private final int baseAttackDuration = 12;
    private final int baseAttackActionPoint = 6;

    public ForestMojoEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        setVariant(1);
    }

    @Override
    protected int getBaseAttackDuration() {
        return baseAttackDuration;
    }

    @Override
    protected int getBaseAttackActionPoint() {
        return baseAttackActionPoint;
    }
}
