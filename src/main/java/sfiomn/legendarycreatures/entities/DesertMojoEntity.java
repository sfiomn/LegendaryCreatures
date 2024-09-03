package sfiomn.legendarycreatures.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class DesertMojoEntity extends MojoEntity {
    private final int baseAttackDuration = 11;
    private final int baseAttackActionPoint = 5;

    public DesertMojoEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        setVariant(0);
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
