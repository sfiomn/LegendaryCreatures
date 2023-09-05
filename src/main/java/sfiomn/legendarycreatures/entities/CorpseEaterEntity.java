package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.BlockRegistry;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import sfiomn.legendarycreatures.util.WorldUtil;
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

    public static CorpseEaterEntity create(EntityType<? extends CreatureEntity> type, World world) {
        return new CorpseEaterEntity(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 16);
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
        this.goalSelector.addGoal(4, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 10, SoundRegistry.CORPSE_EATER_MELEE_ATTACK.get(), 1.0, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.4, 20));
    }

    @Override
    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            this.playSound(SoundRegistry.CORPSE_EATER_MELEE_ATTACK.get(), 1.0F, 1.0F);
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
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

    public static void spawn(IWorld world, Vector3d pos) {
        if (!world.isClientSide()) {
            CorpseEaterEntity entityToSpawn = EntityTypeRegistry.CORPSE_EATER.get().create((World) world);
            if (entityToSpawn != null) {
                WorldUtil.spawnEntity(entityToSpawn, world, pos);
                world.playSound(null, new BlockPos(pos), SoundRegistry.CORPSE_EATER_SPAWN.get(), SoundCategory.HOSTILE, 10.0F, 1.0F);
            }
        }
    }

    @Override
    public void remove(boolean keepData) {
        if (!this.level.isClientSide && this.isDeadOrDying() && !this.removed) {
            for (int i =-2; i<3; i++) {
                for (int j=-2; j<3; j++) {
                    if (this.random.nextFloat() < 0.5)
                        this.level.setBlockAndUpdate(this.blockPosition().offset(new Vector3i(i, 0, j)), BlockRegistry.DOOM_FIRE.get().defaultBlockState());
                }
            }
        }
        super.remove(keepData);
    }
}
