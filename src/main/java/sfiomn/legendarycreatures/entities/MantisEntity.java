package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MantisEntity extends AnimatedCreatureEntity {
    public MantisEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0, 140));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        if (!this.level().isClientSide)
            setVariant(getRandom().nextInt(3));
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity entity) {
    }

    protected void pushEntities() {
    }

    protected void checkFallDamage(double p_27419_, boolean p_27420_, BlockState p_27421_, BlockPos p_27422_) {
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }
}
