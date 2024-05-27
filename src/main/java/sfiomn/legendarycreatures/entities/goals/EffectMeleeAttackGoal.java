package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;

public class EffectMeleeAttackGoal extends MoveToTargetGoal {
    protected final int effectDuration;
    protected final int effectStrength;
    private final int attackDuration;
    private final int actionPoint;
    private final int attackCoolDown;
    private final int coolDown;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextAttack;
    protected boolean isEffectApplied;
    protected final int tentative;
    protected int tentativeCount;

    public EffectMeleeAttackGoal(AnimatedCreatureEntity mob, int effectDuration, int effectStrength, int tentative, int attackDuration, int hurtTick, int attackCoolDown, double speedModifier, boolean followingEvenIfNotSeen, int goalCoolDown) {
        super(mob, speedModifier, followingEvenIfNotSeen);
        this.effectDuration = effectDuration;
        this.effectStrength = effectStrength;
        this.attackDuration = attackDuration;
        this.actionPoint = hurtTick;
        this.attackCoolDown = attackCoolDown;
        this.tentative = tentative;
        this.coolDown = goalCoolDown;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() == AnimatedCreatureEntity.EFFECT_ATTACK;
    }

    public boolean canUse() {
        long time = this.mob.level.getGameTime();
        if (time - this.lastUseTime < coolDown || isAttacking()) {
            return false;
        } else {
            LivingEntity target = this.mob.getTarget();
            if (!super.canUse()) return false;
            assert target != null;
            return !target.hasEffect(Effects.POISON);
        }
    }

    public boolean canContinueToUse() {
        if (super.canContinueToUse())
            return !isEffectApplied || this.tentativeCount < this.tentative;
        else
            return false;
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.isEffectApplied = false;
        this.ticksUntilNextAttack = 0;
        this.tentativeCount = 0;
    }

    public void stop() {
        super.stop();
        this.lastUseTime = this.mob.level.getGameTime();

        if (isAttacking())
            this.stopAttack();

        this.mob.setAggressive(false);
    }

    public void tick() {
        if (this.attackAnimationTick > 0)
            this.attackAnimationTick -= 1;
        if (this.ticksUntilNextAttack > 0)
            this.ticksUntilNextAttack -= 1;

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.mob.getLookControl().setLookAt(target.position());

            // Move to target
            super.tick();

            // Attack target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            if (this.ticksUntilNextAttack == 0 && getAttackReachSqr(target) >= distToTargetSqr && !isAttacking())
                this.startAttack();

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            if (isActionPoint() && distToTargetSqr <= getAttackReachSqr(target))
                this.executeAttack(target);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.EFFECT_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        this.ticksUntilNextAttack = this.attackCoolDown;
    }

    protected void executeAttack(LivingEntity target) {
        if (target != null) {
            if (mob.doHurtTarget(target)) {
                applyEffect(target);
                this.tentativeCount += 1;
            }
        }
    }

    protected void applyEffect(LivingEntity target) {
        target.addEffect(new EffectInstance(Effects.POISON, this.effectDuration, this.effectStrength, false, true));
        this.isEffectApplied = target.hasEffect(Effects.POISON);
    }

    protected  boolean isActionPoint() {
        return this.attackAnimationTick == (this.attackDuration - this.actionPoint);
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }
}
