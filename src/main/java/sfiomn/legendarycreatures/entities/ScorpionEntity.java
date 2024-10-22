package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.entities.goals.PoisonMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class ScorpionEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 16;
    private final int baseAttackActionPoint = 10;
    private final int tailAttackDuration = 20;
    private final int tailAttackActionPoint = 7;

    private final RawAnimation RUN_ANIM = RawAnimation.begin().thenPlay("run");
    private final RawAnimation CLAWS_ANIM = RawAnimation.begin().thenPlay("claws");
    private final RawAnimation TAIL_ANIM = RawAnimation.begin().thenPlay("tail");

    public ScorpionEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        if (isLevel2())
            this.xpReward = 10;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        if (!this.level().isClientSide) {
            // 70% to have babies
            boolean hasBabies = getRandom().nextInt(100) >= 70;

            // 10% to be level 2 scorpion
            if (getRandom().nextInt(100) >= 90)
                if (hasBabies)
                    this.setVariant(8);
                else
                    this.setVariant(2);
            else if (hasBabies)
                this.setVariant(7);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        PoisonMeleeAttackGoal poisonMeleeAttackGoal = new PoisonMeleeAttackGoal(this, 200, 0, 3, tailAttackDuration, tailAttackActionPoint, 5, 1.0, true, 200){
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected void executeAttack(LivingEntity target) {
                super.executeAttack(target);
                this.mob.playSound(SoundRegistry.SCORPION_TAIL_ATTACK_HIT.get(), 1.0f, 1.0f);
            }
        };

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, poisonMeleeAttackGoal);
        this.goalSelector.addGoal(4, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 5, 1.0, true){
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                this.mob.playSound(SoundRegistry.SCORPION_CLAWS_ATTACK_HIT.get(), 1.0f, 1.0f);
                return super.executeAttack(target);
            }

            @Override
            public boolean canContinueToUse() {
                if (poisonMeleeAttackGoal.canUse())
                    return false;
                return super.canContinueToUse();
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.6, 40));
    }

    private float getMobLength() {
        return 1.5f;
    }

    @Override
    public <E extends GeoAnimatable> PlayState attackingPredicate(AnimationState<E> state) {
        if (getAttackAnimation() == BASE_ATTACK) {
            return state.setAndContinue(CLAWS_ANIM);
        } else if (getAttackAnimation() == EFFECT_ATTACK) {
            return state.setAndContinue(TAIL_ANIM);
        }

        state.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag nbt) {
        AttributeInstance healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attackAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (this.isLevel2()) {
            if (healthAttribute != null) {
                healthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_UUID, LegendaryCreatures.MOD_ID + ":scorpion_level2", healthAttribute.getBaseValue() * 2, AttributeModifier.Operation.ADDITION));
                this.setHealth(1000);
            }
            if (attackAttribute != null) {
                attackAttribute.addPermanentModifier(new AttributeModifier(ATTACK_DAMAGE_UUID, LegendaryCreatures.MOD_ID + ":scorpion_level2", attackAttribute.getBaseValue() * 2, AttributeModifier.Operation.ADDITION));
            }
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, nbt);
    }

    @Override
    public Component getName() {
        String descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".scorpion";
        if (isLevel2())
            descriptionId = "entity." + LegendaryCreatures.MOD_ID + ".scorpion2";
        return Component.translatable(descriptionId);
    }

    public boolean isLevel2() {
        return getVariant() == 2 || getVariant() == 8;
    }

    @Override
    public RawAnimation getSprintAnimation() {
        return RUN_ANIM;
    }

    public boolean hasBabies() {
        return this.getVariant() >= 7;
    }

    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose p_21131_, @NotNull EntityDimensions p_21132_) {
        return 0.35F;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.SCORPION_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.SCORPION_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.SCORPION_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundRegistry.SCORPION_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide && this.isDeadOrDying() && !this.isRemoved() && hasBabies()) {
            int nbBabies = 1 + this.random.nextInt(3);

            for(int l = 0; l < nbBabies; ++l) {
                float f1 = ((float)(l % 2) - 0.5F);
                float f2 = ((float)(l / 2) - 0.5F);
                ScorpionBabyEntity scorpionBabyEntity = EntityTypeRegistry.SCORPION_BABY.get().create(this.level());
                if (scorpionBabyEntity == null)
                    return;
                if (this.isPersistenceRequired()) {
                    scorpionBabyEntity.setPersistenceRequired();
                }

                this.level().playSound(null, this.blockPosition(), SoundEvents.SILVERFISH_STEP, SoundSource.HOSTILE, 10.0F, 1.0F);
                scorpionBabyEntity.setInvulnerable(this.isInvulnerable());
                if (isLevel2()) {
                    scorpionBabyEntity.setVariant(2);
                }
                scorpionBabyEntity.moveTo(this.getX() + (double)f1, this.getY() + 0.5D, this.getZ() + (double)f2, this.random.nextFloat() * 360.0F, 0.0F);
                this.level().addFreshEntity(scorpionBabyEntity);
            }
        }

        super.remove(reason);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        if (isLevel2())
            return new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/scorpion_level2");
        return super.getDefaultLootTable();
    }
}
