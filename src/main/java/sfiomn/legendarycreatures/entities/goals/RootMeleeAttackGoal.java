package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.api.DamageSources;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;
import sfiomn.legendarycreatures.registry.EffectRegistry;

import java.util.EnumSet;

public class RootMeleeAttackGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    private final int attackDuration;
    private final int actionPoint;
    private final float damageEvery10Ticks;
    private final int coolDown;
    private final SoundEvent sound;
    private final double speedModifier;
    private final double stopAttackMobHealthPercent;
    private float mobHealth;
    private boolean attackBlocked;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private LivingEntity rootTarget;

    public RootMeleeAttackGoal(AnimatedCreatureEntity mob, int attackDuration, int startActionPoint, float damageEvery10Ticks, double stopAttackMobHealthPercent, double speedModifier, int goalCoolDown, SoundEvent soundAttack) {
        this.mob = mob;
        this.attackDuration = attackDuration;
        this.actionPoint = startActionPoint;
        this.damageEvery10Ticks = damageEvery10Ticks;
        this.coolDown = goalCoolDown;
        this.sound = soundAttack;
        this.speedModifier = speedModifier;
        this.stopAttackMobHealthPercent = stopAttackMobHealthPercent;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() == AnimatedCreatureEntity.ROOT_ATTACK;
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
                if (path != null) {
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
        } else if (((this.mobHealth - this.mob.getHealth()) / this.mob.getMaxHealth()) > this.stopAttackMobHealthPercent) {
            LegendaryCreatures.LOGGER.debug("start mob health : " + this.mobHealth + ", current health : " + this.mob.getHealth() + ", stop trigger : " + ((this.mobHealth - this.mob.getHealth()) / this.mob.getMaxHealth()));
            LegendaryCreatures.LOGGER.debug("stop attack ? " + (((this.mobHealth - this.mob.getHealth()) / this.mob.getMaxHealth()) > this.stopAttackMobHealthPercent));
            return false;
        }
        return !this.attackBlocked;
    }

    public void start() {
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
        this.mobHealth = this.mob.getHealth();
        this.attackBlocked = false;
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
            if (this.mob.canSee(target) && (getAttackReachSqr(target) / 1.5f) < distToTargetSqr) {
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
            if (this.ticksUntilNextAttack == 0 && getAttackReachSqr(target) >= distToTargetSqr && !isAttacking()) {
                this.startAttack();
            }

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            this.attack(target, distToTargetSqr);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.ROOT_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        if (this.rootTarget != null)
            removeRootEffect(this.rootTarget);
    }

    protected void attack(LivingEntity target, double squaredDistance) {
        if (this.sound != null && isAttacking() && isActionPoint() &&
                squaredDistance <= getAttackReachSqr(target)) {
            mob.playSound(this.sound, 1.0F, 1.0F);
        }

        if (target != null && isAttacking()) {
            if (squaredDistance <= getAttackReachSqr(target)) {
                // Apply root effect on first "hurt" if not blocked
                if (isActionPoint()) {
                    if (isInitialActionPoint()) {
                        if (!this.isDamageSourceBlocked(target)) {
                            target.hurt(DamageSources.ROOT_ATTACK, this.damageEvery10Ticks);
                            addRootEffect(target);
                            this.rootTarget = target;
                        } else {
                            target.hurt(DamageSource.mobAttack(this.mob), this.damageEvery10Ticks);
                            this.attackBlocked = true;
                        }
                    } else {
                        target.hurt(DamageSources.ROOT_ATTACK, this.damageEvery10Ticks);
                    }
                }
                // remove root effect if out of reach
            } else {
                if (this.rootTarget != null) {
                    removeRootEffect(this.rootTarget);
                }
            }
        }
    }

    protected void addRootEffect(LivingEntity target) {
        target.addEffect(new EffectInstance(EffectRegistry.ROOT.get(), this.attackDuration, 0, false, true));
    }

    protected void removeRootEffect(LivingEntity target) {
        if (target.hasEffect(EffectRegistry.ROOT.get())) {
            target.removeEffect(EffectRegistry.ROOT.get());
        }
    }

    protected boolean isInitialActionPoint() {
        return (this.attackDuration - this.actionPoint) == this.attackAnimationTick;
    }

    protected boolean isActionPoint() {
        return (this.attackDuration - this.actionPoint - this.attackAnimationTick) % 10 == 0 &&
                (this.attackDuration - this.actionPoint - this.attackAnimationTick) >= 0;
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }

    protected boolean isDamageSourceBlocked(LivingEntity target) {
        if (target.isBlocking()) {
            Vector3d vector3d2 = this.mob.position();
            Vector3d vector3d = target.getViewVector(1.0F);
            Vector3d vector3d1 = vector3d2.vectorTo(target.position()).normalize();
            vector3d1 = new Vector3d(vector3d1.x, 0.0D, vector3d1.z);
            if (vector3d1.dot(vector3d) < 0.0D) {
                return true;
            }
        }
        return false;
    }
}
