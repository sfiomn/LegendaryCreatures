package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;

public class PoisonMeleeAttackGoal extends MoveToTargetGoal {
    protected final int poisonDuration;
    protected final int poisonStrength;
    private final int attackDuration;
    private final int actionPoint;
    private final int attackCoolDown;
    private final int goalCoolDown;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextAttack;
    protected boolean isPoisonApplied;
    protected final int tentative;
    protected int tentativeCount;

    public PoisonMeleeAttackGoal(AnimatedCreatureEntity mob, int poisonDuration, int poisonStrength, int tentative, int attackDuration, int hurtTick, int attackCoolDown, double speedModifier, boolean followingEvenIfNotSeen, int goalCoolDown) {
        super(mob, speedModifier, followingEvenIfNotSeen);
        this.poisonDuration = poisonDuration;
        this.poisonStrength = poisonStrength;
        this.attackDuration = (int) Math.ceil(attackDuration / 2.0);
        this.actionPoint = hurtTick / 2;
        this.attackCoolDown = (int) Math.ceil(attackCoolDown / 2.0);
        this.tentative = tentative;
        this.goalCoolDown = goalCoolDown;
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
        long time = this.mob.level().getGameTime();
        if (time - this.lastUseTime < goalCoolDown || isAttacking()) {
            return false;
        } else {
            LivingEntity target = this.mob.getTarget();
            if (!super.canUse()) return false;
            assert target != null;
            return !target.hasEffect(MobEffects.POISON);
        }
    }

    public boolean canContinueToUse() {
        if (super.canContinueToUse())
            return !isPoisonApplied && this.tentativeCount < this.tentative;
        else
            return false;
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.isPoisonApplied = false;
        this.ticksUntilNextAttack = 0;
        this.tentativeCount = 0;
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

            if (isActionPoint())
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
                applyPoison(target);
                this.tentativeCount += 1;
            }
        }
    }

    protected void applyPoison(LivingEntity target) {
        target.addEffect(new MobEffectInstance(MobEffects.POISON, this.poisonDuration, this.poisonStrength, false, true));
        this.isPoisonApplied = target.hasEffect(MobEffects.POISON);
    }

    protected  boolean isActionPoint() {
        return this.attackAnimationTick == (this.attackDuration - this.actionPoint);
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }
}
