package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.commons.lang3.tuple.Pair;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.*;

public class ChargeMeleeAttackGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    private final int attackDuration;
    private final int actionPoint;
    private final int coolDown;
    private final double minDistance;
    private final SoundEvent sound;
    private final double speedModifier;
    private final double avoidChargeAngle;
    private final int avoidChargeTick;
    private int originalPosCountInPastTick;
    private int updatePathTick;
    private int attackAnimationTick;
    private boolean hasAttacked;
    private long lastUseTime;
    private int updateOriginalPosTick;
    private final Queue<Pair<Vector3d, Vector3d>> pastPositions;
    private Vector3d originalPos;
    private Vector3d originalTargetPos;
    private double targetX;
    private double targetY;
    private double targetZ;

    public ChargeMeleeAttackGoal(AnimatedCreatureEntity mob, int attackDuration, int hurtTick, int attackCoolDown, double minDistanceAttack, SoundEvent soundAttack, double speedModifier, double avoidChargeAngle, int avoidChargeTick) {
        this.mob = mob;
        this.attackDuration = attackDuration;
        this.actionPoint = hurtTick;
        this.coolDown = attackCoolDown;
        this.minDistance = minDistanceAttack;
        this.sound = soundAttack;
        this.speedModifier = speedModifier;
        this.avoidChargeAngle = avoidChargeAngle;
        this.avoidChargeTick = avoidChargeTick;
        this.pastPositions = new LinkedList<>();
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
                Path path = this.mob.getNavigation().createPath(target, 0);
                if (path != null && this.mob.canSee(target) && this.mob.distanceToSqr(target) > minDistance * minDistance) {
                    this.originalPos = new Vector3d(this.mob.getX(), this.mob.getY(), this.mob.getZ());
                    this.originalTargetPos = new Vector3d(target.getX(), target.getY(), target.getZ());
                    return true;
                } else {
                    return false;
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
        }
        return !hasAttacked;
    }

    public void start() {
        this.lastUseTime = this.mob.level.getGameTime();

        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.hasAttacked = false;
        this.updatePathTick = 0;
        this.updateOriginalPosTick = 0;
        this.originalPosCountInPastTick = this.avoidChargeTick;
        this.pastPositions.clear();
    }

    public void stop() {
        if (isAttacking())
            this.stopAttack();

        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            if (this.originalPosCountInPastTick > 0)
                this.originalPosCountInPastTick -= 1;

            double distSqrToTarget = this.mob.distanceToSqr(target);

            // Add the original Mob Pos and Target Pos in the queue & read from the queue after "tick in Past"
            // The idea is to update up where the mob can go based on where he was
            // It allows to "avoid" the charge if you can move around the mob fast enough
            if (--updateOriginalPosTick <= 0) {
                this.updateOriginalPosTick = 5;

                Vector3d originalPos = this.mob.getPosition(0.0f);
                Vector3d originalTargetPos = target.getPosition(0.0f);

                this.pastPositions.add(Pair.of(originalPos, originalTargetPos));

                if (this.originalPosCountInPastTick == 0) {
                    Pair<Vector3d, Vector3d> pastPosition = this.pastPositions.remove();
                    this.originalPos = pastPosition.getLeft();
                    this.originalTargetPos = pastPosition.getRight();
                }
            }

            // Update next target point
            if (this.mob.canSee(target) && this.getCorrectionAngle(target) <= this.avoidChargeAngle) {
                this.targetX = target.getX();
                this.targetY = target.getY();
                this.targetZ = target.getZ();
            }

            // Move to target point
            if (--updatePathTick <= 0 && getAttackReachSqr(target) / 2.0f < distSqrToTarget) {
                if (this.mob.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speedModifier)) {
                    this.updatePathTick = 4 + this.mob.getRandom().nextInt(7);
                } else if (!isAttacking()) {
                    this.startAttack();
                }
                // Increase path calculation interval if target far away (similar to melee attack)
                if (distSqrToTarget > 1024.0D) {
                    this.updatePathTick += 10;
                } else if (distSqrToTarget > 256.0D) {
                    this.updatePathTick += 5;
                }
            }

            // Attack target
            if ((getAttackReachSqr(target) >= distSqrToTarget) && !isAttacking())
                this.startAttack();

            if (this.attackAnimationTick > 0)
                this.attackAnimationTick -= 1;
            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            this.attack(target, distSqrToTarget);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.CHARGE_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        this.hasAttacked = true;
    }

    protected void attack(LivingEntity target, double squaredDistance) {
        if (this.sound != null && this.attackAnimationTick == (this.attackDuration - this.actionPoint) &&
                squaredDistance < getAttackReachSqr(target)) {
            mob.playSound(this.sound, 1.0F, 1.0F);
        }

        if (target != null && this.attackAnimationTick == (this.attackDuration - this.actionPoint) &&
                squaredDistance < getAttackReachSqr(target)) {
            mob.doHurtTarget(target);
        }
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }

    protected double getCorrectionAngle(LivingEntity target) {
        double distFromOldToNewPos = Math.sqrt(target.distanceToSqr(this.originalTargetPos));
        double distToOldPos = Math.sqrt(this.originalPos.distanceToSqr(this.originalTargetPos));
        double distToNewPos = Math.sqrt(this.originalPos.distanceToSqr(target.getX(), target.getY(), target.getZ()));
        double halfPerimeter = (distToNewPos + distToOldPos + distFromOldToNewPos) / 2.0;
        double correctionAngleRad = 2 * Math.asin(Math.sqrt((halfPerimeter - distToOldPos) * (halfPerimeter - distToNewPos) / (distToOldPos * distToNewPos)));
        return correctionAngleRad / Math.PI * 180;
    }
}
