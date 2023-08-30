package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;

public class MoveToTargetGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    final double speedModifier;
    private int ticksUntilNextPathRecalculation;
    private Path path;
    private final int approachTo;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private final boolean followingEvenIfNotSeen;

    public MoveToTargetGoal(AnimatedCreatureEntity mob, double speedModifier, int approachTo, boolean followingEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingEvenIfNotSeen = followingEvenIfNotSeen;
        this.approachTo = approachTo;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean isInterruptable() {
        return true;
    }

    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return false;
        } else if (target.isAlive()) {
            this.path = this.mob.getNavigation().createPath(target, 0);
            if (this.path != null) {
                return true;
            }
        }
        return false;
    }

    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (isWithinReach(target)) {
            return false;
        } else if (!this.followingEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        }
        return true;
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
    }

    public void stop() {
        mob.getNavigation().stop();
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        double d0 = this.mob.distanceToSqr(livingentity);
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
        if ((this.followingEvenIfNotSeen || this.mob.canSee(livingentity)) && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D)) {
            this.pathedTargetX = livingentity.getX();
            this.pathedTargetY = livingentity.getY();
            this.pathedTargetZ = livingentity.getZ();
            if (d0 > 1024.0D) {
                this.ticksUntilNextPathRecalculation += 10;
            } else if (d0 > 256.0D) {
                this.ticksUntilNextPathRecalculation += 5;
            }

            if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                this.ticksUntilNextPathRecalculation += 15;
            }
        }
    }

    public boolean isWithinReach(LivingEntity target) {
        return this.mob.distanceToSqr(target) <= this.approachTo;
    }
}
