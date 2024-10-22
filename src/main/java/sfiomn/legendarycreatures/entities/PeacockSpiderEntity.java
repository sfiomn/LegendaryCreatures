package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.goals.DelayedMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.EffectRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class PeacockSpiderEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 16;
    private final int baseAttackActionPoint = 10;
    private final int attackDelayTicks = 40;
    private int hissSoundTick = 40;

    private final RawAnimation RUN_ANIM = RawAnimation.begin().thenPlay("run");
    private final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack");
    private final RawAnimation STARTLE_ANIM = RawAnimation.begin().thenPlay("startle");

    public PeacockSpiderEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        if (isLevel3())
            this.xpReward = 20;
        else if (isLevel2())
            this.xpReward = 10;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        if (!this.level().isClientSide) {
            int randomVariant = getRandom().nextInt(100);
            if (randomVariant >= 96)
                setVariant(9);
            else if (randomVariant >= 76)
                setVariant(7);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag nbt) {
        AttributeInstance healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attackAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
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

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, nbt);
    }

    @Override
    public Component getName() {
        String descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".peacock_spider";
        if (isLevel2())
            descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".peacock_spider2";
        else if (isLevel3())
            descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".peacock_spider3";
        return Component.translatable(descriptionId);
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new PeacockSpiderEntity.BodyHelperController(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new DelayedMeleeAttackGoal(this, attackDelayTicks, baseAttackDuration, baseAttackActionPoint, 5, 1.0, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                boolean hit = super.executeAttack(target);
                if (hit) {
                    target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, true));
                    target.addEffect(new MobEffectInstance(EffectRegistry.CONVULSION.get(), 200, 0, false, true));
                }
                return hit;
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.6, 40));
    }

    private float getMobLength() {
        return 1.4f;
    }

    @Override
    public <E extends GeoAnimatable> PlayState attackingPredicate(AnimationState<E> state) {
        if (getAttackAnimation() == BASE_ATTACK) {
            return state.setAndContinue(ATTACK_ANIM);
        } else if (getAttackAnimation() == DELAY_ATTACK) {
            return state.setAndContinue(STARTLE_ANIM);
        }

        state.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public void tick() {
        super.tick();

        if (getAttackAnimation() == DELAY_ATTACK && this.hissSoundTick++ >= 40) {
            this.hissSoundTick = 0;
            this.level().playSound(null, this.blockPosition(), SoundRegistry.PEACOCK_SPIDER_HISS.get(), SoundSource.HOSTILE, 10.0F, 1.0F);
        }
    }

    public boolean isLevel3() {
        return getVariant() >= 9;
    }

    public boolean isLevel2() {
        return getVariant() >= 7 && getVariant() < 9;
    }

    @Override
    public RawAnimation getSprintAnimation() {
        return RUN_ANIM;
    }

    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose p_21131_, @NotNull EntityDimensions p_21132_) {
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

    class BodyHelperController extends BodyRotationControl {
        public BodyHelperController(Mob p_i49925_2_) {
            super(p_i49925_2_);
        }

        public void clientTick() {
            PeacockSpiderEntity.this.yHeadRot = PeacockSpiderEntity.this.yBodyRot;
            PeacockSpiderEntity.this.yBodyRot = PeacockSpiderEntity.this.getYRot();
        }
    }

    @Override
    protected @NotNull ResourceLocation getDefaultLootTable() {
        if (isLevel2())
            return new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/peacock_spider_level2");
        else if (isLevel3())
            return new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/peacock_spider_level3");
        return super.getDefaultLootTable();
    }
}
