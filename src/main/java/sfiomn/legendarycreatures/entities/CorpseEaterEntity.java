package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.blocks.DoomFireBlock;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.BlockRegistry;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nullable;

public class CorpseEaterEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 15;
    private final int baseAttackActionPoint = 7;
    private final int spawnTimerInTicks = 20;
    public CorpseEaterEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 5;
        this.maxUpStep = 1.0F;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        setSpawnTimer(spawnTimerInTicks);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, (float) 12));
        this.goalSelector.addGoal(4, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 10, 1.5, true) {
            @Override
            protected void executeAttack(LivingEntity target) {
                super.executeAttack(target);

                mob.playSound(SoundRegistry.CORPSE_EATER_ATTACK_HIT.get(), 1.0F, 1.0F);
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.4, 20));
    }

    @Override
    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            this.playSound(SoundRegistry.CORPSE_EATER_ATTACK.get(), 1.0F, 1.0F);
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpawnTimer() == spawnTimerInTicks - 1) {
            this.level.playSound(null, new BlockPos(this.position()), SoundRegistry.CORPSE_EATER_SPAWN.get(), SoundCategory.HOSTILE, 10.0F, 1.0F);
        }

        if (getSpawnTimer() > spawnTimerInTicks - 1 && level != null) {
            for (int i = 0; i < 30; ++i) {
                double offsetX = (2 * this.level.getRandom().nextFloat() - 1) * 0.2F;
                double offsetZ = (2 * this.level.getRandom().nextFloat() - 1) * 0.2F;

                double x = this.position().x + offsetX;
                double y = this.position().y + 0.1 + (this.level.getRandom().nextFloat() * 0.2F);
                double z = this.position().z + offsetZ;

                this.level.addParticle(ParticleTypeRegistry.CORPSE_SPLATTER.get(), x, y, z, offsetX / 2, 0.23D, offsetZ / 2);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.CORPSE_EATER_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.CORPSE_EATER_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.CORPSE_EATER_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundRegistry.CORPSE_EATER_STEP.get(), 1.0F, 1.0F);
    }

    public AnimationBuilder getSprintAnimation() {
        return new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    }

    @Override
    public AnimationBuilder getSpawnAnimation() {
        return new AnimationBuilder().addAnimation("spawn", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    }

    @Override
    public void remove(boolean keepData) {
        if (!this.level.isClientSide && this.isDeadOrDying() && !this.removed) {
            for (int i =-2; i<3; i++) {
                for (int j=-2; j<3; j++) {
                    if (this.random.nextFloat() < 0.5) {
                        BlockPos pos = this.blockPosition().offset(new Vector3i(i, 0, j));
                        BlockPos posUp = this.blockPosition().above().offset(new Vector3i(i, 0, j));
                        BlockPos posDown = this.blockPosition().below().offset(new Vector3i(i, 0, j));
                        if (DoomFireBlock.canSurviveOnBlock(this.level.getBlockState(pos.below()).getBlock()) && this.level.getBlockState(pos.below()).isFaceSturdy(this.level, pos.below(), Direction.UP) && this.level.getBlockState(pos).getDestroySpeed(this.level, pos) == 0.0f)
                            this.level.setBlockAndUpdate(pos, BlockRegistry.DOOM_FIRE_BLOCK.get().defaultBlockState());
                        else if (DoomFireBlock.canSurviveOnBlock(this.level.getBlockState(posDown.below()).getBlock()) && this.level.getBlockState(posDown.below()).isFaceSturdy(this.level, posDown.below(), Direction.UP)  && this.level.getBlockState(posDown).getDestroySpeed(this.level, posDown) == 0.0f)
                            this.level.setBlockAndUpdate(posDown, BlockRegistry.DOOM_FIRE_BLOCK.get().defaultBlockState());
                        else if (DoomFireBlock.canSurviveOnBlock(this.level.getBlockState(posUp.below()).getBlock()) && this.level.getBlockState(posUp.below()).isFaceSturdy(this.level, posUp.below(), Direction.UP)  && this.level.getBlockState(posUp).getDestroySpeed(this.level, posUp) == 0.0f)
                            this.level.setBlockAndUpdate(posUp, BlockRegistry.DOOM_FIRE_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }
        super.remove(keepData);
    }
}
