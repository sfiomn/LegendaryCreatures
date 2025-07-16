package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.entities.goals.FleeAirGoal;
import sfiomn.legendarycreatures.entities.goals.FlyingHoverGoal;

public class ButterflyEntity extends AnimatedCreatureEntity implements FlyingAnimal {
    public ButterflyEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);

        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.navigation = new FlyingPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FLYING_SPEED, 0.2f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FleeAirGoal<>(this, Player.class, 2, 2.0, 2.0));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FlyingHoverGoal(this, 5, 5, 1, 4));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        if (!this.level().isClientSide)
            setVariant(getRandom().nextInt(3));
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
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
