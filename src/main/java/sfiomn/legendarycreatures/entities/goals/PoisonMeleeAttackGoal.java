package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;
import sfiomn.legendarycreatures.entities.MojoEntity;

import java.util.EnumSet;

public class PoisonMeleeAttackGoal extends MoveToTargetGoal {
    private final int poisonDuration;
    private final int poisonStrength;
    private final int attackDuration;
    private final int actionPoint;
    private final int attackCoolDown;
    private final int coolDown;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextAttack;
    private boolean isTargetPoisoned;

    public PoisonMeleeAttackGoal(AnimatedCreatureEntity mob, int poisonDuration, int poisonStrength, int attackDuration, int hurtTick, int attackCoolDown, double speedModifier, boolean followingEvenIfNotSeen, int goalCoolDown) {
        super(mob, speedModifier, followingEvenIfNotSeen);
        this.poisonDuration = poisonDuration;
        this.poisonStrength = poisonStrength;
        this.attackDuration = attackDuration;
        this.actionPoint = hurtTick;
        this.attackCoolDown = attackCoolDown;
        this.coolDown = goalCoolDown;
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
            return !isTargetPoisoned;
        else
            return false;
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.isTargetPoisoned = false;
        this.ticksUntilNextAttack = 0;
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
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

            // Move to target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            super.tick();

            // Attack target
            if (this.ticksUntilNextAttack == 0 && getAttackReachSqr(target) >= distToTargetSqr && !isAttacking())
                this.startAttack();

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            if (isActionPoint() && distToTargetSqr <= getAttackReachSqr(target))
                this.executeAttack(target);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.POISON_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        this.ticksUntilNextAttack = this.attackCoolDown;
    }

    protected void executeAttack(LivingEntity target) {
        if (target != null) {
            LegendaryCreatures.LOGGER.debug("execute poison attack");
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
