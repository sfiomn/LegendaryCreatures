package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.config.Config;
import sfiomn.legendarycreatures.registry.BlockRegistry;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WispPurseEntity extends MobEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    protected static final AnimationBuilder FALL = new AnimationBuilder().addAnimation("fall", ILoopType.EDefaultLoopTypes.LOOP);
    protected static final AnimationBuilder GROUND = new AnimationBuilder().addAnimation("ground", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public WispPurseEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        List<Integer> xpRewardLimits = getRangeXp();
        int xpReward = 0;
        if (xpRewardLimits.size() == 1)
            xpReward = xpRewardLimits.get(0);
        else if (xpRewardLimits.size() > 1) {
            if (Objects.equals(xpRewardLimits.get(0), xpRewardLimits.get(1))) {
                xpReward = xpRewardLimits.get(0);
            } else {
                xpReward = Math.min(xpRewardLimits.get(0), xpRewardLimits.get(1)) + world.random.nextInt(Math.abs(xpRewardLimits.get(0) - xpRewardLimits.get(1)));
            }
        }
        this.xpReward = xpReward;
    }

    protected List<Integer> getRangeXp() {
        return Config.Baked.wispPurseXpReward;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(ForgeMod.ENTITY_GRAVITY.get(), 0.005)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "animController", 10, this::animController));
    }

    protected <E extends WispPurseEntity> PlayState animController(final AnimationEvent<E> event) {
        if (this.isOnGround()) {
            event.getController().setAnimation(GROUND);
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                return PlayState.STOP;
            }
        } else {
            event.getController().setAnimation(FALL);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        if (level != null && !this.onGround) {
            Random random = this.getRandom();
            if (this.level.getGameTime() % 3 == 0) {
                for (int i = 0; i < 3; ++i) {
                    double offsetX = (2 * random.nextFloat() - 1) * 0.3F;
                    double offsetZ = (2 * random.nextFloat() - 1) * 0.3F;

                    double x = this.position().x + offsetX;
                    double y = this.position().y + (random.nextFloat() * 0.05F);
                    double z = this.position().z + offsetZ;

                    if (this.level.getGameTime() % 3 == 0)
                        this.level.addParticle(ParticleTypeRegistry.WISP_PARTICLE.get(), x, y, z, offsetX / 10, 0.01D, offsetZ / 10);
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
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        return super.hurt(source, amount);
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
