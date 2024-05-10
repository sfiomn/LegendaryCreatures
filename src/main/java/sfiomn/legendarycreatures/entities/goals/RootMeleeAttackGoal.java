package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import sfiomn.legendarycreatures.api.DamageSources;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;
import sfiomn.legendarycreatures.registry.EffectRegistry;

import java.util.EnumSet;

public class RootMeleeAttackGoal extends MoveToTargetGoal {
    private final AttributeModifier maxKnockBackResistance = new AttributeModifier("maxKnockBackResistance", 1000.0D, AttributeModifier.Operation.ADDITION);
    private ModifiableAttributeInstance mobKnockBackResAttribute;
    private final int initialAttackDuration;
    private final int initialActionPoint;
    private boolean initialAttackDone;
    private final int longAttackDuration;
    private final float damageEvery20Ticks;
    private final int coolDown;
    private final double stopAttackMobHealthPercent;
    private float mobHealth;
    private boolean isInitialAttackBlocked;
    private int attackAnimationTick;
    private int rootTick;
    private long lastUseTime;
    private LivingEntity rootTarget;

    public RootMeleeAttackGoal(AnimatedCreatureEntity mob, int initialAttackDuration, int initialActionPoint, int longAttackDuration, float damageEvery20Ticks, double stopAttackMobHealthPercent, double speedModifier, int goalCoolDown) {
        super(mob, speedModifier, true);
        this.initialAttackDuration = initialAttackDuration;
        this.initialActionPoint = initialActionPoint;
        this.longAttackDuration = longAttackDuration;
        this.damageEvery20Ticks = damageEvery20Ticks;
        this.coolDown = goalCoolDown;
        this.stopAttackMobHealthPercent = stopAttackMobHealthPercent;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() == AnimatedCreatureEntity.ROOT_ATTACK || this.mob.getAttackAnimation() == AnimatedCreatureEntity.BASE_ATTACK;
    }

    public boolean canUse() {
        long time = this.mob.level.getGameTime();
        if (time - this.lastUseTime < coolDown || isAttacking()) {
            return false;
        } else {
            return super.canUse();
        }
    }

    public boolean canContinueToUse() {
        if (super.canContinueToUse()) {
            if (((this.mobHealth - this.mob.getHealth()) / this.mob.getMaxHealth()) > this.stopAttackMobHealthPercent) {
                return false;
            }
            return !this.isInitialAttackBlocked;
        } else
            return false;
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.mobHealth = this.mob.getHealth();
        this.isInitialAttackBlocked = false;
        this.initialAttackDone = false;
        this.rootTick = 0;
        this.mobKnockBackResAttribute = this.mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
    }

    public void stop() {
        super.stop();
        this.lastUseTime = this.mob.level.getGameTime();

        if (isAttacking())
            this.stopAttack();

        if (this.rootTarget != null)
            removeRootEffect(this.rootTarget);

        this.mob.setAggressive(false);
    }

    public void tick() {
        if (this.attackAnimationTick > 0)
            this.attackAnimationTick -= 1;

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.mob.getLookControl().setLookAt(target.position());

            // Move to target
            super.tick();

            // Attack target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            if (getAttackReachSqr(target) >= distToTargetSqr && !isAttacking()) {
                this.mob.lookAt(EntityAnchorArgument.Type.EYES, target.position());
                this.startAttack();
            }

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            if (isAttacking()) {
                if (distToTargetSqr <= getAttackReachSqr(target)) {
                    // Apply root effect on first "hurt" if not blocked
                    if (this.initialAttackDone) {
                        if (this.rootTick++ >= 19) {
                            this.rootTick = 0;
                            this.executeRootAttack(target);
                        }
                    } else if (isInitialActionPoint()) {
                        this.executeInitialAttack(target);
                    }
                } else {
                    // remove root effect if out of reach
                    if (this.rootTarget != null) {
                        removeRootEffect(this.rootTarget);
                    }
                }
            }
        }
    }

    protected void startAttack() {
        if (!this.initialAttackDone) {
            this.mob.setAttackAnimation(AnimatedCreatureEntity.BASE_ATTACK);
            this.attackAnimationTick = this.initialAttackDuration;
        } else {
            this.mob.setAttackAnimation(AnimatedCreatureEntity.ROOT_ATTACK);
            this.attackAnimationTick = this.longAttackDuration;
        }
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
    }

    protected void executeInitialAttack(LivingEntity target) {
        if (target != null) {
            if (!this.isDamageSourceBlocked(target)) {
                target.hurt(DamageSources.ROOT_ATTACK, this.damageEvery20Ticks);
                addRootEffect(target);
                this.rootTarget = target;
                this.initialAttackDone = true;
            } else {
                target.hurt(DamageSource.mobAttack(this.mob), this.damageEvery20Ticks);
                this.isInitialAttackBlocked = true;
            }
        }
    }

    protected void executeRootAttack(LivingEntity target) {
        addRootEffect(target);
        target.hurt(DamageSources.ROOT_ATTACK, this.damageEvery20Ticks);
    }

    protected void addRootEffect(LivingEntity target) {
        target.addEffect(new EffectInstance(EffectRegistry.ROOT.get(), this.longAttackDuration, 0, false, true));
        if (this.mobKnockBackResAttribute != null && !this.mobKnockBackResAttribute.hasModifier(maxKnockBackResistance))
            this.mobKnockBackResAttribute.addTransientModifier(maxKnockBackResistance);
    }

    protected void removeRootEffect(LivingEntity target) {
        if (target.hasEffect(EffectRegistry.ROOT.get())) {
            target.removeEffect(EffectRegistry.ROOT.get());
        }
        if (this.mobKnockBackResAttribute != null && this.mobKnockBackResAttribute.hasModifier(maxKnockBackResistance))
            this.mobKnockBackResAttribute.removeModifier(maxKnockBackResistance);
    }

    protected boolean isInitialActionPoint() {
        return (this.initialAttackDuration - this.initialActionPoint) == this.attackAnimationTick;
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
