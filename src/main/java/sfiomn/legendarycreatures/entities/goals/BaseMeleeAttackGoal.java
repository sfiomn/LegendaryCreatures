package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.SoundEvent;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.EnumSet;

public class BaseMeleeAttackGoal extends MoveToTargetGoal {
    private final int attackDuration;
    private final int actionPoint;
    private final int coolDown;
    private int attackAnimationTick;
    private long lastUseTime;
    private int ticksUntilNextAttack;

    public BaseMeleeAttackGoal(AnimatedCreatureEntity mob, int attackDuration, int hurtTick, int attackCoolDown, double speedModifier, boolean followingEvenIfNotSeen ) {
        super(mob, speedModifier, followingEvenIfNotSeen);
        this.attackDuration = attackDuration;
        this.actionPoint = hurtTick;
        this.coolDown = attackCoolDown;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() == AnimatedCreatureEntity.BASE_ATTACK;
    }

    public boolean canUse() {
        long time = this.mob.level.getGameTime();
        if (time - this.lastUseTime < 20 || isAttacking()) {
            return false;
        } else {
            return super.canUse();
        }
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
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
            double distToTargetSqr = this.mob.distanceToSqr(target);

            // Move to target
            super.tick();

            // Attack target
            if (this.ticksUntilNextAttack == 0 && getAttackReachSqr(target) >= distToTargetSqr && !isAttacking()) {
                this.mob.lookAt(EntityAnchorArgument.Type.EYES, target.position());
                this.startAttack();
            }

            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            if (isActionPoint() && distToTargetSqr <= getAttackReachSqr(target))
                this.executeAttack(target);
        }
    }

    protected void startAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.BASE_ATTACK);
        this.attackAnimationTick = this.attackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
        this.ticksUntilNextAttack = this.coolDown;
    }

    protected boolean executeAttack(LivingEntity target) {
        return mob.doHurtTarget(target);
    }

    protected  boolean isActionPoint() {
        return this.attackAnimationTick == (this.attackDuration - this.actionPoint);
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }
}
