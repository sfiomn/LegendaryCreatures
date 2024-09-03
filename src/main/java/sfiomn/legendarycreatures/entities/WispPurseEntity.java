package sfiomn.legendarycreatures.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import sfiomn.legendarycreatures.config.Config;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class WispPurseEntity extends Mob implements GeoEntity {

    private final AnimatableInstanceCache instanceCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation FALL = RawAnimation.begin().thenPlay("fall");
    protected static final RawAnimation GROUND = RawAnimation.begin().thenPlayAndHold("ground");

    public WispPurseEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        List<Integer> xpRewardLimits = getRangeXp();
        int xpReward = 0;
        if (xpRewardLimits.size() == 1)
            xpReward = xpRewardLimits.get(0);
        else if (xpRewardLimits.size() > 1) {
            if (Objects.equals(xpRewardLimits.get(0), xpRewardLimits.get(1))) {
                xpReward = xpRewardLimits.get(0);
            } else {
                xpReward = Math.min(xpRewardLimits.get(0), xpRewardLimits.get(1)) + level.random.nextInt(Math.abs(xpRewardLimits.get(0) - xpRewardLimits.get(1)));
            }
        }
        this.xpReward = xpReward;
    }

    protected List<Integer> getRangeXp() {
        return Config.Baked.wispPurseXpReward;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(ForgeMod.ENTITY_GRAVITY.get(), 0.005)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "animController", 10, this::animPredicate));
    }

    public <E extends GeoAnimatable> PlayState animPredicate(AnimationState<E> event) {
        if (this.onGround()) {
            event.getController().setAnimation(GROUND);
            if (event.getController().hasAnimationFinished()) {
                return PlayState.STOP;
            }
        } else {
            event.getController().setAnimation(FALL);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        if (!this.onGround()) {
            RandomSource random = this.getRandom();
            if (this.level().getGameTime() % 3 == 0) {
                for (int i = 0; i < 3; ++i) {
                    double offsetX = (2 * random.nextFloat() - 1) * 0.3F;
                    double offsetZ = (2 * random.nextFloat() - 1) * 0.3F;

                    double x = this.position().x + offsetX;
                    double y = this.position().y + (random.nextFloat() * 0.05F);
                    double z = this.position().z + offsetZ;

                    if (this.level().getGameTime() % 3 == 0)
                        this.level().addParticle(ParticleTypeRegistry.WISP_PARTICLE.get(), x, y, z, offsetX / 10, 0.01D, offsetZ / 10);
                }
            }
        }
        super.tick();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.WISP_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.WISP_DEATH.get();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        if (source.is(DamageTypes.DROWN))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.instanceCache;
    }
}
