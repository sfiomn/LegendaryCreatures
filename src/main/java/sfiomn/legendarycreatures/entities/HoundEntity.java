package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.entities.goals.ChargeMeleeAttackGoal;
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

public class HoundEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 12;
    private final int chargeAttackDuration = 17;
    private final int biteAttackDuration = 26;

    public HoundEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.maxUpStep = 1.0f;
        this.xpReward = 5;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.ATTACK_KNOCKBACK, 4);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        //this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, (float) 1.0));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        //this.goalSelector.addGoal(4, new BaseMeleeAttackGoal(this, baseAttackDuration, (int) (baseAttackDuration / 2.0f), 120, null, 1.0, true));
        this.goalSelector.addGoal(5, new ChargeMeleeAttackGoal(this, chargeAttackDuration, (int) (chargeAttackDuration / 2.0f), 120, 8, null, 1.5, 15, 40));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
        //this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.6, 40));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (getAttackAnimation() == CHARGE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("charge", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.HOUND_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.HOUND_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.HOUND_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundRegistry.HOUND_STEP.get(), 1.0F, 1.0F);
    }

    public static void spawn(IWorld world, Vector3d pos) {
        if (!world.isClientSide()) {
            HoundEntity entityToSpawn = EntityTypeRegistry.HOUND.get().create((World) world);
            if (entityToSpawn != null) {
                WorldUtil.spawnEntity(entityToSpawn, world, pos);
            }
        }
    }
}
