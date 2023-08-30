package sfiomn.legendarycreatures.entities;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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

public abstract class AnimatedCreatureEntity extends CreatureEntity implements IAnimatable {
    protected BlockState fromBlockStateSpawn;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(AnimatedCreatureEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> ATTACK_ANIMATION = EntityDataManager.defineId(AnimatedCreatureEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> SPAWN_TIMER = EntityDataManager.defineId(AnimatedCreatureEntity.class, DataSerializers.INT);

    public static final int NO_ANIMATION = 0;
    public static final int BASE_ATTACK = 1;
    public static final int CHARGE_ATTACK = 2;
    public static final int HOLD_ATTACK = 3;

    protected AnimatedCreatureEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    public AnimatedCreatureEntity fromBlock(BlockState fromBlockStateSpawn) {
        this.fromBlockStateSpawn = fromBlockStateSpawn;
        return this;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        int variant = random.nextInt(10);

        this.entityData.define(VARIANT, variant);
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
        if (source == DamageSource.DROWN)
            return false;
        return super.hurt(source, amount);
    }

    public <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        boolean isMoving = !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F);
        if (getDeathAnimation() != null && this.dead) {
            event.getController().setAnimation(getDeathAnimation());
            return PlayState.CONTINUE;
        } else if (isMoving) {
            if (getSwimAnimation() != null && this.isInWaterOrBubble()) {
                event.getController().setAnimation(getSwimAnimation());
                return PlayState.CONTINUE;
            } else if (getSprintAnimation() != null && this.isAggressive()) {
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

        super.customServerAiStep();
    }

    @Override
    public void tick() {
        if (getSpawnTimer() > 0 && level != null) {
            //MinecraftClient.getInstance().particleManager.addBlockBreakParticles(this.getBlockPos().down(), world.getBlockState(this.getBlockPos().down()));
            Random random = this.getRandom();
            BlockState blockstate = fromBlockStateSpawn;
            if (fromBlockStateSpawn != null && blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
                for(int i = 0; i < 30; ++i) {
                    double d0 = this.getX() + (double) ((random.nextFloat() * 1.4F) - 0.7);
                    double d1 = this.getY();
                    double d2 = this.getZ() + (double) ((random.nextFloat() * 1.4F) - 0.7);
                    this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        super.tick();
    }
}
