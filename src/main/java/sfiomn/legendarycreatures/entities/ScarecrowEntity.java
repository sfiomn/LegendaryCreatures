package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;


public class ScarecrowEntity extends AnimatedCreatureEntity {
    private final int spawnTimerInTicks = 32;
    private final int baseAttackDuration = 10;
    private final int baseAttackActionPoint = 5;

    private final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlayAndHold("spawn");
    private final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("attack");

    public ScarecrowEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
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
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 5, 1.0, true) {
            @Override
            protected boolean executeAttack(LivingEntity target) {
                this.mob.playSound(SoundRegistry.SCARECROW_BASE_ATTACK_HIT.get(), 1.0f, 1.0f);
                return super.executeAttack(target);
            }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    public <E extends GeoAnimatable> PlayState attackingPredicate(AnimationState<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().hasAnimationFinished()) {
            event.getController().setAnimation(ATTACK_ANIM);
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.SCARECROW_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundRegistry.SCARECROW_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public RawAnimation getSpawnAnimation() {
        return SPAWN_ANIM;
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpawnTimer() == spawnTimerInTicks - 1) {
            this.level().playSound(null, this.blockPosition(), SoundRegistry.SCARECROW_SPAWN.get(), SoundSource.HOSTILE, 10.0F, 1.0F);
        }
    }
}
