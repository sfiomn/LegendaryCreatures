package sfiomn.legendarycreatures.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.entities.goals.ChargeMeleeAttackGoal;
import sfiomn.legendarycreatures.entities.goals.RootMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import sfiomn.legendarycreatures.sounds.StoppableSound;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class HoundEntity extends AnimatedCreatureEntity {
    private final int chargeAttackDuration = 17;
    private final int chargeAttackActionPoint = 9;
    private final int biteAttackDuration = 12;
    private final int biteAttackActionPoint = 7;
    private final int biteLongAttackDuration = 26;

    private final RawAnimation RUN_ANIM = RawAnimation.begin().thenPlay("run");
    private final RawAnimation CHARGE_ANIM = RawAnimation.begin().thenPlay("charge");
    private final RawAnimation BITE_ANIM = RawAnimation.begin().thenPlay("bite");
    private final RawAnimation BITE_LONG_ANIM = RawAnimation.begin().thenPlay("bite_long");

    public HoundEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.31)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        ChargeMeleeAttackGoal chargeMeleeAttackGoal = new ChargeMeleeAttackGoal(this, chargeAttackDuration, chargeAttackActionPoint, 120, 8,  1.7, 30, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected void executeAttack(LivingEntity target) {
                super.executeAttack(target);
                this.mob.playSound(SoundRegistry.HOUND_BASE_ATTACK_HIT.get(), 1.0f, 1.0f);
            }
        };

        RootMeleeAttackGoal rootMeleeAttackGoal = new RootMeleeAttackGoal(this, this.biteAttackDuration, this.biteAttackActionPoint, biteLongAttackDuration * 3, 3.0f,   0.3, 1.0f, 240) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected void executeBaseAttack(LivingEntity target) {
                super.executeBaseAttack(target);
                this.mob.playSound(SoundRegistry.HOUND_BASE_ATTACK_HIT.get(), 1.0f, 1.0f);
            }

            @Override
            protected void startRootAttack() {
                super.startRootAttack();
                Minecraft.getInstance().getSoundManager().play(
                        new StoppableSound(SoundRegistry.HOUND_ROOT_ATTACK.get(),
                                this.mob,
                                (mob) -> mob.getAttackAnimation() != ROOT_ATTACK));
            }
        };

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, chargeMeleeAttackGoal);
        this.goalSelector.addGoal(4, rootMeleeAttackGoal);
        this.goalSelector.addGoal(5, new BaseMeleeAttackGoal(this, biteAttackDuration, biteAttackActionPoint, 5, 1.3, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                mob.playSound(SoundRegistry.HOUND_BASE_ATTACK_HIT.get(), 1.0F, 1.0F);
                return super.executeAttack(target);
            }

            @Override
            public boolean canContinueToUse() {
                if (chargeMeleeAttackGoal.canUse() || rootMeleeAttackGoal.canUse())
                    return false;
                return super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6, 40));
    }

    private float getMobLength() {
        return 1.5f;
    }

    @Override
    public <E extends GeoAnimatable> PlayState attackingPredicate(AnimationState<E> state) {
        if (getAttackAnimation() == BASE_ATTACK) {
            return state.setAndContinue(BITE_ANIM);
        } else if (getAttackAnimation() == CHARGE_ATTACK) {
            return state.setAndContinue(CHARGE_ANIM);
        } else if (getAttackAnimation() == ROOT_ATTACK) {
            return state.setAndContinue(BITE_LONG_ANIM);
        }

        state.getController().forceAnimationReset();
        return PlayState.STOP;
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
        if (getAttackAnimation() == CHARGING)
            this.playSound(SoundEvents.HOGLIN_STEP, 1.0F, 1.0F);
        else
            this.playSound(SoundRegistry.HOUND_STEP.get(), 1.0F, 1.0F);
    }

    public RawAnimation getSprintAnimation() {
        return RUN_ANIM;
    }
}
