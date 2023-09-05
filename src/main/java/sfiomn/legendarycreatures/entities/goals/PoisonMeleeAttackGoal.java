package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;

public class PoisonMeleeAttackGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    private final int poisonDuration;
    private final int poisonStrength;
    private final int attackDuration;
    private final int actionPoint;
    private final int coolDown;
    private final SoundEvent sound;
    private final double speedModifier;
    private final boolean followingEvenIfNotSeen;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private boolean isTargetPoisoned;

    public PoisonMeleeAttackGoal(AnimatedCreatureEntity mob, int poisonDuration, int poisonStrength, int attackDuration, int hurtTick, int attackCoolDown, SoundEvent soundAttack, double speedModifier, boolean followingEvenIfNotSeen ) {
        this.mob = mob;
        this.poisonDuration = poisonDuration;
        this.poisonStrength = poisonStrength;
        this.attackDuration = attackDuration;
        this.actionPoint = hurtTick;
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
        return this.mob.getAttackAnimation() == AnimatedCreatureEntity.POISON_ATTACK;
    }

    public boolean canUse() {
        long time = this.mob.level.getGameTime();
        if (time - this.lastUseTime < 20 || isAttacking()) {
            return false;
        } else {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                Path path = this.mob.getNavigation().createPath(target, 0);
                if (path != null) {
                    return !target.hasEffect(Effects.POISON);
                } else {
                    return !target.hasEffect(Effects.POISON) && this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target) && this.mob.canSee(target);
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
        return !isTargetPoisoned;
    }

    public void start() {
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.isTargetPoisoned = false;
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void stop() {
        this.lastUseTime = this.mob.level.getGameTime();

        if (isAttacking())
            this.stopAttack();

        this.mob.setAggressive(false);
        mob.getNavigation().stop();
    }

    public void tick() {
        if (this.attackAnimationTick > 0)
            this.attackAnimationTick -= 1;
        if (this.ticksUntilNextAttack > 0)
            this.ticksUntilNextAttack -= 1;

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

            // Move to target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            if ((this.followingEvenIfNotSeen || this.mob.canSee(target)) && getAttackReachSqr(target) / 1.5f < distToTargetSqr) {
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

            // Attack target
            if (this.ticksUntilNextAttack == 0 && getAttackReachSqr(target) >= distToTargetSqr && !isAttacking())
                this.startAttack();

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            this.attack(target, distToTargetSqr);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.POISON_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        this.ticksUntilNextAttack = this.coolDown;
    }

    protected void attack(LivingEntity target, double squaredDistance) {

        if (this.sound != null && isActionPoint() &&
                squaredDistance <= getAttackReachSqr(target)) {
            mob.playSound(this.sound, 1.0F, 1.0F);
        }

        if (target != null && isActionPoint() &&
                squaredDistance <= getAttackReachSqr(target)) {
            if (mob.doHurtTarget(target)) {
                target.addEffect(new EffectInstance(Effects.POISON, this.poisonDuration, this.poisonStrength, false, true));
                this.isTargetPoisoned = target.hasEffect(Effects.POISON);
            }
        }
    }

    protected  boolean isActionPoint() {
        return this.attackAnimationTick == (this.attackDuration - this.actionPoint);
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }
}
