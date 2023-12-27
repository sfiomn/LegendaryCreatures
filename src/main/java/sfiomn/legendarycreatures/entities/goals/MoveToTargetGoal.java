package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

public abstract class MoveToTargetGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    protected final double speedModifier;
    protected final boolean followingEvenIfNotSeen;
    protected int ticksUntilNextPathRecalculation;

    public MoveToTargetGoal(AnimatedCreatureEntity mob, double speedModifier, boolean followingEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingEvenIfNotSeen = followingEvenIfNotSeen;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else {
            Path path = this.mob.getNavigation().createPath(target, 0);
            if (path != null) {
                return true;
            } else {
                return this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target) && this.mob.canSee(target);
            }
        }
    }

    @Override
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

    @Override
    public void start() {
        this.ticksUntilNextPathRecalculation = 0;
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            // Move to target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            if ((this.followingEvenIfNotSeen || this.mob.canSee(target))) {
                if (--ticksUntilNextPathRecalculation <= 0) {
                    if (this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                    } else {
                        this.ticksUntilNextPathRecalculation += 15;
                    }
                    if (distToTargetSqr > 1024.0D) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (distToTargetSqr > 256.0D) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }
                }
            }
        }
    }

    abstract protected double getAttackReachSqr(LivingEntity entity);
}
