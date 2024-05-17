package sfiomn.legendarycreatures.entities;

import com.minecraftabnormals.atmospheric.core.other.AtmosphericDamageSources;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Random;
import java.util.UUID;

public abstract class AnimatedCreatureEntity extends CreatureEntity implements IAnimatable {
    protected static final UUID MAX_HEALTH_UUID = UUID.fromString("4133085c-5129-4018-9ea1-de2b2190ecc1");
    protected static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("1d16a9ed-6ef2-4547-b14c-f3e3e7ec273e");

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(AnimatedCreatureEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> ATTACK_ANIMATION = EntityDataManager.defineId(AnimatedCreatureEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> SPAWN_TIMER = EntityDataManager.defineId(AnimatedCreatureEntity.class, DataSerializers.INT);

    public static final int NO_ANIMATION = 0;
    public static final int BASE_ATTACK = 1;
    public static final int CHARGE_ATTACK = 2;
    public static final int CHARGING = 3;
    public static final int ROOT_ATTACK = 4;
    public static final int EFFECT_ATTACK = 5;
    public static final int DELAY_ATTACK = 6;
    public static final int DISTANCE_ATTACK = 7;

    protected AnimatedCreatureEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(VARIANT, 0);
        this.entityData.define(ATTACK_ANIMATION, NO_ANIMATION);
        this.entityData.define(SPAWN_TIMER, 0);
    }

    public int getAttackAnimation() {
        return this.entityData.get(ATTACK_ANIMATION);
    }

    public void setAttackAnimation(int animation) {
        this.entityData.set(ATTACK_ANIMATION, animation);
    }

    public int getSpawnTimer() {
        return entityData.get(SPAWN_TIMER);
    }

    public void setSpawnTimer(int spawnTimer) {
        entityData.set(SPAWN_TIMER, spawnTimer);
    }

    public int getVariant() {
        // Return range [1 - 10]
        return entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("variant", getVariant());
        nbt.putInt("spawnTimer", getSpawnTimer());
    }

    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        setVariant(nbt.getByte("variant"));
        setSpawnTimer(nbt.getInt("spawnTimer"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        else if (source == DamageSource.DROWN)
            return false;
        else if (source == DamageSource.CACTUS)
            return false;
        else if (source == DamageSource.IN_WALL)
            return false;
        else if (source == DamageSource.SWEET_BERRY_BUSH)
            return false;
        else if (source == DamageSource.ANVIL)
            return false;
        else if (source == DamageSource.DRAGON_BREATH)
            return false;
        else if (LegendaryCreatures.atmosphericLoaded) {
            if (source == AtmosphericDamageSources.ALOE_LEAVES)
                return false;
            else if (source == AtmosphericDamageSources.BARREL_CACTUS)
                return false;
            else if (source == AtmosphericDamageSources.YUCCA_BRANCH)
                return false;
            else if (source == AtmosphericDamageSources.YUCCA_FLOWER)
                return false;
            else if (source == AtmosphericDamageSources.YUCCA_LEAVES)
                return false;
            else if (source == AtmosphericDamageSources.YUCCA_SAPLING)
                return false;
        }
        return super.hurt(source, amount);
    }

    public <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        boolean isMoving = !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F);
        if (getSpawnTimer() > 0)
            return PlayState.CONTINUE;

        if (getDeathAnimation() != null && this.dead) {
            event.getController().setAnimation(getDeathAnimation());
            return PlayState.CONTINUE;
        } else if (isMoving) {
            if (this.isInWaterOrBubble()) {
                if (getSwimAnimation() != null) {
                    event.getController().setAnimation(getSwimAnimation());
                    return PlayState.CONTINUE;
                } else if (getWalkAnimation() != null) {
                    event.getController().setAnimation(getWalkAnimation());
                    return PlayState.CONTINUE;
                }
            } else if (getSprintAnimation() != null && this.isSprinting()) {
                event.getController().setAnimation(getSprintAnimation());
                return PlayState.CONTINUE;
            } else if (getWalkAnimation() != null) {
                event.getController().setAnimation(getWalkAnimation());
                return PlayState.CONTINUE;
            }
        }
        event.getController().setAnimation(getIdleAnimation());
        return PlayState.CONTINUE;
    }

    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    public <E extends IAnimatable> PlayState spawnPredicate(AnimationEvent<E> event) {
        if (getSpawnAnimation() != null && getSpawnTimer() > 0) {
            event.getController().setAnimation(getSpawnAnimation());
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.addAnimationController(new AnimationController<>(this, "attacking", 4, this::attackingPredicate));
        data.addAnimationController(new AnimationController<>(this, "spawn", 0, this::spawnPredicate));
    }

    @Override
    public void tick() {
        if (getSpawnTimer() > 5 && level != null) {
            Random random = this.getRandom();
            for(int i = 0; i < 6; ++i) {
                double x = this.getX() + 0.5 + (double) ((random.nextFloat() * 0.5F) - 1.0);
                double y = this.getY() + 0.1;
                double z = this.getZ() + 0.5 + (double) ((random.nextFloat() * 0.5F) - 1.0);
                BlockState blockstate = this.level.getBlockState(new BlockPos(x, y, z).below());
                if (blockstate.getRenderShape() != BlockRenderType.INVISIBLE && this.level.isClientSide) {
                    this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockstate), x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        super.tick();
    }

    public static boolean checkCreatureSpawnRules(EntityType<? extends MobEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.below();
        return spawnReason == SpawnReason.SPAWNER || world.getBlockState(blockpos).isFaceSturdy(world, blockpos, Direction.UP);
    }

    public static boolean checkFlyingCreatureSpawnRules(EntityType<? extends MobEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.HOSTILE_HURT;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public AnimationBuilder getSpawnAnimation() {
        return null;
    }
    public AnimationBuilder getWalkAnimation() { return new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP);}
    public AnimationBuilder getIdleAnimation() { return new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);}
    public AnimationBuilder getDeathAnimation() {
        return null;
    }
    public AnimationBuilder getSwimAnimation() {
        return null;
    }
    public AnimationBuilder getSprintAnimation() {
        return null;
    }

    @Override
    protected void customServerAiStep() {
        if (this.getSpawnTimer() > 0) {
            this.setSpawnTimer(getSpawnTimer() - 1);
        }

        if (this.isSprinting() != this.isAggressive())
            this.setSprinting(this.isAggressive());

        super.customServerAiStep();
    }
}
