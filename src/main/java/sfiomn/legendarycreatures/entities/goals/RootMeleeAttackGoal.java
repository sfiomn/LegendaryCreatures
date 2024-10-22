package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;
import sfiomn.legendarycreatures.registry.EffectRegistry;
import sfiomn.legendarycreatures.util.DamageSourceUtil;

import java.util.EnumSet;
import java.util.UUID;

import static sfiomn.legendarycreatures.api.ModDamageTypes.ROOT_ATTACK;

public class RootMeleeAttackGoal extends MoveToTargetGoal {
    protected static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("e20745c1-e432-40f2-b930-d08f1ef67508");
    private final AttributeModifier maxKnockBackResistance = new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, LegendaryCreatures.MOD_ID + ".rootAttack.knockbackResistance", 1000.0D, AttributeModifier.Operation.ADDITION);
    private AttributeInstance mobKnockBackResAttribute;
    private final int baseAttackDuration;
    private final int baseActionPoint;
    private boolean baseAttackDone;
    private final int rootAttackDuration;
    private final float damageEvery10Ticks;
    private final int coolDown;
    private final double stopAttackMobHealthPercent;
    private float mobHealth;
    private boolean shouldStopAttack;
    private int baseAttackAnimationTick;
    private int rootAttackAnimationTick;
    private int rootTick;
    private long lastUseTime;
    private LivingEntity rootTarget;

    public RootMeleeAttackGoal(AnimatedCreatureEntity mob, int baseAttackDuration, int baseActionPoint, int rootAttackDuration, float damageEvery10Ticks, double stopAttackMobHealthPercent, double speedModifier, int goalCoolDown) {
        super(mob, speedModifier, true);
        this.baseAttackDuration = baseAttackDuration;
        this.baseActionPoint = baseActionPoint;
        this.rootAttackDuration = rootAttackDuration;
        this.damageEvery10Ticks = damageEvery10Ticks;
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
        long time = this.mob.level().getGameTime();
        if (time - this.lastUseTime < coolDown) {
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
            return !this.shouldStopAttack;
        } else
            return false;
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.baseAttackAnimationTick = 0;
        this.rootAttackAnimationTick = 0;
        this.mobHealth = this.mob.getHealth();
        this.shouldStopAttack = false;
        this.baseAttackDone = false;
        this.rootTick = 0;
        this.mobKnockBackResAttribute = this.mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
    }

    public void stop() {
        super.stop();
        this.lastUseTime = this.mob.level().getGameTime();

        this.stopAttack();

        if (this.rootTarget != null)
            removeRootEffect(this.rootTarget);

        this.mob.setAggressive(false);
    }

    public void tick() {
        if (this.baseAttackAnimationTick > 0)
            this.baseAttackAnimationTick -= 1;
        if (this.rootAttackAnimationTick > 0)
            this.rootAttackAnimationTick -= 1;

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.mob.getLookControl().setLookAt(target.position());

            // Move to target
            if (this.rootAttackAnimationTick <= 0)
                super.tick();

            // Attack target
            double distToTargetSqr = this.mob.distanceToSqr(target);
            if (getAttackReachSqr(target) >= distToTargetSqr && !this.isAttacking()) {
                this.mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                this.startBaseAttack();
            }

            if (this.baseAttackDone && this.rootAttackAnimationTick == 0) {
                this.mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                this.startRootAttack();
            }

            if (this.mob.getAttackAnimation() == AnimatedCreatureEntity.BASE_ATTACK && getBaseActionPoint()) {
                this.executeBaseAttack(target);
            }

            if (this.mob.getAttackAnimation() == AnimatedCreatureEntity.ROOT_ATTACK) {
                if (this.rootTick-- <= 0) {
                    this.rootTick = 10;
                    this.executeRootAttack(target);
                }
            }

            if (this.baseAttackDone && getAttackReachSqr(target) * 1.5 < distToTargetSqr)
                this.shouldStopAttack = true;
        }
    }

    protected void startBaseAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.BASE_ATTACK);
        this.baseAttackAnimationTick = this.baseAttackDuration;
    }

    protected void startRootAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.ROOT_ATTACK);
        this.rootAttackAnimationTick = this.rootAttackDuration;
    }

    protected void stopAttack() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.NO_ANIMATION);
    }

    protected void executeBaseAttack(LivingEntity target) {
        if (target != null) {
            if (!this.isDamageSourceBlocked(target)) {
                addRootEffect(target);
                target.hurt(DamageSourceUtil.getDamageSource(this.mob.level(), ROOT_ATTACK), this.damageEvery10Ticks);
                this.rootTarget = target;
                this.baseAttackDone = true;
            } else {
                target.hurt(this.mob.damageSources().mobAttack(this.mob), this.damageEvery10Ticks);
                this.shouldStopAttack = true;
            }
        }
    }

    protected void executeRootAttack(LivingEntity target) {
        addRootEffect(target);
        target.hurt(DamageSourceUtil.getDamageSource(this.mob.level(), ROOT_ATTACK), this.damageEvery10Ticks);
    }

    protected void addRootEffect(LivingEntity target) {
        target.addEffect(new MobEffectInstance(EffectRegistry.ROOT.get(), 40, 0, false, true));
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

    protected boolean getBaseActionPoint() {
        return (this.baseAttackDuration - this.baseActionPoint) == this.baseAttackAnimationTick;
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }

    protected boolean isDamageSourceBlocked(LivingEntity target) {
        if (target.isBlocking()) {
            Vec3 vector3d2 = this.mob.position();
            Vec3 vector3d = target.getViewVector(1.0F);
            Vec3 vector3d1 = vector3d2.vectorTo(target.position()).normalize();
            vector3d1 = new Vec3(vector3d1.x, 0.0D, vector3d1.z);
            return vector3d1.dot(vector3d) < 0.0D;
        }
        return false;
    }
}
