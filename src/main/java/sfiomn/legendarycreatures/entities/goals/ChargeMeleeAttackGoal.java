package sfiomn.legendarycreatures.entities.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import sfiomn.legendarycreatures.entities.AnimatedCreatureEntity;

import java.util.*;

public class ChargeMeleeAttackGoal extends Goal {
    protected final AnimatedCreatureEntity mob;
    private final int attackDuration;
    private final int actionPoint;
    private final int coolDown;
    private final double minDistance;
    private final double speedModifier;
    private final double chargeCorrectionAngle;
    private final boolean mayDisableShield;
    private int updatePathTick;
    private Path path;
    private boolean pathUpdated;
    private int attackAnimationTick;
    private boolean hasAttacked;
    private long lastUseTime;
    private Vec3 chargeAxe;
    private BlockPos targetBlockPos;

    public ChargeMeleeAttackGoal(AnimatedCreatureEntity mob, int attackDuration, int hurtTick, int attackCoolDown, double minDistanceAttack, double speedModifier, double chargeCorrectionAngle, boolean mayDisableShield) {
        this.mob = mob;
        this.attackDuration = attackDuration;
        this.actionPoint = hurtTick;
        this.coolDown = attackCoolDown;
        this.minDistance = minDistanceAttack;
        this.speedModifier = speedModifier;
        this.chargeCorrectionAngle = chargeCorrectionAngle;
        this.mayDisableShield = mayDisableShield;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public boolean isAttacking() {
        return this.mob.getAttackAnimation() == AnimatedCreatureEntity.CHARGE_ATTACK;
    }

    public boolean canUse() {
        long time = this.mob.level().getGameTime();
        if (time - this.lastUseTime < coolDown || isAttacking()) {
            return false;
        } else {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                this.path = this.mob.getNavigation().createPath(target, 0);
                if (path != null && this.mob.getSensing().hasLineOfSight(target) && this.mob.distanceToSqr(target) > minDistance * minDistance) {
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
        } else if (this.mob.getDeltaMovement().length() <= 0.1)
            return false;
        return !hasAttacked ;
    }

    public void start() {
        this.mob.setAttackAnimation(AnimatedCreatureEntity.CHARGING);
        this.mob.setAggressive(true);
        this.attackAnimationTick = 0;
        this.hasAttacked = false;
        this.updatePathTick = 0;
        this.pathUpdated = false;
        this.chargeAxe = Vec3.ZERO;
        this.targetBlockPos = BlockPos.ZERO;
    }

    public void stop() {
        this.lastUseTime = this.mob.level().getGameTime();

        if (isAttacking())
            this.stopAttack();

        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            if (this.chargeAxe == Vec3.ZERO)
                this.chargeAxe = this.mob.position().subtract(target.position());
            if (this.targetBlockPos == BlockPos.ZERO)
                this.targetBlockPos = target.blockPosition();

            double distToTargetSqr = this.mob.distanceToSqr(target);
            double angleToTarget = this.getAngleToChargeAxe(target);

            // Update next target point
            if (this.mob.getSensing().hasLineOfSight(target) && angleToTarget <= this.chargeCorrectionAngle && target.blockPosition() != this.targetBlockPos && !isAttacking() && !hasAttacked) {
                this.targetBlockPos = target.blockPosition();
                this.pathUpdated = false;
            }

            // Move effects
            if (!hasAttacked) {
                if (!this.mob.level().isClientSide()) {
                    ((ServerLevel) this.mob.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.mob.getX(), this.mob.getY(), this.mob.getZ(), 5, this.mob.getBbWidth() / 4.0F, 0, this.mob.getBbWidth() / 4.0F, 0.01D);
                }
                if (this.mob.level().getGameTime() % 2L == 0L) {
                    this.mob.playSound(SoundEvents.HOGLIN_STEP, 0.5F, (this.mob.getRandom().nextFloat() - this.mob.getRandom().nextFloat()) * 0.2f + 1.0f);
                }
            }

            // Move to new target point
            if (getAttackReachSqr(target) / 2.0f < distToTargetSqr) {
                if (--updatePathTick <= 0 && !pathUpdated) {
                    this.pathUpdated = true;
                    if (this.move()) {
                        this.updatePathTick = 1 + this.mob.getRandom().nextInt(5);
                    }
                    // Increase path calculation interval if target far away (similar to melee attack)
                    if (distToTargetSqr > 1024.0D) {
                        this.updatePathTick += 20;
                    } else if (distToTargetSqr > 256.0D) {
                        this.updatePathTick += 10;
                    }
                }
            } else {
                this.mob.getNavigation().stop();
            }

            // Attack target - ensure only 1 attack will be triggered per charge
            if ((getAttackReachSqr(target) >= distToTargetSqr || (this.path.getNextNodeIndex() >= (this.path.getNodeCount() - 1) && angleToTarget > this.chargeCorrectionAngle)) && !isAttacking() && !hasAttacked)
                this.startAttack();

            if (this.attackAnimationTick > 0)
                this.attackAnimationTick -= 1;
            if (this.attackAnimationTick == 0 && isAttacking())
                this.stopAttack();

            if (isActionPoint()) {
                this.executeAttack(target);
            }
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

    protected void executeAttack(LivingEntity target) {
        if (target != null) {
            this.doHurt(target);
        }
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }

    protected  boolean isActionPoint() {
        return this.attackAnimationTick == (this.attackDuration - this.actionPoint);
    }

    protected double getAngleToChargeAxe(LivingEntity target) {
        Vec3 currentPosToTargetVector = this.mob.position().subtract(target.position());
        return Math.acos((currentPosToTargetVector.dot(this.chargeAxe)) / (currentPosToTargetVector.length() * this.chargeAxe.length())) / Math.PI * 180;
    }

    // Rewrite of moveTo navigation method to go up to the position of the target, instead of at reach distance 1
    protected boolean move() {
        Path path = this.mob.getNavigation().createPath(this.targetBlockPos, 0);
        if (path != null)
            this.path = path;
        return path != null && this.mob.getNavigation().moveTo(this.path, this.speedModifier);
    }

    protected void doHurt(LivingEntity target) {
        float damage = (float)this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockBack = Mth.clamp(this.mob.getSpeed() / 0.7f, 0.2f, 3.0f);
        boolean flag = target.hurt(mob.damageSources().mobAttack(mob), damage);
        if (target instanceof Player player) {
            this.mayDisableShield(player, player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
        }
        if (flag) {
            target.knockback(knockBack, this.chargeAxe.x, this.chargeAxe.z);
            double knockBackResistance = Math.max(0.0, 1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.4f * knockBackResistance, 0.0));
            mob.setLastHurtMob(target);
        }
    }

    protected void mayDisableShield(Player player, ItemStack handItem) {
        if (this.mayDisableShield && !handItem.isEmpty() && handItem.getItem() == Items.SHIELD) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this.mob) * 0.05F;
            if (this.mob.getRandom().nextFloat() < f) {
                //player.stopUsingItem();
                player.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.mob.level().broadcastEntityEvent(player, (byte)30);
            }
        }
    }
}
