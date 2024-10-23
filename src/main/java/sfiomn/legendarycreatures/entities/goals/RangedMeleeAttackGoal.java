package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;
import java.util.Objects;

public class RangedMeleeAttackGoal extends MoveToTargetGoal {
    protected final int attackDuration;
    private final int actionPoint;
    private final int goalCoolDown;
    private final double minDistance;
    private final double maxDistance;
    protected int attackAnimationTick;
    private long lastUseTime;
    private boolean hasAttacked;

    public RangedMeleeAttackGoal(AnimatedCreatureEntity mob, int attackDuration, int hurtTick, int goalCoolDown, double minDistanceAttack, double maxDistanceAttack, double speedModifier, boolean followingEvenIfNotSeen ) {
        super(mob, speedModifier, followingEvenIfNotSeen);
        this.attackDuration = (int) Math.ceil(attackDuration / 2.0);
        this.actionPoint = hurtTick / 2;
        this.goalCoolDown = goalCoolDown;
        this.minDistance = minDistanceAttack;
        this.maxDistance = maxDistanceAttack;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() == getAnimationId();
    }

    public boolean canUse() {
        long time = this.mob.level().getGameTime();
        if (time - this.lastUseTime < goalCoolDown || isAttacking()) {
            return false;
        } else {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                if (this.mob.getSensing().hasLineOfSight(target) && this.mob.distanceToSqr(target) > getMinAttackReachSqr(target)) {
                    return super.canUse();
                } else {
                    return false;
                }
            }
        }
    }

    public boolean canContinueToUse() {
        boolean canContinueToUse = super.canContinueToUse();
        if (canContinueToUse)
            return !hasAttacked &&
                    (this.mob.distanceToSqr(Objects.requireNonNull(this.mob.getTarget())) > getMinAttackReachSqr(this.mob.getTarget()));
        else
            return false;
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.hasAttacked = false;
    }

    public void stop() {
        super.stop();
        this.lastUseTime = this.mob.level().getGameTime();

        if (isAttacking())
            this.stopAttack();

        this.mob.setAggressive(false);
    }

    public void tick() {
        if (this.attackAnimationTick > 0)
            this.attackAnimationTick -= 1;

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            // Move to target
            if (!isAttacking())
                super.tick();
            else
                this.mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());

            // Attack target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            if (!isAttacking() && !hasAttacked && distToTargetSqr <= getAttackReachSqr(target) - 1.0f) {
                this.mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                this.startAttack(target);
            }

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            if (isActionPoint())
                this.executeAttack(target);
        }
    }

    protected int getAnimationId() {
        return AnimatedCreatureEntity.DISTANCE_ATTACK;
    }

    protected void startAttack(LivingEntity target) {
        this.mob.setAttackAnimation(getAnimationId());
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.hasAttacked = true;
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
    }

    protected boolean executeAttack(LivingEntity target) {
        return mob.doHurtTarget(target);
    }

    protected  boolean isActionPoint() {
        return this.attackAnimationTick == (this.attackDuration - this.actionPoint);
    }

    protected double getMinAttackReachSqr(LivingEntity entity) {
        return (double) (this.minDistance * this.minDistance);
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.maxDistance * this.maxDistance + entity.getBbWidth());
    }
}
