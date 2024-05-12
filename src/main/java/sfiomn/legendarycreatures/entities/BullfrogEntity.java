package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.entities.goals.RangedMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nullable;


public class BullfrogEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 10;
    private final int baseAttackActionPoint = 5;
    private final int baseDistanceAttackDuration = 10;
    private final int baseDistanceAttackActionPoint = 5;
    public final int SHORT_RANGED_ATTACK = 7;
    public final int shortRangedAttackDistance = 6;
    public final int MIDDLE_RANGED_ATTACK = 8;
    public final int middleRangedAttackDistance = 8;
    public final int LONG_RANGED_ATTACK = 9;
    public final int longRangedAttackDistance = 10;
    public BullfrogEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 12;
        if (isLevel3())
            this.xpReward = 20;
        else if (isLevel2())
            this.xpReward = 10;
        this.maxUpStep = 1.0F;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        int randomVariant = getRandom().nextInt(100);
        if (randomVariant >= 96)
            setVariant(9);
        else if (randomVariant >= 76)
            setVariant(7);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        ModifiableAttributeInstance healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        ModifiableAttributeInstance attackAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (this.isLevel2()) {
            if (attackAttribute != null) {
                attackAttribute.addPermanentModifier(new AttributeModifier(ATTACK_DAMAGE_UUID, LegendaryCreatures.MOD_ID + ":bullfrog_level2", 5, AttributeModifier.Operation.ADDITION));
            }
            if (healthAttribute != null) {
                healthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_UUID, LegendaryCreatures.MOD_ID + ":bullfrog_level2", 15, AttributeModifier.Operation.ADDITION));
                this.setHealth(1000);
            }
        } else if (this.isLevel3()) {
            if (attackAttribute != null) {
                attackAttribute.addPermanentModifier(new AttributeModifier(ATTACK_DAMAGE_UUID, LegendaryCreatures.MOD_ID + ":bullfrog_level3", 10, AttributeModifier.Operation.ADDITION));
            }
            if (healthAttribute != null) {
                healthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_UUID, LegendaryCreatures.MOD_ID + ":bullfrog_level3", 35, AttributeModifier.Operation.ADDITION));
                this.setHealth(1000);
            }
        }

        return super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, nbt);
    }

    @Override
    protected BodyController createBodyControl() {
        return new BullfrogEntity.BodyHelperController(this);
    }

    @Override
    protected ITextComponent getTypeName() {
        String descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".bullfrog";
        if (isLevel2())
            descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".bullfrog2";
        else if (isLevel3())
            descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".bullfrog3";
        return new TranslationTextComponent(descriptionId);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        RangedMeleeAttackGoal rangedMeleeAttackGoal = new RangedMeleeAttackGoal(this, baseDistanceAttackDuration, baseDistanceAttackActionPoint, 100, shortRangedAttackDistance, longRangedAttackDistance + 1.0f, 1.0, true) {
            @Override
            protected void startAttack(LivingEntity target) {
                double distToTargetSqr = this.mob.distanceToSqr(target);
                if (distToTargetSqr < (middleRangedAttackDistance - 1.0f) * (middleRangedAttackDistance - 1.0f) + target.getBbWidth()) {
                    this.mob.setAttackAnimation(SHORT_RANGED_ATTACK);
                } else if (distToTargetSqr < (longRangedAttackDistance - 1.0f) * (longRangedAttackDistance - 1.0f) + target.getBbWidth()) {
                    this.mob.setAttackAnimation(MIDDLE_RANGED_ATTACK);
                } else {
                    this.mob.setAttackAnimation(LONG_RANGED_ATTACK);
                }
                this.attackAnimationTick = this.attackDuration;
                this.mob.playSound(SoundRegistry.BULLFROG_TONGUE_ATTACK.get(), 1.0f, 1.0f);
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                boolean hit = super.executeAttack(target);
                if (hit) {
                    if (isLevel3())
                        target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 2, false, true));

                    Vector3d targetToMobVector = this.mob.position().subtract(target.position());
                    Vector3d vector3d = target.isOnGround() ? targetToMobVector.scale(0.18) : targetToMobVector.scale(0.13);
                    target.setDeltaMovement(vector3d.x, 0.5D, vector3d.z);
                    target.hasImpulse = true;
                    if (target instanceof ServerPlayerEntity)
                        ((ServerPlayerEntity) target).connection.send(new SEntityVelocityPacket(target));
                }
                return hit;
            }

            @Override
            public boolean isAttacking() {
                return this.mob.getAttackAnimation() == SHORT_RANGED_ATTACK ||
                        this.mob.getAttackAnimation() == MIDDLE_RANGED_ATTACK ||
                        this.mob.getAttackAnimation() == LONG_RANGED_ATTACK;
            }
        };

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, rangedMeleeAttackGoal);
        this.goalSelector.addGoal(8, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 5, 1.0, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                this.mob.playSound(SoundRegistry.BULLFROG_ATTACK.get(), 1.0f, 1.0f);
                boolean hit = super.executeAttack(target);
                if (hit && (isLevel2() || isLevel3())) {
                    target.addEffect(new EffectInstance(Effects.POISON, 150, 1, false, true));
                }
                return hit;
            }

            @Override
            public boolean canContinueToUse() {
                boolean canContinueToUse = super.canContinueToUse();
                if (canContinueToUse)
                    return !rangedMeleeAttackGoal.canUse();
                return false;
            }
        });
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.6, 40));
    }

    private float getMobLength() {
        return 1.4f;
    }

    @Override
    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (getAttackAnimation() == SHORT_RANGED_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tongue", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (getAttackAnimation() == MIDDLE_RANGED_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tongue2", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (getAttackAnimation() == LONG_RANGED_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tongue3", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    public boolean isLevel3() {
        return getVariant() >= 9;
    }

    public boolean isLevel2() {
        return getVariant() >= 7 && getVariant() < 9;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.BULLFROG_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.BULLFROG_IDLE.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundRegistry.BULLFROG_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public AnimationBuilder getSprintAnimation() {
        return new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    }

    class BodyHelperController extends BodyController {
        public BodyHelperController(MobEntity p_i49925_2_) {
            super(p_i49925_2_);
        }

        public void clientTick() {
            BullfrogEntity.this.yHeadRot = BullfrogEntity.this.yBodyRot;
            BullfrogEntity.this.yBodyRot = BullfrogEntity.this.yRot;
        }
    }
}
