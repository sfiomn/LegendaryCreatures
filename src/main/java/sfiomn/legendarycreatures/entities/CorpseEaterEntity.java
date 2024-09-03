package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.blocks.DoomFireBlock;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.BlockRegistry;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class CorpseEaterEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 15;
    private final int baseAttackActionPoint = 7;
    private final int spawnTimerInTicks = 20;

    private final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlayAndHold("spawn");
    private final RawAnimation RUN_ANIM = RawAnimation.begin().thenPlay("run");
    private final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("attack");

    public CorpseEaterEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
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
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, (float) 12));
        this.goalSelector.addGoal(4, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 5, 1.5, true) {
            @Override
            protected boolean executeAttack(LivingEntity target) {
                mob.playSound(SoundRegistry.CORPSE_EATER_ATTACK_HIT.get(), 1.0F, 1.0F);
                return super.executeAttack(target);
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.4, 20));
    }

    @Override
    public <E extends GeoAnimatable> PlayState attackingPredicate(AnimationState<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().hasAnimationFinished()) {
            this.playSound(SoundRegistry.CORPSE_EATER_ATTACK.get(), 1.0F, 1.0F);
            event.getController().setAnimation(ATTACK_ANIM);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpawnTimer() == spawnTimerInTicks - 1) {
            this.level().playSound(null, this.blockPosition(), SoundRegistry.CORPSE_EATER_SPAWN.get(), SoundSource.HOSTILE, 10.0F, 1.0F);
        }

        if (getSpawnTimer() > spawnTimerInTicks - 1) {
            for (int i = 0; i < 30; ++i) {
                double offsetX = (2 * this.level().getRandom().nextFloat() - 1) * 0.2F;
                double offsetZ = (2 * this.level().getRandom().nextFloat() - 1) * 0.2F;

                double x = this.position().x + offsetX;
                double y = this.position().y + 0.1 + (this.level().getRandom().nextFloat() * 0.2F);
                double z = this.position().z + offsetZ;

                this.level().addParticle(ParticleTypeRegistry.CORPSE_SPLATTER.get(), x, y, z, offsetX / 2, 0.23D, offsetZ / 2);
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

    public RawAnimation getSprintAnimation() {
        return RUN_ANIM;
    }

    @Override
    public RawAnimation getSpawnAnimation() {
        return SPAWN_ANIM;
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        if (!this.level().isClientSide && this.isDeadOrDying() && !this.isRemoved()) {
            for (int i =-2; i<3; i++) {
                for (int j=-2; j<3; j++) {
                    if (this.random.nextFloat() < 0.5) {
                        BlockPos pos = this.blockPosition().offset(new Vec3i(i, 0, j));
                        BlockPos posUp = this.blockPosition().above().offset(new Vec3i(i, 0, j));
                        BlockPos posDown = this.blockPosition().below().offset(new Vec3i(i, 0, j));
                        if (applyFireAtPos(this.level(), pos))
                            return;
                        if (applyFireAtPos(this.level(), posDown))
                            return;
                        if (applyFireAtPos(this.level(), posUp))
                            return;
                    }
                }
            }
        }
        super.remove(reason);
    }

    private boolean applyFireAtPos(Level level, BlockPos pos) {
        if (DoomFireBlock.checkDoomFireSurvive(level.getBlockState(pos.below()), level, pos.below()) &&
                level.getBlockState(pos).getDestroySpeed(level, pos) == 0.0f) {
            level.setBlockAndUpdate(pos, BlockRegistry.DOOM_FIRE_BLOCK.get().defaultBlockState());
            return true;
        }
        return false;
    }
}
