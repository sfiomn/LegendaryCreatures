package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.SoundEvent;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;

public class HoldMeleeAttackGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    private final int attackDuration;
    private final int coolDown;
    private final SoundEvent sound;
    private final double speedModifier;
    private final boolean followingEvenIfNotSeen;
    private Path path;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;

    public HoldMeleeAttackGoal(AnimatedCreatureEntity mob, int attackDuration, int attackCoolDown, SoundEvent soundAttack, double speedModifier, boolean followingEvenIfNotSeen ) {
        this.mob = mob;
        this.attackDuration = attackDuration;
        this.coolDown = attackCoolDown;
        this.sound = soundAttack;
        this.speedModifier = speedModifier;
        this.followingEvenIfNotSeen = followingEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() != AnimatedCreatureEntity.NO_ANIMATION;
    }

    public boolean canUse() {
        long time = this.mob.level.getGameTime();
        if (time - this.lastUseTime < coolDown || isAttacking()) {
            return false;
        } else {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                this.path = this.mob.getNavigation().createPath(target, 0);
                this.mob.getNavigation().setSpeedModifier(this.speedModifier);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target) && this.mob.canSee(target);
                }
            }
        }
    }

    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!this.followingEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        }
        return true;
    }

    public void start() {
        this.lastUseTime = this.mob.level.getGameTime();

        this.mob.setAggressive(true);
        this.mob.setSprinting(true);
        this.attackAnimationTick = 0;
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        this.stopAttack();

        this.mob.setAggressive(false);
        this.mob.setSprinting(false);
        mob.getNavigation().stop();
    }

    public void tick() {
        if (this.attackAnimationTick > 0)
            this.attackAnimationTick -= 1;
        if (this.ticksUntilNextPathRecalculation > 0)
            this.ticksUntilNextPathRecalculation -= 1;
        if (this.ticksUntilNextAttack > 0)
            this.ticksUntilNextAttack -= 1;

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

            // Move to target
            double d = this.mob.distanceToSqr(target);
            if ((this.followingEvenIfNotSeen || this.mob.canSee(target)) && getAttackReachSqr(target) < d) {
                if (ticksUntilNextPathRecalculation == 0) {
                    this.path = this.mob.getNavigation().createPath(target, 0);
                    this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                }
                if (d > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
            }

            // Attack target
            if (this.ticksUntilNextAttack == 0 && getAttackReachSqr(target) >= d && !isAttacking())
                this.startAttack();

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            this.attack(target, d);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.HOLD_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        this.ticksUntilNextAttack = this.coolDown;
    }

    protected void attack(LivingEntity target, double squaredDistance) {

        if (this.sound != null && this.attackAnimationTick == (this.attackDuration - (int) (this.attackDuration / 2.0f))) {
            mob.playSound(this.sound, 1.0F, 1.0F);
        }

        if (target != null && squaredDistance < getAttackReachSqr(target)
                && this.attackAnimationTick == (this.attackDuration -(int) (this.attackDuration / 2.0f))) {
            mob.doHurtTarget(target);
        }

    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }

}
