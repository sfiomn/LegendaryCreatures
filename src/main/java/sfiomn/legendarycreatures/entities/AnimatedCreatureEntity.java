package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public abstract class AnimatedCreatureEntity extends PathfinderMob implements GeoEntity {
    protected static final UUID MAX_HEALTH_UUID = UUID.fromString("4133085c-5129-4018-9ea1-de2b2190ecc1");
    protected static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("1d16a9ed-6ef2-4547-b14c-f3e3e7ec273e");

    private final AnimatableInstanceCache instanceCache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(AnimatedCreatureEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_ANIMATION = SynchedEntityData.defineId(AnimatedCreatureEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SPAWN_EFFECT = SynchedEntityData.defineId(AnimatedCreatureEntity.class, EntityDataSerializers.BOOLEAN);

    private final RawAnimation WALK_ANIM = RawAnimation.begin().thenPlay("walk");
    private final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("idle");

    public static final int NO_ANIMATION = 0;
    public static final int BASE_ATTACK = 1;
    public static final int CHARGE_ATTACK = 2;
    public static final int CHARGING = 3;
    public static final int ROOT_ATTACK = 4;
    public static final int EFFECT_ATTACK = 5;
    public static final int DELAY_ATTACK = 6;
    public static final int DISTANCE_ATTACK = 7;

    protected AnimatedCreatureEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(VARIANT, 0);
        this.entityData.define(ATTACK_ANIMATION, NO_ANIMATION);
        this.entityData.define(SPAWN_EFFECT, Boolean.FALSE);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        if (spawnType.equals(MobSpawnType.SPAWN_EGG))
            setSpawnEffect(true);

        return super.finalizeSpawn(level, difficultyInstance, spawnType, spawnGroupData, tag);
    }

    public int getAttackAnimation() {
        return this.entityData.get(ATTACK_ANIMATION);
    }

    public void setAttackAnimation(int animation) {
        this.entityData.set(ATTACK_ANIMATION, animation);
    }

    public int getVariant() {
        // Return range [1 - 10]
        return entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    public void setSpawnEffect(boolean spawnEffect) {
        entityData.set(SPAWN_EFFECT, spawnEffect);
    }

    public boolean hasSpawnEffect() {
        return entityData.get(SPAWN_EFFECT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("variant", getVariant());
        nbt.putBoolean("spawn_effect", hasSpawnEffect());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        setVariant(nbt.getInt("variant"));
        setSpawnEffect(nbt.getBoolean("spawn_effect"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        else if (source.is(DamageTypes.DROWN))
            return false;
        else if (source.is(DamageTypes.CACTUS))
            return false;
        else if (source.is(DamageTypes.IN_WALL))
            return false;
        else if (source.is(DamageTypes.SWEET_BERRY_BUSH))
            return false;
        else if (source.is(DamageTypes.FALLING_ANVIL))
            return false;
        else if (source.is(DamageTypes.DRAGON_BREATH))
            return false;
        return super.hurt(source, amount);
    }

    public <E extends GeoAnimatable> PlayState movementPredicate(AnimationState<E> state) {
        if (hasSpawnEffect() && this.tickCount < getSpawnAnimationTicks())
            return PlayState.CONTINUE;

        if (getDeathAnimation() != null && this.isDeadOrDying()) {
            return state.setAndContinue(getDeathAnimation());
        } else if (state.isMoving()) {
            if (this.isInWaterOrBubble()) {
                if (getSwimAnimation() != null) {
                    return state.setAndContinue(getSwimAnimation());
                } else if (getWalkAnimation() != null) {
                    return state.setAndContinue(getWalkAnimation());
                }
            } else if (getSprintAnimation() != null && this.isSprinting()) {
                return state.setAndContinue(getSprintAnimation());
            } else if (getWalkAnimation() != null) {
                return state.setAndContinue(getWalkAnimation());
            }
        }
        return state.setAndContinue(getIdleAnimation());
    }

    public <E extends GeoAnimatable> PlayState attackingPredicate(AnimationState<E> state) {
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        if (getSpawnAnimationTicks() > 0 && hasSpawnEffect())
            controllerRegistrar.add(DefaultAnimations.getSpawnController(this, (animationState) -> this, hasSpawnEffect() ? getSpawnAnimationTicks(): 0));
        controllerRegistrar.add(new AnimationController<>(this, "Movement", 4, this::movementPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "Attack", 4, this::attackingPredicate));
    }

    @Override
    public void tick() {
        if (hasSpawnEffect() && this.tickCount < this.getSpawnAnimationTicks() * 0.6) {
            RandomSource random = this.getRandom();
            for(int i = 0; i < 6; ++i) {
                double x = this.getX() + 0.5 + ((random.nextFloat() * 0.5F) - 1.0);
                double y = this.getY() + 0.1;
                double z = this.getZ() + 0.5 + ((random.nextFloat() * 0.5F) - 1.0);
                BlockState blockstate = this.level().getBlockState(new BlockPos((int) x, (int) y, (int) z).below());
                if (blockstate.getRenderShape() != RenderShape.INVISIBLE && this.level().isClientSide) {
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        if (hasSpawnEffect() && this.tickCount > this.getSpawnAnimationTicks() + 5)
            setSpawnEffect(false);

        super.tick();
    }

    public static boolean checkHostileCreatureDaySpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBrightness(LightLayer.SKY, pos) > 8 && level.getDifficulty() != Difficulty.PEACEFUL;
    }

    public static boolean checkHostileCreatureNoSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
    }

    public static boolean checkPeacefulFlyingCreatureSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.HOSTILE_HURT;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.instanceCache;
    }

    public int getSpawnAnimationTicks() {
        return 0;
    }
    public RawAnimation getWalkAnimation() { return WALK_ANIM;}
    public RawAnimation getIdleAnimation() { return IDLE_ANIM;}
    public RawAnimation getDeathAnimation() {
        return null;
    }
    public RawAnimation getSwimAnimation() {
        return null;
    }
    public RawAnimation getSprintAnimation() {
        return null;
    }

    @Override
    protected void customServerAiStep() {
        if (this.isSprinting() != this.isAggressive())
            this.setSprinting(this.isAggressive());

        super.customServerAiStep();
    }
}
