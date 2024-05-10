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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.goals.DelayedMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.EffectRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nullable;

public class PeacockSpiderEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 16;
    private final int baseAttackActionPoint = 10;
    private final int minAttackDelayTicks = 40;
    private final int maxAttackDelayTicks = 40;
    private int hissSoundTick = 40;

    public PeacockSpiderEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 5;
        if (isLevel3())
            this.xpReward += 10;
        else if (isLevel2())
            this.xpReward += 5;
        this.maxUpStep = 1.0F;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 16)
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
    public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        ModifiableAttributeInstance healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        ModifiableAttributeInstance attackAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (this.isLevel2() || this.isLevel3()) {
            if (healthAttribute != null) {
                healthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_UUID, LegendaryCreatures.MOD_ID + ":peacock_spider_level2", 18, AttributeModifier.Operation.ADDITION));
                this.setHealth(1000);
            }
            if (attackAttribute != null) {
                if (this.isLevel3())
                    attackAttribute.addPermanentModifier(new AttributeModifier(ATTACK_DAMAGE_UUID, LegendaryCreatures.MOD_ID + ":peacock_spider_level3", 12, AttributeModifier.Operation.ADDITION));
                else
                    attackAttribute.addPermanentModifier(new AttributeModifier(ATTACK_DAMAGE_UUID, LegendaryCreatures.MOD_ID + ":peacock_spider_level2", 4, AttributeModifier.Operation.ADDITION));
            }
        }

        return super.finalizeSpawn(serverWorld, difficultyInstance, spawnReason, entityData, nbt);
    }

    @Override
    protected BodyController createBodyControl() {
        return new PeacockSpiderEntity.BodyHelperController(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new DelayedMeleeAttackGoal(this, minAttackDelayTicks, maxAttackDelayTicks, baseAttackDuration, baseAttackActionPoint, 20, 1.0, true){
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                boolean hit = super.executeAttack(target);
                if (hit) {
                    target.addEffect(new EffectInstance(Effects.CONFUSION, 200, 0, false, true));
                    target.addEffect(new EffectInstance(EffectRegistry.CONVULSION.get(), 200, 0, false, true));
                }
                return hit;
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.6, 40));
    }

    private float getMobLength() {
        return 1.4f;
    }

    @Override
    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (getAttackAnimation() == DELAY_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("startle", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();

        if (getAttackAnimation() == DELAY_ATTACK && this.hissSoundTick++ >= 40) {
            this.hissSoundTick = 0;
            this.level.playSound(null, new BlockPos(this.position()), SoundRegistry.PEACOCK_SPIDER_HISS.get(), SoundCategory.HOSTILE, 10.0F, 1.0F);
        }
    }

    public boolean isLevel3() {
        return getVariant() >= 9;
    }

    public boolean isLevel2() {
        return getVariant() >= 7 && getVariant() < 9;
    }

    @Override
    public AnimationBuilder getSprintAnimation() {
        return new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.3F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.PEACOCK_SPIDER_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.PEACOCK_SPIDER_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!this.isSprinting())
            this.playSound(SoundRegistry.PEACOCK_SPIDER_STEP.get(), 1.0F, 1.0F);
        else
            this.playSound(SoundRegistry.PEACOCK_SPIDER_RUN.get(), 1.0F, 1.0F);
    }

    class BodyHelperController extends BodyController {
        public BodyHelperController(MobEntity p_i49925_2_) {
            super(p_i49925_2_);
        }

        public void clientTick() {
            PeacockSpiderEntity.this.yHeadRot = PeacockSpiderEntity.this.yBodyRot;
            PeacockSpiderEntity.this.yBodyRot = PeacockSpiderEntity.this.yRot;
        }
    }
}
